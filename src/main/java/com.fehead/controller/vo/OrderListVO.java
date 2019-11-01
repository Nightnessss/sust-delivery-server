package com.fehead.controller.vo;

import com.fehead.model.DeliveryPointModel;
import com.fehead.model.StatusModel;

import java.util.Date;

public class OrderListVO {

    private Integer id;
    private String displayName;
    private Date updateTime;
    private Date deadline;
    private String destination;
    private DeliveryPointModel deliveryPoint;
    private String fee;
    private StatusModel status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public StatusModel getStatus() {
        return status;
    }

    public void setStatus(StatusModel status) {
        this.status = status;
    }
}
