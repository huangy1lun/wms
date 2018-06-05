package com.hyl.wms.service.impl;

import com.hyl.wms.domain.Employee;
import com.hyl.wms.domain.ProductStock;
import com.hyl.wms.domain.StockOutcomeBill;
import com.hyl.wms.domain.StockOutcomeBillItem;
import com.hyl.wms.exception.BillException;
import com.hyl.wms.mapper.ProductStockMapper;
import com.hyl.wms.mapper.StockOutcomeBillItemMapper;
import com.hyl.wms.mapper.StockOutcomeBillMapper;
import com.hyl.wms.page.PageResult;
import com.hyl.wms.query.QueryObject;
import com.hyl.wms.service.IProductStockService;
import com.hyl.wms.service.IStockOutcomeBillService;
import com.hyl.wms.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class StockOutcomeBillServiceImpl implements IStockOutcomeBillService {

    @Autowired
    private StockOutcomeBillMapper stockoutcomebillMapper;

    @Autowired
    private StockOutcomeBillItemMapper orderBillItemMapper;

    @Autowired
    private IProductStockService productStockService;




    @Override
    public List<StockOutcomeBill> list() {
        return stockoutcomebillMapper.selectAll();
    }

    @Override
    public void saveOrUpdate(StockOutcomeBill stockoutcomebill) {
        if (stockoutcomebill.getId() != null) {
            //编辑
            upDate(stockoutcomebill);
        } else {
            //新增
            save(stockoutcomebill);
        }
    }

    /**
     * 保存操作
     *
     * @param stockoutcomebill
     */
    private void save(StockOutcomeBill stockoutcomebill) {
        //获取当前登录用户
        Employee currentUserMsg = (Employee) SessionUtil.getCurrentUserMsg();
        //为订单设置录入人,录入时间,重置审核状态
        stockoutcomebill.setInputUser(currentUserMsg);
        stockoutcomebill.setInputTime(new Date());
        stockoutcomebill.setStatus(StockOutcomeBill.BILL_STATUS_NORMAL);
        //遍历计算总金额和总数量
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalNumber = BigDecimal.ZERO;
        //计算明细小计,设置到明细中
        for (StockOutcomeBillItem item : stockoutcomebill.getItems()) {
            BigDecimal amount = item.getSalePrice().multiply(item.getNumber()).setScale(2, BigDecimal.ROUND_HALF_UP);
            item.setAmount(amount);
            totalAmount = totalAmount.add(amount);
            totalNumber = totalNumber.add(item.getNumber());
        }
        //设置订单的总金额和总数量
        stockoutcomebill.setTotalAmount(totalAmount);
        stockoutcomebill.setTotalNumber(totalNumber);
        //保存订单
        stockoutcomebillMapper.insert(stockoutcomebill);
        //遍历明细,设置订单id,保存明细
        for (StockOutcomeBillItem item : stockoutcomebill.getItems()) {
            item.setBillId(stockoutcomebill.getId());
            orderBillItemMapper.insert(item);
        }


    }

    /**
     * 更新操作
     *
     * @param stockoutcomebill
     */
    private void upDate(StockOutcomeBill stockoutcomebill) {
        //判断订单的状态是否是已审核
        if (stockoutcomebill.getStatus() == StockOutcomeBill.BILL_STATUS_AUDITED) {
            throw new RuntimeException("该订单已经通过审批");
        }
        // 1.根据订单ID删除明细
        orderBillItemMapper.deleteByOrderId(stockoutcomebill.getId());
        // 2.遍历计算订单的总金额和总数量
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalCount = BigDecimal.ZERO;
        for (StockOutcomeBillItem item : stockoutcomebill.getItems()) {
            BigDecimal amount = item.getSalePrice().multiply(item.getNumber()).setScale(2, BigDecimal.ROUND_HALF_UP);
            totalAmount = totalAmount.add(amount);
            totalCount = totalCount.add(item.getNumber());
            // 3.计算明细小计
            // 4.保存明细
            item.setAmount(amount);
            item.setBillId(stockoutcomebill.getId());
            orderBillItemMapper.insert(item);
        }
        // 5.更新订单
        stockoutcomebill.setTotalAmount(totalAmount);
        stockoutcomebill.setTotalNumber(totalCount);
        stockoutcomebillMapper.updateByPrimaryKey(stockoutcomebill);
    }

    @Override
    public StockOutcomeBill get(Long id) {
        return stockoutcomebillMapper.selectByPrimaryKey(id);
    }

    @Override
    public void delete(Long id) {
        StockOutcomeBill bill = stockoutcomebillMapper.selectByPrimaryKey(id);
        if (bill.getStatus() == StockOutcomeBill.BILL_STATUS_AUDITED) {
            throw new RuntimeException("该订单已经通过审批");
        }
        //删除明细
        orderBillItemMapper.deleteByOrderId(id);
        //删除订单
        stockoutcomebillMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageResult query(QueryObject qo) {
        Integer count = stockoutcomebillMapper.queryForCount(qo);
        if (count == null) {
            return PageResult.empty(qo.getPageSize());
        }
        List<StockOutcomeBill> list = stockoutcomebillMapper.queryForList(qo);
        return new PageResult(qo.getCurrentPage(), qo.getPageSize(), count, list);
    }

    @Override
    public void audit(Long id) {
        //获取当前订单
        StockOutcomeBill bill = stockoutcomebillMapper.selectByPrimaryKey(id);
        //获取当前登录用户
        //设置当前订单的审核人和审核时间
        bill.setAuditor((Employee) SessionUtil.getCurrentUserMsg());
        bill.setAuditTime(new Date());
        //设置订单的状态
        bill.setStatus(StockOutcomeBill.BILL_STATUS_AUDITED);
        //更新订单
        stockoutcomebillMapper.audit(bill);
        /**
         * 出库操作
         */
        productStockService.stockOutcome(bill);

    }
}
