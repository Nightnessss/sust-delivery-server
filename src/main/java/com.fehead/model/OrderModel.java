package com.fehead.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class OrderModel {
    private Integer id;

   // @NotBlank(message = "订单更新时间不能为空")
    private Date updateTime;

  //  @NotBlank(message = "订单截至时间不能为空")
    private Date deadline;

    //@NotBlank(message = "订单目的地不能为空")
    private String destination;


//    @Max(value = 6,message = "快递点选择错误")
//    @NotNull(message = "快递地点不能为空")
//    @Min(value = 1,message = "快递点选择错误")
    private DeliveryPointModel deliveryPoint;

   // @NotBlank(message = "快递类型不能为空")
//   @Max(value = 2,message = "快递类型选择错误")
//   @Min(value = 0,message = "快递类型选择错误")
    private Integer type;

//    @NotBlank(message = "快递运费不能为空")
//    @Max(value = 5,message = "费用不得超过5元")
    private String fee;

   // @NotNull(message = "快递留言不能不填")
    private String remark;

    private UserModel publisher;
    private UserModel receiver;
    private StatusModel status;

   // @NotNull(message = "快递取货相关信息不能为空")
    private PickModel pick;

    public OrderModel(Integer id, Date updateTime, Date deadline, String destination, DeliveryPointModel deliveryPoint, Integer type, String fee, String remark, UserModel publisher, UserModel receiver, StatusModel status, PickModel pick) {
        this.id = id;
        this.updateTime = updateTime;
        this.deadline = deadline;
        this.destination = destination;
        this.deliveryPoint = deliveryPoint;
        this.type = type;
        this.fee = fee;
        this.remark = remark;
        this.publisher = publisher;
        this.receiver = receiver;
        this.status = status;
        this.pick = pick;
}

    public OrderModel() {
    }

    public PickModel getPick() {
        return pick;
    }

    public void setPick(PickModel pick) {
        this.pick = pick;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public DeliveryPointModel getDeliveryPoint() {
        return deliveryPoint;
    }

    public void setDeliveryPoint(DeliveryPointModel deliveryPoint) {
        this.deliveryPoint = deliveryPoint;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public UserModel getPublisher() {
        return publisher;
    }

    public void setPublisher(UserModel publisher) {
        this.publisher = publisher;
    }

    public UserModel getReceiver() {
        return receiver;
    }

    public void setReceiver(UserModel receiver) {
        this.receiver = receiver;
    }

    public StatusModel getStatus() {
        return status;
    }

    public void setStatus(StatusModel status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "id=" + id +
                ", updateTime=" + updateTime +
                ", deadline=" + deadline +
                ", destination='" + destination + '\'' +
                ", deliveryPoint=" + deliveryPoint +
                ", type=" + type +
                ", fee='" + fee + '\'' +
                ", remark='" + remark + '\'' +
                ", publisher=" + publisher +
                ", receiver=" + receiver +
                ", status=" + status +
                ", pick=" + pick +
                '}';
    }
}
