package com.fehead.service.impl;

import com.fehead.controller.vo.OrderDetailVO;
import com.fehead.controller.vo.OrderListVO;
import com.fehead.controller.vo.OrderPickVO;
import com.fehead.controller.vo.UserVO;
import com.fehead.error.BusinessException;
import com.fehead.error.EmBusinessError;
import com.fehead.model.DeliveryPointModel;
import com.fehead.model.OrderModel;
import com.fehead.model.StatusModel;
import com.fehead.model.UserModel;
import com.fehead.service.CloudService;
import com.fehead.service.UserService;
import com.fehead.validator.ValidationResult;
import com.fehead.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private CloudService cloudService;

    @Autowired
    private ValidatorImpl validator;

    /**
     * 通过用户id获取所有订单信息
     * @param id
     * @param page
     * @param pagesize
     * @return
     */
    @Override
    public List<OrderListVO> getAllItemByUserId(Integer id,Integer page,Integer pagesize) {
        List<OrderListVO> orderListVOList=new ArrayList<>();
        List<OrderModel> orderModelList=cloudService.getPublisherOrder(id,page,pagesize);

        for (OrderModel orderModel:orderModelList) {
                OrderListVO orderListVO=new OrderListVO();
                BeanUtils.copyProperties(orderModel,orderListVO);
                orderListVOList.add(orderListVO);
        }
        return orderListVOList;
    }


    /**
     * 发布订单
     * @param orderModel
     * @param id
     * @return
     * @throws BusinessException
     */
    @Override
    public OrderDetailVO insert(OrderModel orderModel,Integer id) throws BusinessException {

//        ValidationResult result=validator.validate(orderModel);
//        if(result.isHasErrors()){
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
//        }


        //插入
        OrderDetailVO orderDetailVO=new OrderDetailVO();
        BeanUtils.copyProperties(orderModel,orderDetailVO);

        //复制
        UserModel publisher=cloudService.getUserById(id);
        orderDetailVO.setPublisher(publisher);
        orderModel.setPublisher(publisher);

        UserModel receiver=null;
        orderDetailVO.setReceiver(receiver);
        orderModel.setReceiver(receiver);

        StatusModel statusModel=new StatusModel();
        statusModel.setUpdateTime(new Date());
        statusModel.setStatus(1);
        statusModel.setUpdateTime(new Date());


        orderDetailVO.setStatus(statusModel);
        orderModel.setStatus(statusModel);

        DeliveryPointModel deliveryPointModel=cloudService.getDeliveryById(orderDetailVO.getDeliveryPoint().getId());
        orderDetailVO.setDeliveryPoint(deliveryPointModel);
        orderModel.setDeliveryPoint(deliveryPointModel);

//        cloudService.commitOrder(orderDetailVO.getDestination(),
//                                 orderDetailVO.getDeadline(),
//                                 orderDetailVO.getDeliveryPoint().getId(),
//                                orderDetailVO.getType(),
//                                orderDetailVO.getFee(),
//                                orderDetailVO.getRemark(),
//                                orderModel.getPick().getPickName(),
//                                orderModel.getPick().getTailNumber(),
//                                orderModel.getPick().getPickCode(),
//                                orderDetailVO.getPublisher().getId(),
//                                date,
//                                1,
//                                date);

        orderModel.setUpdateTime(new Date());
        OrderModel orderModel1 = cloudService.commitOrder(orderModel);

        orderDetailVO.setId(orderModel1.getId());

        StatusModel statusModel1=new StatusModel();
        statusModel1.setStatus(orderModel1.getStatus().getStatus());
        statusModel1.setUpdateTime(new Date());
        statusModel1.setId(orderModel1.getStatus().getId());

        orderDetailVO.setStatus(statusModel1);

        orderDetailVO.setUpdateTime(new Date());

        return orderDetailVO;
    }


    @Override
    public OrderDetailVO updateItemByOrderId(List<OrderDetailVO> orderDetailVOS, Integer orderId) {

        StatusModel statusModel=new StatusModel();
        statusModel.setStatus(2);
        OrderDetailVO orderDetailVO=new OrderDetailVO();
        orderDetailVO.setStatus(statusModel);

        return orderDetailVO;
    }

    /**
     * 通过订单id获取订单
     * @param orderId
     * @return
     * @throws BusinessException
     */
    @Override
    public OrderModel getOrderByOrderId(Integer orderId) throws BusinessException {
        OrderModel orderModel=new OrderModel();
        try{
            orderModel=cloudService.getOrderById(orderId);
        }
        catch (Exception ex){
            throw new BusinessException(EmBusinessError.DATA_SELECT_ERROR);
        }
        return orderModel;
    }

    /**
     * 更新订单
     * @param orderModel
     * @throws BusinessException
     */
    @Override
    public void updateItem(OrderModel orderModel) throws BusinessException {

        cloudService.updateItem(orderModel);

    }

    @Override
    //通过id查找用户
    public UserVO getUserById(Integer id) {
        UserModel userModel=cloudService.getUserById(id);
        UserVO userVO=convertFromModel(userModel);
        return userVO;
    }

    //查找用户所有接收的订单
    @Override
    public List<OrderListVO> getAllOderList(Integer id, Integer page,Integer pagesize) {
        List<OrderModel> orderModels=new ArrayList<>();
        orderModels=cloudService.getMyReceiverOrder(id,page,pagesize);
        List<OrderListVO> orderListVOS=new ArrayList<>();
        for(OrderModel order:orderModels){
            OrderListVO orderListVO=new OrderListVO();
            BeanUtils.copyProperties(order,orderListVO);
            orderListVOS.add(orderListVO);
        }
        return orderListVOS;
    }

    //删除订单
    @Override
    public String deleteOrder(Integer id, Integer orderId) throws BusinessException {
        String str="";
        OrderModel orderModel=new OrderModel();
        orderModel=cloudService.getOrderById(orderId);
        cloudService.updateStatus2(id, orderId);
        if (!orderModel.getPublisher().getId().equals(id)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "无删除权限");
        }
        OrderListVO orderListVO=new OrderListVO();
        BeanUtils.copyProperties(orderModel,orderListVO);
        if(orderListVO.getStatus().getStatus()==1){
            str="删除成功";
            cloudService.updateStatus(orderId,5);
        }else{
            str="删除失败";
        }
        return str;
    }

    //接订单
    @Override
    public OrderPickVO getOrderPick(Integer id, Integer orderId) {
        OrderModel orderModel=new OrderModel();
        orderModel=cloudService.getOrderById(orderId);
        cloudService.updateStatus2(id, orderId);
        //  orderModel=selectByOrderId(orderId);
        if(orderModel.getStatus().getStatus()!=1){
            return null;
        }
        OrderPickVO orderPickVO=new OrderPickVO();
        orderPickVO.setId(orderId);
        orderPickVO.setOrderId(orderId);
        orderPickVO.setPickCode(orderModel.getPick().getPickCode());
        orderPickVO.setPickName(orderModel.getPick().getPickName());
        orderPickVO.setTailNumber(orderModel.getPick().getTailNumber());
        return orderPickVO;
    }
    private UserVO convertFromModel(UserModel userModel){
        if(userModel==null){
            return null;
        }
        UserVO userVO=new UserVO();
        BeanUtils.copyProperties(userModel,userVO);
        return userVO;
    }

}
