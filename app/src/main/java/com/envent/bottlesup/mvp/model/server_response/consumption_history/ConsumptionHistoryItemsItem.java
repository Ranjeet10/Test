package com.envent.bottlesup.mvp.model.server_response.consumption_history;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ConsumptionHistoryItemsItem implements Serializable {

    @SerializedName("venue")
    private ConsumptionHistoryVenue consumptionHistoryVenue;

    @SerializedName("amount")
    private double amount;

    @SerializedName("orderId")
    private int orderId;

    @SerializedName("createdOn")
    private String createdOn;

    public void setConsumptionHistoryVenue(ConsumptionHistoryVenue consumptionHistoryVenue) {
        this.consumptionHistoryVenue = consumptionHistoryVenue;
    }

    public ConsumptionHistoryVenue getConsumptionHistoryVenue() {
        return consumptionHistoryVenue;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
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

    @Override
    public String toString() {
        return
                "ConsumptionHistoryItemsItem{" +
                        "consumptionHistoryVenue = '" + consumptionHistoryVenue + '\'' +
                        ",amount = '" + amount + '\'' +
                        ",orderId = '" + orderId + '\'' +
                        ",createdOn = '" + createdOn + '\'' +
                        "}";
    }
}