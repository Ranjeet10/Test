package com.envent.bottlesup.mvp.model.server_response.consumption_history_detail_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ronem on 7/13/18.
 */

public class CancelOrderResponse implements Serializable {

    @SerializedName("data")
    private Data data;

    @SerializedName("status")
    private String status;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
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
        return "CancelOrderResponse{" +
                "data=" + data +
                ", status='" + status + '\'' +
                '}';
    }
}
