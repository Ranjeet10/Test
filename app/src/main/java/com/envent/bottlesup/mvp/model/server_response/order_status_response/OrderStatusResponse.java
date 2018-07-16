package com.envent.bottlesup.mvp.model.server_response.order_status_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderStatusResponse implements Serializable {

    @SerializedName("data")
    private OrderStatusData orderStatusData;

    @SerializedName("status")
    private String status;

    public void setData(OrderStatusData orderStatusData) {
        this.orderStatusData = orderStatusData;
    }

    public OrderStatusData getOrderStatusData() {
        return orderStatusData;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return
                "OrderStatusResponse{" +
                        "orderStatusData = '" + orderStatusData + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}