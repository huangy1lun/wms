package com.hyl.wms.web.controller;

import com.alibaba.druid.util.StringUtils;
import com.hyl.wms.domain.Product;
import com.hyl.wms.query.ProductQueryObject;
import com.hyl.wms.query.QueryObject;
import com.hyl.wms.service.IBrandService;
import com.hyl.wms.service.IProductService;
import com.hyl.wms.util.RequiredPermission;
import com.hyl.wms.util.UploadUtil;
import com.hyl.wms.vo.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.util.Arrays;
import java.util.Map;

@Controller
@RequestMapping("product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IBrandService brandService;

    @Autowired
    private ServletContext ctx;


    @RequestMapping("list")
    @RequiredPermission("商品列表")
    public String list(Map map,@ModelAttribute("qo") ProductQueryObject qo) {
        map.put("result", productService.query(qo));
        map.put("brands",brandService.list());
        return "product/list";
    }

    @RequestMapping("input")
    @RequiredPermission("商品新增或删除")
    public String input(Long id, Map map) {
        if (id != null) {
            map.put("product", productService.get(id));
        }
        map.put("brands",brandService.list());
        return "product/input";
    }

    @RequestMapping("saveOrUpdate")
    @RequiredPermission("商品编辑")
    @ResponseBody
    public JSONResult saveOrUpdate(Product product, MultipartFile img) {
        try {
            //上传图片
            if (img != null) {
                //更新时,判断原来是否有图片,如果有先删除原本的
                if(!StringUtils.isEmpty(product.getImagePath())) {
                    UploadUtil.deleteFile(ctx,product.getImagePath());
                }
                //新建时直接上传
                String imagePath = UploadUtil.upload(ctx, img);
                product.setImagePath(imagePath);
            }
            productService.saveOrUpdate(product);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.mark("操作失败");
        }
        return JSONResult.success("保存成功");
    }

    @RequestMapping("delete")
    @RequiredPermission("商品删除")
    @ResponseBody
    public JSONResult delete(Long id,String imagePath) {
        try {
            //删除upload下的文件
            if(!StringUtils.isEmpty(imagePath)) {
                UploadUtil.deleteFile(ctx,imagePath);
            }
            productService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("商品删除成功");
    }

    @RequestMapping("batchDelete")
    @ResponseBody
    @RequiredPermission("商品批量删除")
    public JSONResult batchDelete(@RequestParam("ids[]") Long[] ids) {
        System.out.println(Arrays.toString(ids));
        try {
            //删除图片
            if(ids != null && ids.length != 0){
                for (Long id : ids) {
                    Product product = productService.get(id);
                    if (!StringUtils.isEmpty(product.getImagePath())) {
                        UploadUtil.deleteFile(ctx, product.getImagePath());
                    }
                }
            }

            //删除商品
            productService.batchDelete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("删除成功");
    }

    @RequestMapping("allProduct")
    public String allProduct(Map map,@ModelAttribute("qo") ProductQueryObject qo) {
        map.put("result", productService.query(qo));
        map.put("brands",brandService.list());
        return "product/selectByOrder";
    }



}
