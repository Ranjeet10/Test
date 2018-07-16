package com.envent.bottlesup.mvp.model.server_response.consumption_history_detail_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ConsumptionHistoryDetailResponse implements Serializable {

    @SerializedName("data")
    private Data data;

    @SerializedName("status")
    private String status;

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
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
                "ConsumptionHistoryDetailResponse{" +
                        "data = '" + data + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}