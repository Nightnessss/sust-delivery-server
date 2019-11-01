package com.fehead.service.impl;

import com.fehead.controller.vo.OrderDetailVO;
import com.fehead.controller.vo.OrderDetailVOIncludePicker;
import com.fehead.controller.vo.OrderListVO;
import com.fehead.controller.vo.OrderPickVO;
import com.fehead.model.DeliveryPointModel;
import com.fehead.model.OrderModel;
import com.fehead.model.StatusModel;
import com.fehead.service.CloudService;
import com.fehead.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CloudService cloudService;


    @Override
    public List<OrderListVO> selectItemByStatusOk(Integer page,Integer pagesize, Integer point) {


        List<OrderModel> orderModelList=cloudService.getAcceptableOrder(point,page, pagesize);

        List<OrderListVO> realOrderListVOList=orderModelList.stream().map(orderModel -> {
            OrderListVO orderListVO=new OrderListVO();
            BeanUtils.copyProperties(orderModel,orderListVO);
            orderListVO.setDisplayName(orderModel.getPublisher().getDisplayName());
            return orderListVO;
        }).collect(Collectors.toList());

        return realOrderListVOList;
    }

    @Override
    public List<OrderListVO> search(Integer page, Integer pagesize, String search) {
        List<OrderModel> orderModelList=cloudService.getAcceptableOrderByDestination(search, page, pagesize);

        List<OrderListVO> realOrderListVOList=orderModelList.stream().map(orderModel -> {
            OrderListVO orderListVO=new OrderListVO();
            BeanUtils.copyProperties(orderModel,orderListVO);
            orderListVO.setDisplayName(orderModel.getPublisher().getDisplayName());
            return orderListVO;
        }).collect(Collectors.toList());

        return realOrderListVOList;
    }

    public void updateStatus(Integer orderId,Integer statusId){

        cloudService.updateStatus(orderId,statusId);

    }

    //查找该订单详细信息
    @Override
    public OrderDetailVO getDetailOrder(Integer id) {
        final OrderModel orderModel=cloudService.getOrderById(id);
        OrderDetailVOIncludePicker orderDetailVO=new OrderDetailVOIncludePicker();
        BeanUtils.copyProperties(orderModel,orderDetailVO);
        orderDetailVO.setOrderPickVO(new OrderPickVO(){{
            setPickCode(orderModel.getPick().getPickCode());
            setPickName(orderModel.getPick().getPickName());
            setTailNumber(orderModel.getPick().getTailNumber());
        }} );
        orderDetailVO.setReceiver(orderModel.getReceiver());

        return orderDetailVO;
    }

    @Override
    public List<DeliveryPointModel> selectAllDeliveryPoint() {
        return cloudService.getAllDeliveryPoint();
    }


}
