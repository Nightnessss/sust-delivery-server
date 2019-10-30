package com.fehead.service;

import com.fehead.controller.vo.OrderDetailVO;
import com.fehead.controller.vo.OrderListVO;
import com.fehead.model.DeliveryPointModel;

import java.util.List;

public interface OrderService {

    public List<OrderListVO> selectItemByStatusOk(Integer page,Integer pagesize, Integer point);

    public List<OrderListVO> search(Integer page,Integer pagesize, String search);

    public void updateStatus(Integer orderId,Integer statusId);

    OrderDetailVO getDetailOrder(Integer id);

    public List<DeliveryPointModel> selectAllDeliveryPoint();
}
