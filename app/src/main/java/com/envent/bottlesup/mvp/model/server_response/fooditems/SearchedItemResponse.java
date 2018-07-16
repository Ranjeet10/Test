package com.envent.bottlesup.mvp.model.server_response.fooditems;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SearchedItemResponse implements Serializable {

    @SerializedName("data")
    private List<FoodItems> data;

    @SerializedName("status")
    private String status;

    public List<FoodItems> getData() {
        return data;
    }

    public void setData(List<FoodItems> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SearchedItemResponse{" +
                "data=" + data +
                ", status='" + status + '\'' +
                '}';
    }
}

