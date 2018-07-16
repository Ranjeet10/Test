package com.envent.bottlesup.mvp.model.server_response.drink_item_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DrinkItemResponse implements Serializable {

    @SerializedName("data")
    private DrinkItemData data;

    @SerializedName("status")
    private String status;

    public void setData(DrinkItemData data) {
        this.data = data;
    }

    public DrinkItemData getData() {
        return data;
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
                "DrinkItemResponse{" +
                        "data = '" + data + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}