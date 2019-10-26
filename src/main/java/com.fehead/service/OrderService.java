package com.fehead.service;

import com.fehead.controller.vo.OrderDetailVO;
import com.fehead.controller.vo.OrderListVO;

import java.util.List;

public interface OrderService {

    public List<OrderListVO> selectItemByStatusOk(Integer page,Integer pagesize, Integer point);

    public void updateStatus(Integer orderId,Integer statusId);

    OrderDetailVO getDetailOrder(Integer id);
}
