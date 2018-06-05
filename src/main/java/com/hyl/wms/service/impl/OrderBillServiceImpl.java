package com.hyl.wms.service.impl;

import com.hyl.wms.domain.Employee;
import com.hyl.wms.domain.OrderBill;
import com.hyl.wms.domain.OrderBillItem;
import com.hyl.wms.mapper.OrderBillItemMapper;
import com.hyl.wms.mapper.OrderBillMapper;
import com.hyl.wms.page.PageResult;
import com.hyl.wms.query.QueryObject;
import com.hyl.wms.service.IOrderBillService;
import com.hyl.wms.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class OrderBillServiceImpl implements IOrderBillService {

    @Autowired
    private OrderBillMapper orderbillMapper;

    @Autowired
    private OrderBillItemMapper orderBillItemMapper;


    @Override
    public List<OrderBill> list() {
        return orderbillMapper.selectAll();
    }

    @Override
    public void saveOrUpdate(OrderBill orderbill) {
        if (orderbill.getId() != null) {
            //编辑
            upDate(orderbill);
        } else {
            //新增
            save(orderbill);
        }
    }

    /**
     * 保存操作
     *
     * @param orderbill
     */
    private void save(OrderBill orderbill) {
        // 1.获取当前用户的信息
        Employee user = (Employee) SessionUtil.getCurrentUserMsg();
        // 2.将录入人设置为当前用户 设置录入时间
        orderbill.setInputUser(user);
        orderbill.setInputTime(new Date());
        // 3.将审核状态重置为未审核状态
        orderbill.setStatus(orderbill.ORDER_BILL_STATUS_NORMAL);
        // 4.遍历计算订单的总金额和总数量
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalCount = BigDecimal.ZERO;
        for (OrderBillItem item : orderbill.getItems()) {
            // 5.计算明细的小计金额,设置到明细的对象中
            BigDecimal amount = item.getCostPrice().multiply(item.getNumber()).setScale(2, BigDecimal.ROUND_HALF_UP);
            item.setAmount(amount);
            //叠加总金额和总数量
            totalAmount = totalAmount.add(amount);
            totalCount = totalCount.add(item.getNumber());
        }
        // 6.为订单设置总金额和总数量
        orderbill.setTotalAmount(totalAmount);
        orderbill.setTotalNumber(totalCount);
        // 7.保存订单
        orderbillMapper.insert(orderbill);
        // 8.设置明细的订单号
        // 9.遍历保存明细
        for (OrderBillItem item : orderbill.getItems()) {
            item.setBillId(orderbill.getId());
            orderBillItemMapper.insert(item);
        }

    }

    /**
     * 更新操作
     *
     * @param orderbill
     */
    private void upDate(OrderBill orderbill) {
        // 1.根据订单ID删除明细
        orderBillItemMapper.deleteByOrderId(orderbill.getId());
        // 2.遍历计算订单的总金额和总数量
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalCount = BigDecimal.ZERO;
        for (OrderBillItem item : orderbill.getItems()) {
            BigDecimal amount = item.getCostPrice().multiply(item.getNumber()).setScale(2, BigDecimal.ROUND_HALF_UP);
            totalAmount = totalAmount.add(amount);
            totalCount = totalCount.add(item.getNumber());
            // 3.计算明细小计
            // 4.保存明细
            item.setAmount(amount);
            item.setBillId(orderbill.getId());
            orderBillItemMapper.insert(item);
        }
        // 5.更新订单
        orderbill.setTotalAmount(totalAmount);
        orderbill.setTotalNumber(totalCount);
        orderbillMapper.updateByPrimaryKey(orderbill);
    }

    @Override
    public OrderBill get(Long id) {
        return orderbillMapper.selectByPrimaryKey(id);
    }

    @Override
    public void delete(Long id) {
        //删除明细
        orderBillItemMapper.deleteByOrderId(id);
        //删除订单
        orderbillMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageResult query(QueryObject qo) {
        Integer count = orderbillMapper.queryForCount(qo);
        if (count == null) {
            return PageResult.empty(qo.getPageSize());
        }
        List<OrderBill> list = orderbillMapper.queryForList(qo);
        return new PageResult(qo.getCurrentPage(), qo.getPageSize(), count, list);
    }

    @Override
    public void audit(Long id) {
        //获取当前订单
        OrderBill orderBill = orderbillMapper.selectByPrimaryKey(id);
        //获取当前用户
        Employee currentUserMsg = (Employee) SessionUtil.getCurrentUserMsg();
        //为订单更新审核用户,审核时间,审核状态
        orderBill.setAuditor(currentUserMsg);
        orderBill.setAuditTime(new Date());
        orderBill.setStatus(orderBill.ORDER_BILL_STATUS_AUDITED);
        orderbillMapper.audit(orderBill);
    }
}
