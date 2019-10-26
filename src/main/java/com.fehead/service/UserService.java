package com.fehead.service;

import com.fehead.controller.vo.OrderDetailVO;
import com.fehead.controller.vo.OrderListVO;
import com.fehead.controller.vo.OrderPickVO;
import com.fehead.controller.vo.UserVO;
import com.fehead.error.BusinessException;
import com.fehead.model.OrderModel;
import com.fehead.model.PickModel;
import com.fehead.model.UserModel;

import java.util.List;

public interface UserService {
    public List<OrderListVO> getAllItemByUserId(Integer id,Integer page,Integer pagesize);
//    public UserModel getUserById(Integer id);
    public OrderDetailVO insert(OrderModel orderModel,Integer id) throws BusinessException;
    public OrderDetailVO updateItemByOrderId(List<OrderDetailVO> orderDetailVOS,Integer orderId);
    public OrderModel getOrderByOrderId(Integer orderId) throws BusinessException;

    public void updateItem(OrderModel orderModel) throws BusinessException;
    UserVO getUserById(Integer id);
    List<OrderListVO> getAllOderList(Integer id, Integer page,Integer pageSize);
    String deleteOrder(Integer id,Integer orderId) throws BusinessException;
    OrderPickVO getOrderPick(Integer id,Integer orderId);

}
