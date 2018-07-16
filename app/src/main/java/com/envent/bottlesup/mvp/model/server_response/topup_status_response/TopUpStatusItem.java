package com.envent.bottlesup.mvp.model.server_response.topup_status_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TopUpStatusItem implements Serializable {

    @SerializedName("topUpOn")
    private String topUpOn;

    @SerializedName("venue")
    private String venue;

    @SerializedName("amount")
    private int amount;

    @SerializedName("id")
    private int id;

    @SerializedName("particulars")
    private String particulars;

    @SerializedName("type")
    private String type;

    @SerializedName("status")
    private String status;

    public void setTopUpOn(String topUpOn) {
        this.topUpOn = topUpOn;
    }

    public String getTopUpOn() {
        return topUpOn;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getVenue() {
        return venue;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return
                "ItemsItem{" +
                        "topUpOn = '" + topUpOn + '\'' +
                        ",venue = '" + venue + '\'' +
                        ",amount = '" + amount + '\'' +
                        ",id = '" + id + '\'' +
                        ",particulars = '" + particulars + '\'' +
                        ",type = '" + type + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}