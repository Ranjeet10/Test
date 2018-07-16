package com.envent.bottlesup.mvp.model.server_response.order_status_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderStatusItem implements Serializable {

    @SerializedName("venue")
    private OrderedVenue venue;

    @SerializedName("amount")
    private int amount;

    @SerializedName("orderId")
    private int orderId;

    @SerializedName("createdOn")
    private String createdOn;

    @SerializedName("status")
    private String status;

    public void setVenue(OrderedVenue venue) {
        this.venue = venue;
    }

    public OrderedVenue getVenue() {
        return venue;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedOn() {
        return createdOn;
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
                "ItemsItem{" +
                        "venue = '" + venue + '\'' +
                        ",amount = '" + amount + '\'' +
                        ",orderId = '" + orderId + '\'' +
                        ",createdOn = '" + createdOn + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}