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
import com.hyl.wms.service.IProductStockService;
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
    private IProductStockService productStockService;


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
        //为订单设置录入人,录入时间,重置审核状态
        stockincomebill.setInputUser(currentUserMsg);
        stockincomebill.setInputTime(new Date());
        stockincomebill.setStatus(StockIncomeBill.BILL_STATUS_NORMAL);
        //遍历计算总金额和总数量
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalNumber = BigDecimal.ZERO;
        //计算明细小计,设置到明细中
        for (StockIncomeBillItem item : stockincomebill.getItems()) {
            BigDecimal amount = item.getCostPrice().multiply(item.getNumber()).setScale(2, BigDecimal.ROUND_HALF_UP);
            item.setAmount(amount);
            totalAmount = totalAmount.add(amount);
            totalNumber = totalNumber.add(item.getNumber());
        }
        //设置订单的总金额和总数量
        stockincomebill.setTotalAmount(totalAmount);
        stockincomebill.setTotalNumber(totalNumber);
        //保存订单
        stockincomebillMapper.insert(stockincomebill);
        //遍历明细,设置订单id,保存明细
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
        //判断订单的状态是否是已审核
        if (stockincomebill.getStatus() == StockIncomeBill.BILL_STATUS_AUDITED) {
            throw new RuntimeException("该订单已经通过审批");
        }
        // 1.根据订单ID删除明细
        orderBillItemMapper.deleteByOrderId(stockincomebill.getId());
        // 2.遍历计算订单的总金额和总数量
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalCount = BigDecimal.ZERO;
        for (StockIncomeBillItem item : stockincomebill.getItems()) {
            BigDecimal amount = item.getCostPrice().multiply(item.getNumber()).setScale(2, BigDecimal.ROUND_HALF_UP);
            totalAmount = totalAmount.add(amount);
            totalCount = totalCount.add(item.getNumber());
            // 3.计算明细小计
            // 4.保存明细
            item.setAmount(amount);
            item.setBillId(stockincomebill.getId());
            orderBillItemMapper.insert(item);
        }
        // 5.更新订单
        stockincomebill.setTotalAmount(totalAmount);
        stockincomebill.setTotalNumber(totalCount);
        stockincomebillMapper.updateByPrimaryKey(stockincomebill);
    }

    @Override
    public StockIncomeBill get(Long id) {
        return stockincomebillMapper.selectByPrimaryKey(id);
    }

    @Override
    public void delete(Long id) {
        StockIncomeBill bill = stockincomebillMapper.selectByPrimaryKey(id);
        if (bill.getStatus() == StockIncomeBill.BILL_STATUS_AUDITED) {
            throw new RuntimeException("该订单已经通过审批");
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
        //获取当前订单
        StockIncomeBill bill = stockincomebillMapper.selectByPrimaryKey(id);
        //获取当前登录用户
        //设置当前订单的审核人和审核时间
        bill.setAuditor((Employee) SessionUtil.getCurrentUserMsg());
        bill.setAuditTime(new Date());
        //设置订单的状态
        bill.setStatus(StockIncomeBill.BILL_STATUS_AUDITED);
        //更新订单
        stockincomebillMapper.audit(bill);
        /**
         * 入库操作
         */
        productStockService.stockIncome(bill);

    }
}
