package com.fehead.controller;

import com.fehead.controller.vo.OrderDetailVO;
import com.fehead.controller.vo.OrderListVO;
import com.fehead.error.BusinessException;
import com.fehead.error.EmBusinessError;
import com.fehead.model.DeliveryPointModel;
import com.fehead.model.StatusModel;
import com.fehead.response.CommonReturnType;
import com.fehead.service.OrderService;
import com.fehead.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.omg.CORBA.ORB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/v1.0/SUSTDelivery/view")
@RestController
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class OrderController extends BaseController{
    public static Log logger = LogFactory.getLog(OrderController.class);
    @Autowired
    OrderService orderService;

    /**
     * 分页查找订单（只返回可接订单）
     * @param page
     * @param pagesize
     * @return
     * @throws BusinessException
     */
    @GetMapping("/order/lists/{point}")
    @ApiOperation("分页查找订单（只返回可接订单）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "point", value = "快递点", dataType = "Integer"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "Integer"),
            @ApiImplicitParam(name = "pagesize", value = "页大小", dataType = "Integer")
    })
    public CommonReturnType selectItemByStatusOk(@PathVariable(value = "point") Integer point,
                                                 @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                 @RequestParam(value = "pagesize", defaultValue = "6") Integer pagesize) throws BusinessException {
        logger.info("PARAM: point " + point);
        logger.info("PARAM: page " + page);
        logger.info("PARAM: pagesize " + pagesize);

        List<OrderListVO> realOrderListVOList=new ArrayList<>();
        try {
            realOrderListVOList=orderService.selectItemByStatusOk(page,pagesize, point);
            logger.info("SUCCESS: selectItemByStatusOk" );
        }catch (Exception ex){
            logger.info("EXCEPTION: " + EmBusinessError.DATA_SELECT_ERROR.getErrorCode() + " " + EmBusinessError.DATA_SELECT_ERROR.getErrorMsg());
            throw new BusinessException(EmBusinessError.DATA_SELECT_ERROR);
        }
        return  CommonReturnType.creat(realOrderListVOList);

    }


    @PostMapping("/order/lists")
    @ApiOperation("分页关键字搜索订单（只返回可接订单）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "search", value = "查找", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "Integer"),
            @ApiImplicitParam(name = "pagesize", value = "页大小", dataType = "Integer")
    })
    public CommonReturnType search(@RequestParam(value = "search", defaultValue = "") String search,
                                   @RequestParam(value = "page", defaultValue = "1") Integer page,
                                   @RequestParam(value = "pagesize", defaultValue = "6") Integer pagesize) throws BusinessException {

        logger.info("PARAM: search " + search);
        logger.info("PARAM: page " + page);
        logger.info("PARAM: pagesize " + pagesize);

        List<OrderListVO> realOrderListVOList=new ArrayList<>();
        try {
            realOrderListVOList=orderService.search(page,pagesize, search);
            logger.info("SUCCESS: selectItemByStatusOk" );
        }catch (Exception ex){
            logger.info("EXCEPTION: " + EmBusinessError.DATA_SELECT_ERROR.getErrorCode() + " " + EmBusinessError.DATA_SELECT_ERROR.getErrorMsg());
            throw new BusinessException(EmBusinessError.DATA_SELECT_ERROR);
        }
        return  CommonReturnType.creat(realOrderListVOList);
    }


    /**
     * 修改订单状态
     * @param orderId
     * @param statusId
     * @return
     * @throws BusinessException
     */
    @GetMapping("/order/lists/{order_id}/status/{status_id}")
    @ApiOperation("修改订单状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order_id", value = "订单ID", dataType = "Integer"),
            @ApiImplicitParam(name = "status_id", value = "订单状态（0异常1未接2已接3未完成4已完成5已删除）", dataType = "Integer")
    })
    public CommonReturnType updateItemStatus(@PathVariable("order_id") Integer orderId,
                                             @PathVariable("status_id") Integer statusId) throws BusinessException {

        logger.info("PARAM: 订单ID " + orderId);
        logger.info("PARAM: 订单状态ID " + statusId);
        try{
            orderService.updateStatus(orderId,statusId);
            logger.info("SUCCESS: updateItemStatus" );
        }catch (Exception ex){
            logger.info("EXCEPTION: " + EmBusinessError.DATA_UPDATE_ERROR.getErrorCode() + " " + EmBusinessError.DATA_UPDATE_ERROR.getErrorMsg());
            throw new BusinessException(EmBusinessError.DATA_UPDATE_ERROR);
        }
        return CommonReturnType.creat(null);
    }

    /**
     * 通过订单id查找订单
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/order/lists/{id}/info")
    @ApiOperation("通过订单id查找订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order_id", value = "订单ID", dataType = "Integer")
    })
    public CommonReturnType getOrderInfo(@PathVariable("id") Integer id) throws Exception {
        logger.info("PARAM: id "+id);
        OrderDetailVO orderDetailVO=new OrderDetailVO();
        try {
            orderDetailVO = orderService.getDetailOrder(id);
        }catch (Exception e){
            logger.info("EXCEPTION: " + EmBusinessError.DATA_SELECT_ERROR.getErrorCode() + " "
                    + EmBusinessError.DATA_SELECT_ERROR.getErrorMsg());
            throw new BusinessException(EmBusinessError.DATA_SELECT_ERROR,"获取详细订单异常");
        }
        logger.info("SUCCESS: putOrderInfo ");
        return CommonReturnType.creat(orderDetailVO);

    }


    @GetMapping("/order/lists/delivery")
    @ApiOperation("获取所有快递点")
    public CommonReturnType getAllDeliveryPoint() {
        List<DeliveryPointModel> deliveryPointModels = orderService.selectAllDeliveryPoint();

        return CommonReturnType.creat(deliveryPointModels);
    }

}
