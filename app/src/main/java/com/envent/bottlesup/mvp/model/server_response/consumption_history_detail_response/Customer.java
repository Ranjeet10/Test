package com.envent.bottlesup.mvp.model.server_response.consumption_history_detail_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Customer implements Serializable {

    @SerializedName("name")
    private String name;

    @SerializedName("orderFrom")
    private String orderFrom;

    @SerializedName("id")
    private int id;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom;
    }

    public String getOrderFrom() {
        return orderFrom;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return
                "Customer{" +
                        "name = '" + name + '\'' +
                        ",orderFrom = '" + orderFrom + '\'' +
                        ",id = '" + id + '\'' +
                        "}";
    }
}