package com.hyl.wms.service.impl;

import com.hyl.wms.domain.Employee;
import com.hyl.wms.domain.ProductStock;
import com.hyl.wms.domain.StockIncomeBill;
import com.hyl.wms.domain.StockIncomeBillItem;
import com.hyl.wms.mapper.ProductStockMapper;
import com.hyl.wms.mapper.StockIncomeBillItemMapper;
import com.hyl.wms.mapper.StockIncomeBillMapper;
import com.hyl.wms.page.PageResult;
import com.hyl.wms.query.QueryObject;
import com.hyl.wms.service.IStockIncomeBillService;
import com.hyl.wms.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Service
public class StockIncomeBillServiceImpl implements IStockIncomeBillService {

    @Autowired
    private StockIncomeBillMapper stockincomebillMapper;


    @Autowired
    private StockIncomeBillItemMapper orderBillItemMapper;

    @Autowired
    private ProductStockMapper productStockMapper;

    @Override
    public List<StockIncomeBill> list() {
        return stockincomebillMapper.selectAll();
    }

    @Override
    public void saveOrUpdate(StockIncomeBill stockincomebill) {
        if (stockincomebill.getId() != null) {
            //编辑
            upDate(stockincomebill);
        } else {
            //新增
            save(stockincomebill);
        }
    }

    /**
     * 保存操作
     *
     * @param stockincomebill
     */
    private void save(StockIncomeBill stockincomebill) {
        //获取当前登录用户
        Employee currentUserMsg = (Employee) SessionUtil.getCurrentUserMsg();
        //为订单设置录入人,录入时间,重置状态为0
        stockincomebill.setInputUser(currentUserMsg);
        stockincomebill.setInputTime(new Date());
        stockincomebill.setStatus(StockIncomeBill.STATUS_NORMAL);
        //遍历明细,计算明细
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalNumber = BigDecimal.ZERO;
        for (StockIncomeBillItem item : stockincomebill.getItems()) {
            BigDecimal amount = item.getCostPrice().multiply(item.getNumber()).setScale(2, RoundingMode.HALF_UP);
            item.setAmount(amount);
            totalAmount = totalAmount.add(amount);
            totalNumber = totalNumber.add(item.getNumber());
        }
        stockincomebill.setTotalAmount(totalAmount);
        stockincomebill.setTotalNumber(totalNumber);
        //保存订单
        stockincomebillMapper.insert(stockincomebill);
        //设置明细订单id,保存明细
        for (StockIncomeBillItem item : stockincomebill.getItems()) {
            item.setBillId(stockincomebill.getId());
            orderBillItemMapper.insert(item);
        }
    }

    /**
     * 更新操作
     *
     * @param stockincomebill
     */
    private void upDate(StockIncomeBill stockincomebill) {
        if (stockincomebill.getStatus() == StockIncomeBill.STATUS_AUDIT) {
            throw new RuntimeException("该订单已通过审批");
        }
        //删除所有明细
        orderBillItemMapper.deleteByOrderId(stockincomebill.getId());
        //保存
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalNumber = BigDecimal.ZERO;
        for (StockIncomeBillItem item : stockincomebill.getItems()) {
            BigDecimal amount = item.getCostPrice().multiply(item.getNumber()).setScale(2,RoundingMode.HALF_UP);
            item.setAmount(amount);
            item.setBillId(stockincomebill.getId());
            orderBillItemMapper.insert(item);

            totalAmount = totalAmount.add(amount);
            totalNumber = totalNumber.add(item.getNumber());
        }
        stockincomebill.setTotalAmount(totalAmount);
        stockincomebill.setTotalNumber(totalNumber);
        stockincomebillMapper.updateByPrimaryKey(stockincomebill);
    }

    @Override
    public StockIncomeBill get(Long id) {
        return stockincomebillMapper.selectByPrimaryKey(id);
    }

    @Override
    public void delete(Long id) {
        StockIncomeBill bill = stockincomebillMapper.selectByPrimaryKey(id);
        if (bill.getId() == StockIncomeBill.STATUS_AUDIT) {
            throw new RuntimeException("该订单已通过审批");
        }
        //删除明细
        orderBillItemMapper.deleteByOrderId(id);
        //删除订单
        stockincomebillMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageResult query(QueryObject qo) {
        Integer count = stockincomebillMapper.queryForCount(qo);
        if (count == null) {
            return PageResult.empty(qo.getPageSize());
        }
        List<StockIncomeBill> list = stockincomebillMapper.queryForList(qo);
        return new PageResult(qo.getCurrentPage(), qo.getPageSize(), count, list);
    }

    @Override
    public void audit(Long id) {
        StockIncomeBill bill = stockincomebillMapper.selectByPrimaryKey(id);
        if (bill.getId() == StockIncomeBill.STATUS_AUDIT) {
            throw new RuntimeException("该订单已通过审批");
        }
        bill.setAuditUser((Employee) SessionUtil.getCurrentUserMsg());
        bill.setAuditTime(new Date());
        bill.setStatus(StockIncomeBill.STATUS_AUDIT);
        stockincomebillMapper.audit(bill);


        //入库操作
        for (StockIncomeBillItem item : bill.getItems()) {
            ProductStock ps = productStockMapper.queryByProductIdAndBillId(item.getProduct().getId(),bill.getDepot().getId());
            if (ps == null) {
                //新增库存
                ps = new ProductStock();
                ps.setPrice(item.getCostPrice());
                ps.setStoreNumber(item.getNumber());
                ps.setAmount(item.getAmount());
                ps.setProduct(item.getProduct());
                ps.setDepot(bill.getDepot());
                productStockMapper.insert(ps);
            } else {
                //更新库存价格 总金额 总数量
                ps.setStoreNumber(ps.getStoreNumber().add(item.getNumber()));
                ps.setAmount(ps.getAmount().add(item.getAmount()));
                ps.setPrice(ps.getAmount().divide(ps.getStoreNumber(),2,RoundingMode.HALF_UP));
                productStockMapper.updateByPrimaryKey(ps);
            }
        }

    }
}
