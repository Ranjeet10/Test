package com.envent.bottlesup.mvp.model.server_response.consumption_history_detail_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ItemsItem implements Serializable {

    @SerializedName("total")
    private double total;

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("rate")
    private double rate;

    @SerializedName("servingType")
    private String servingType;

    @SerializedName("name")
    private String name;

    @SerializedName("mixer")
    private String mixer;

    @SerializedName("id")
    private int id;

    @SerializedName("remarks")
    private String remarks;

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotal() {
        return total;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getRate() {
        return rate;
    }

    public void setServingType(String servingType) {
        this.servingType = servingType;
    }

    public String getServingType() {
        return servingType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setMixer(String mixer) {
        this.mixer = mixer;
    }

    public String getMixer() {
        return mixer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarks() {
        return remarks;
    }

    @Override
    public String toString() {
        return
                "ItemsItem{" +
                        "total = '" + total + '\'' +
                        ",quantity = '" + quantity + '\'' +
                        ",rate = '" + rate + '\'' +
                        ",servingType = '" + servingType + '\'' +
                        ",name = '" + name + '\'' +
                        ",mixer = '" + mixer + '\'' +
                        ",id = '" + id + '\'' +
                        ",remarks = '" + remarks + '\'' +
                        "}";
    }
}