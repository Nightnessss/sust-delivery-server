package com.fehead.controller;


import com.fehead.controller.vo.OrderDetailVO;
import com.fehead.controller.vo.OrderListVO;
import com.fehead.controller.vo.OrderPickVO;
import com.fehead.controller.vo.UserVO;
import com.fehead.error.BusinessException;
import com.fehead.error.EmBusinessError;
import com.fehead.model.*;
import com.fehead.response.CommonReturnType;
import com.fehead.service.UserService;
import com.fehead.validator.ValidationResult;
import com.fehead.validator.ValidatorImpl;
import com.sun.org.apache.xpath.internal.operations.Or;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


@RequestMapping("/api/v1.0/SUSTDelivery/view")
@RestController
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class UserController extends BaseController{
    public static Log logger = LogFactory.getLog(UserController.class);

    @Autowired
    UserService userService;
    @Autowired
    private ValidatorImpl validator;

    /**
     * 查找该用户所有发布的订单（已删除订单除外）
     * @param id
     * @param page
     * @param pagesize
     * @return
     * @throws BusinessException
     */
    @GetMapping("/user/{id}/myLists")
    @ApiOperation("查找该用户所有发布的订单（已删除订单除外）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", dataType = "Integer"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "int"),
            @ApiImplicitParam(name = "pagesize", value = "页大小", dataType = "int")
    })
    private CommonReturnType findAllListByUserId(@PathVariable("id") Integer id,
                                                @RequestParam("page") int page,
                                                 @RequestParam("pagesize") int pagesize) throws BusinessException {
        logger.info("PARAM: 用户ID " + id);
        List<OrderListVO> orderListVOList=new ArrayList<>();
        try {
            orderListVOList=userService.getAllItemByUserId(id,page,pagesize);
            logger.info("SUCCESS: findAllListByUserId" );
        }
        catch (Exception ex){
            logger.info("EXCEPTION: " + EmBusinessError.DATA_SELECT_ERROR.getErrorCode() + " " + EmBusinessError.DATA_SELECT_ERROR.getErrorMsg());
            throw new BusinessException(EmBusinessError.DATA_SELECT_ERROR);
        }
        return CommonReturnType.creat(orderListVOList);
    }


    /**
     * 发布订单
     * @param id
     * @param destination
     * @param deadline
     * @param deliveryPointId
     * @param type
     * @param fee
     * @param remark
     * @param pickName
     * @param tailNumber
     * @param pickCode
     * @return
     * @throws BusinessException
     */
    @PostMapping("/user/{id}/commit")
    @ApiOperation("发布订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", dataType = "Integer"),
            @ApiImplicitParam(name = "destination", value = "目的地", dataType = "Date"),
            @ApiImplicitParam(name = "deadline", value = "订单截止时间", dataType = "Integer"),
            @ApiImplicitParam(name = "deliveryPointId", value = "快递点", dataType = "Integer"),
            @ApiImplicitParam(name = "type", value = "快递类型（0小1中2大）", dataType = "Integer"),
            @ApiImplicitParam(name = "fee", value = "小费", dataType = "String"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "String"),
            @ApiImplicitParam(name = "pickName", value = "取货人（淘宝用户名）", dataType = "String"),
            @ApiImplicitParam(name = "tailNumber", value = "手机尾号", dataType = "String"),
            @ApiImplicitParam(name = "pickCode", value = "取货码", dataType = "String")
    })
    private CommonReturnType  publishItem(@PathVariable("id")Integer id,
                                          @RequestParam(value = "destination") String destination,
                                          @RequestParam(value = "deadline") Date deadline,
                                          @RequestParam(value = "deliveryPointId") Integer deliveryPointId,
                                          @RequestParam(value = "type") Integer type,
                                          @RequestParam(value = "fee") String fee,
                                          @RequestParam(value = "remark") String remark,
                                          @RequestParam(value = "pickName") String pickName,
                                          @RequestParam(value = "tailNumber") String tailNumber,
                                          @RequestParam(value = "pickCode") String pickCode
                                          ) throws BusinessException {




        logger.info("PARAM: 用户ID " + id);
        logger.info("PARAM: 用户送达地点 " + destination);
        logger.info("PARAM: 用户包裹类型 " + type);
        logger.info("PARAM: 用户给的小费 " + fee);
        logger.info("PARAM: 用户发单的截止时间 " + deadline);
        logger.info("PARAM: 用户快递点 " + deliveryPointId);
        logger.info("PARAM: 用户留言 " + remark);
        logger.info("PARAM: 用户留下的取货人名 " + pickName);
        logger.info("PARAM: 用户留下的取货人手机尾号 " + tailNumber);
        logger.info("PARAM: 用户留下的取货码 " + pickCode);


        PickModel pickModel=new PickModel();
        pickModel.setTailNumber(tailNumber);
        pickModel.setPickName(pickName);
        pickModel.setPickCode(pickCode);

        DeliveryPointModel deliveryPointModel=new DeliveryPointModel();
        deliveryPointModel.setId(deliveryPointId);

        OrderModel orderModel=new OrderModel();
        orderModel.setDeadline(deadline);
        orderModel.setDestination(destination);
        orderModel.setType(type);
        orderModel.setFee(fee);
        orderModel.setRemark(remark);

        orderModel.setDeliveryPoint(deliveryPointModel);
        orderModel.setPick(pickModel);


        OrderDetailVO detailVO=new OrderDetailVO();
        try{
            detailVO=userService.insert(orderModel,id);
            logger.info("SUCCESS: insert" );
        }
        catch (Exception ex){
            logger.info("EXCEPTION: " + EmBusinessError.DATA_INSERT_ERROR.getErrorCode() + " " + EmBusinessError.DATA_INSERT_ERROR.getErrorMsg());
            throw new BusinessException(EmBusinessError.DATA_INSERT_ERROR);
        }

        return CommonReturnType.creat(detailVO);
    }


    /**
     * 修改订单
     * @param id
     * @param orderId
     * @param destination
     * @param deadline
     * @param deliveryPointId
     * @param type
     * @param fee
     * @param remark
     * @param pickName
     * @param tailNumber
     * @param pickCode
     * @return
     * @throws BusinessException
     */
    @PutMapping("/user/{id}/myLists/{order_id}")
    @ApiOperation("修改订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", dataType = "Integer"),
            @ApiImplicitParam(name = "order_id", value = "订单ID", dataType = "Integer"),
            @ApiImplicitParam(name = "destination", value = "目的地", dataType = "String"),
            @ApiImplicitParam(name = "deadline", value = "订单截止时间", dataType = "Date"),
            @ApiImplicitParam(name = "deliveryPointId", value = "快递点", dataType = "Integer"),
            @ApiImplicitParam(name = "type", value = "快递类型（0小1中2大）", dataType = "Integer"),
            @ApiImplicitParam(name = "fee", value = "小费", dataType = "String"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "String"),
            @ApiImplicitParam(name = "pickName", value = "取货人（淘宝用户名）", dataType = "String"),
            @ApiImplicitParam(name = "tailNumber", value = "手机尾号", dataType = "String"),
            @ApiImplicitParam(name = "pickCode", value = "取货码", dataType = "String")
    })
    private CommonReturnType  updateItem(@PathVariable("id")Integer id,
                                         @PathVariable("order_id")Integer orderId,
                                          @RequestParam(value = "destination") String destination,
                                          @RequestParam(value = "deadline") Date deadline,
                                          @RequestParam(value = "deliveryPointId") Integer deliveryPointId,
                                          @RequestParam(value = "type") Integer type,
                                          @RequestParam(value = "fee") String fee,
                                          @RequestParam(value = "remark") String remark,
                                          @RequestParam(value = "pickName") String pickName,
                                          @RequestParam(value = "tailNumber") String tailNumber,
                                          @RequestParam(value = "pickCode") String pickCode
    ) throws BusinessException {

        logger.info("PARAM: 用户ID " + id);
        logger.info("PARAM: 用户修改后的订单destination " + destination);
        logger.info("PARAM: 用户修改后的订单deadline " + deadline);
        logger.info("PARAM: 用户修改后的订单deliveryPointId " + deliveryPointId);
        logger.info("PARAM: 用户修改后的订单type " + type);
        logger.info("PARAM: 用户修改后的订单fee " + fee);
        logger.info("PARAM: 用户修改后的订单remark " + remark);
        logger.info("PARAM: 用户修改后的订单pickName " + pickName);
        logger.info("PARAM: 用户修改后的订单tailNumber " + tailNumber);
        logger.info("PARAM: 用户修改后的订单pickCode " + pickCode);

        OrderModel orderModel=userService.getOrderByOrderId(orderId);
//        System.out.println(orderModel.toString());
        if(orderModel==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"订单信息为空");
        }
        logger.info("SUCCESS: getOrderByOrderId" );
        if(orderModel.getStatus().getStatus()!=1){
            String data="修改失败，订单非未接状态";
            return CommonReturnType.creat(data);
        } if (!orderModel.getPublisher().getId().equals(id)) {
            String data="无修改权限";
            return CommonReturnType.creat(data);
        }

            DeliveryPointModel deliveryPointModel=new DeliveryPointModel();
            deliveryPointModel.setId(deliveryPointId);

            Integer pickId=orderModel.getPick().getId();
            PickModel pickModel=new PickModel();
            pickModel.setId(pickId);
            pickModel.setPickCode(pickCode);
            pickModel.setPickName(pickName);
            pickModel.setTailNumber(tailNumber);


            Integer statusId=orderModel.getStatus().getId();
            StatusModel statusModel=new StatusModel();
            statusModel.setUpdateTime(new Date());
            statusModel.setStatus(1);
            statusModel.setId(statusId);


            orderModel.setDestination(destination);
            orderModel.setDeadline(deadline);
            orderModel.setDeliveryPoint(deliveryPointModel);
            orderModel.setType(type);
            orderModel.setFee(fee);
            orderModel.setRemark(remark);
            orderModel.setPick(pickModel);
            orderModel.setStatus(statusModel);
            try {
                userService.updateItem(orderModel);
                logger.info("SUCCESS: updateItem" );
            }
            catch (Exception ex){
                throw new BusinessException(EmBusinessError.DATA_UPDATE_ERROR);
            }
            String data="修改成功";
            return CommonReturnType.creat(data);
    }

    /**
     * 通过用户id查找用户信息
     * @param id
     * @return
     * @throws BusinessException
     */
    @GetMapping("/user/{id}/info")
    @ApiOperation("通过用户id查找用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", dataType = "Integer")
    })
    public CommonReturnType getUser(@PathVariable("id") Integer id) throws BusinessException {
        logger.info("PARAM: id "+id);

        UserVO userVO=new UserVO();
        try{
            userVO = userService.getUserById(id);
        }catch (Exception e){
            logger.info("EXCEPTION: " + EmBusinessError.DATA_SELECT_ERROR.getErrorCode() + " "
                    + EmBusinessError.DATA_SELECT_ERROR.getErrorMsg());
            throw new BusinessException(EmBusinessError.DATA_SELECT_ERROR);

        }
        logger.info(" SUCCESS :getUser");
        return CommonReturnType.creat(userVO);
    }


    /**
     * 查找该用户所有接收的订单
     * @param id
     * @param page
     * @return
     * @throws BusinessException
     */
    @GetMapping("user/{id}/theirList")
    @ApiOperation("查找该用户所有接收的订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", dataType = "Integer"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "Integer"),
            @ApiImplicitParam(name = "pagesize", value = "页大小", dataType = "Integer")
    })
    public CommonReturnType getAllTheirList(@PathVariable("id") Integer id, @RequestParam(name="page") Integer page,@RequestParam(name="pagesize") Integer pagesize) throws BusinessException {
        List<OrderListVO> orderListVOS=new ArrayList<>();
        logger.info("PARAM: id "+id);
        logger.info("PARAM: page "+page);
        try {
            orderListVOS=userService.getAllOderList(id, page,pagesize);
        } catch (Exception e) {
            logger.info("EXCEPTION: " + EmBusinessError.DATA_SELECT_ERROR.getErrorCode() + " "
                    + EmBusinessError.DATA_SELECT_ERROR.getErrorMsg());
            throw new BusinessException(EmBusinessError.DATA_SELECT_ERROR,"获取该用户所有接收订单异常");
        }
        logger.info("SUCCESS:getAllTheirList");
        return CommonReturnType.creat(orderListVOS);
    }

    @GetMapping("user/{id}/their/{order_id}")
    @ApiOperation("查找该用户接收订单的订单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", dataType = "Integer"),
            @ApiImplicitParam(name = "order_id", value = "订单ID", dataType = "Integer"),
    })
    public CommonReturnType getAllTheirOrderId(@PathVariable("id") Integer id
            , @PathVariable(name="order_id") Integer orderId) throws BusinessException {
        OrderPickVO orderVOS=null;
        logger.info("PARAM: id "+id);
        logger.info("PARAM: page "+orderId);
        try {
            orderVOS=userService.getOrderPickedInfo(id, orderId);
        } catch (Exception e) {
            logger.info("EXCEPTION: " + EmBusinessError.DATA_SELECT_ERROR.getErrorCode() + " "
                    + EmBusinessError.DATA_SELECT_ERROR.getErrorMsg());
            throw new BusinessException(EmBusinessError.DATA_SELECT_ERROR,"获取该用户所有接收订单异常");
        }
        logger.info("SUCCESS:getAllTheirList");
        return CommonReturnType.creat(orderVOS);
    }

    /**
     * 删除订单（如果不是未接状态，则返回失败）
     * @param id
     * @param order_id
     * @return
     * @throws BusinessException
     */

    @DeleteMapping("/user/{id}/myLists/{order_id}")
    @ApiOperation("删除订单（如果不是未接状态，则返回失败）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", dataType = "Integer"),
            @ApiImplicitParam(name = "order_id", value = "订单ID", dataType = "Integer")
    })
    public CommonReturnType deleteOrder(@PathVariable("id") Integer id,
                                        @PathVariable("order_id") Integer order_id)throws BusinessException{
        logger.info("PARAM: id "+id);
        logger.info("PARAM: order_id "+order_id);
        String str="";
//        try {
            str=userService.deleteOrder(id, order_id);
//        }catch (Exception e){
//            logger.info("EXCEPTION: " + EmBusinessError.DATA_DELETE_ERROR.getErrorCode() + " "
//                    + EmBusinessError.DATA_DELETE_ERROR.getErrorMsg());
//            throw new BusinessException(EmBusinessError.DATA_DELETE_ERROR,"数据删除异常");
//        }
        logger.info("SUCCESS: deleteOrder ");
        return CommonReturnType.creat(str);
    }
    /**
     * 接订单（如果不是未接状态，则返回失败）
     * @param id
     * @param order_id
     * @return
     * @throws BusinessException
     */
    @GetMapping("/user/{id}/theirLists/{order_id}")
    @ApiOperation("接订单（如果不是未接状态，则返回失败）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", dataType = "Integer"),
            @ApiImplicitParam(name = "order_id", value = "订单ID", dataType = "Integer")
    })
    public CommonReturnType putInfor(@PathVariable("id") Integer id,
                                     @PathVariable("order_id") Integer order_id)throws BusinessException{
        logger.info("PARAM: id "+id);
        logger.info("PARAM: order_id "+id);
        OrderPickVO orderPickVO=new OrderPickVO();
        orderPickVO=userService.getOrderPick(id, order_id);
        if(orderPickVO==null){
            String str="接单失败，订单非未接状态";
            logger.info("SUCCESS: putInfo ");
            return CommonReturnType.creat(str);
        }else {
            logger.info("SUCCESS: putInfo");
            return CommonReturnType.creat(orderPickVO);
        }
    }
}
