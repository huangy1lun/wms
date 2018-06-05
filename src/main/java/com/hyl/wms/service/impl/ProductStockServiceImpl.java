package com.hyl.wms.service.impl;

import com.alibaba.druid.filter.config.ConfigTools;
import com.hyl.wms.domain.*;
import com.hyl.wms.exception.BillException;
import com.hyl.wms.mapper.ProductStockMapper;
import com.hyl.wms.mapper.SaleAccountMapper;
import com.hyl.wms.page.PageResult;
import com.hyl.wms.query.QueryObject;
import com.hyl.wms.service.IProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;

@Service
public class ProductStockServiceImpl implements IProductStockService{

    @Autowired
    private ProductStockMapper productStockMapper;

    @Autowired
    private SaleAccountMapper saleAccountMapper;

    @Override
    public void stockIncome(StockIncomeBill bill) {
        // 1.遍历订单明细根据商品id和仓库id查询库存记录
        for (StockIncomeBillItem item : bill.getItems()) {
            ProductStock productStock = productStockMapper.queryByProductIdAndDepotId(item.getProduct().getId(), bill.getDepot().getId());
            // 2.如果没有库存记录,新增一条库存,
            if (productStock == null) {
                productStock = new ProductStock();
                productStock.setAmount(item.getAmount());
                productStock.setStoreNumber(item.getNumber());
                productStock.setPrice(item.getCostPrice());
                productStock.setProduct(item.getProduct());
                productStock.setDepot(bill.getDepot());
                productStockMapper.insert(productStock);
            } else {
                // 3.如果有库存记录,重新计算amount store_number,price
                productStock.setStoreNumber(productStock.getStoreNumber().add(item.getNumber()));
                productStock.setAmount(productStock.getAmount().add(item.getAmount()));
                productStock.setPrice(productStock.getAmount().divide(productStock.getStoreNumber(),2, RoundingMode.HALF_UP));
                //更新库存
                productStockMapper.updateByPrimaryKey(productStock);
            }
        }
    }

    @Override
    public void stockOutcome(StockOutcomeBill bill) {
        for (StockOutcomeBillItem item : bill.getItems()) {
            //遍历明细根据商品id和仓库id查询仓库
            ProductStock ps = productStockMapper.queryByProductIdAndDepotId(item.getProduct().getId(), bill.getDepot().getId());
            //判断仓库是否存在
            if (ps == null) {
                //如果仓库不存在,throw 当前仓库库存不足
                throw new BillException(bill.getDepot().getName() + "仓库中没有" + item.getProduct().getName());
            }
            //如果存在,判断当前需要的数量是否大于仓库的余量,是则throw出错误
            if (item.getNumber().compareTo(ps.getStoreNumber()) == 1) {
                throw new BillException(bill.getDepot().getName() + "仓库中"+ item.getProduct().getName()+"只有"+ps.getStoreNumber()+"件" +
                        "---需求" + item.getNumber()+"件");
            }
            //重置数量,总金额
            ps.setStoreNumber(ps.getStoreNumber().subtract(item.getNumber()));
            ps.setAmount(ps.getAmount().subtract(item.getAmount()));
            productStockMapper.updateByPrimaryKey(ps);

            /**
             *  记录流水账
             */
            SaleAccount sa = new SaleAccount();
            sa.setVdate(bill.getVdate());
            sa.setNumber(item.getNumber());
            sa.setCostPrice(ps.getPrice());
            sa.setSalePrice(item.getSalePrice());
            sa.setCostAmount(sa.getCostPrice().multiply(sa.getNumber()).setScale(2,RoundingMode.HALF_UP));
            sa.setSaleAmount(item.getAmount());
            sa.setProductId(item.getProduct().getId());
            sa.setSalemanId(bill.getInputUser().getId());
            sa.setClientId(bill.getClient().getId());
            saleAccountMapper.insert(sa);
        }


    }

    @Override
    public PageResult query(QueryObject qo) {
        Integer count = productStockMapper.queryForCount(qo);
        if (count == null) {
            return PageResult.empty(qo.getPageSize());
        }
        return new PageResult(qo.getCurrentPage(),qo.getPageSize(),count,productStockMapper.queryForList(qo));
    }
}
