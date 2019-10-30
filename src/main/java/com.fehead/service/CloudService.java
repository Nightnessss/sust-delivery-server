package com.fehead.service;


import com.fehead.model.DeliveryPointModel;
import com.fehead.model.OrderModel;
import com.fehead.model.UserModel;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "DELIVERY-DATA")
//@Hystrix
public interface CloudService {

    @GetMapping("/api/v1.0/SUSTDelivery/data/lists")
    public List<OrderModel> getAllOrder();

    @PutMapping("/api/v1.0/SUSTDelivery/data/order/{order_id}/status/{status_id}")
    public void updateStatus(@PathVariable("order_id") Integer orderId,
                             @PathVariable("status_id") Integer statusId);

    @GetMapping("/api/v1.0/SUSTDelivery/data/user/{id}/order/{order_id}")
    public boolean updateStatus2(@PathVariable("id") Integer id,
                             @PathVariable("order_id") Integer orderId);

    @GetMapping("/api/v1.0/SUSTDelivery/data/order/{order_id}")
    public OrderModel getOrderById(@PathVariable("order_id") Integer orderId);


//    @PostMapping("/api/v1.0/SUSTDelivery/data/order")
//    public void commitOrder(@RequestParam(value = "destination") String destination,
//                            @RequestParam(value = "deadline") Date deadline,
//                            @RequestParam(value = "deliveryPoint.id") Integer deliverPointId,
//                            @RequestParam(value = "type") Integer type,
//                            @RequestParam(value = "fee") String fee,
//                            @RequestParam(value = "remark") String remark,
//                            @RequestParam(value = "pick.pickName") String pickName,
//                            @RequestParam(value = "pick.tailNumber") String tailNumber,
//                            @RequestParam(value = "pick.pickCode") String pickCode,
//                            @RequestParam(value = "publisher.id") Integer userId,
//                            @RequestParam(value = "status.updateTime") Date statusUpdateTime,
//                            @RequestParam(value = "status.status") Integer status,
//                            @RequestParam(value = "updateTime") Date UpdateTime
//                            ) throws BusinessException;

    @PostMapping("/api/v1.0/SUSTDelivery/data/order")
    public OrderModel commitOrder(@RequestBody OrderModel orderModel);

    @GetMapping("/api/v1.0/SUSTDelivery/data/user/{id}")
    public UserModel getUserById(@PathVariable("id") Integer id);

    @GetMapping("/api/v1.0/SUSTDelivery/data/order/publisher/{id}")
    public List<OrderModel> getPublisherOrder(@PathVariable("id") Integer id,
                                              @RequestParam("page") Integer page,
                                              @RequestParam("pagesize") Integer pagesize
                                                    );

    @GetMapping("/api/v1.0/SUSTDelivery/data/order/lists/status")
    public List<OrderModel> getAcceptableOrder(@RequestParam("point") Integer point,
                                               @RequestParam("page") Integer page,
                                               @RequestParam("pagesize") Integer pagesize);

    @PostMapping("/api/v1.0/SUSTDelivery/data/order/lists")
    public List<OrderModel> getAcceptableOrderByDestination(@RequestParam("search") String search,
                                               @RequestParam("page") Integer page,
                                               @RequestParam("pagesize") Integer pagesize);

    @GetMapping("/api/v1.0/SUSTDelivery/data/order/lists/delivery/{delivery_id}")
    public DeliveryPointModel getDeliveryById(@PathVariable("delivery_id") Integer deliveryId);

    @PutMapping("/api/v1.0/SUSTDelivery/data/order")
    public void updateItem(@RequestBody OrderModel orderModel);

    @RequestMapping(value = "api/v1.0/SUSTDelivery/data/order/receiver/{id}",method = RequestMethod.GET)
    public List<OrderModel> getMyReceiverOrder(@PathVariable("id")Integer id,@RequestParam("page")Integer page,@RequestParam("pagesize")Integer pagesize);

    @GetMapping("/api/v1.0/SUSTDelivery/data/login/thirdPartyId")
    public UserModel alreadyLogin(@RequestParam("thirdPartyId") String thirdPartyId);

    @PostMapping("/api/v1.0/SUSTDelivery/data/login/addUser")
    public int addUser(@RequestBody UserModel userModel);

    @PostMapping("/api/v1.0/SUSTDelivery/data/login/addInfo")
    public int addInfo(@RequestBody UserModel userModel);

    @GetMapping("/api/v1.0/SUSTDelivery/data/order/lists/delivery")
    public List<DeliveryPointModel> getAllDeliveryPoint();
}