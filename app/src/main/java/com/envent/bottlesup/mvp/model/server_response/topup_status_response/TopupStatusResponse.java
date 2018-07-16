package com.envent.bottlesup.mvp.model.server_response.topup_status_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TopupStatusResponse implements Serializable {

    @SerializedName("data")
    private TopUpStatusData data;

    @SerializedName("status")
    private String status;

    public void setData(TopUpStatusData data) {
        this.data = data;
    }

    public TopUpStatusData getData() {
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
                "TopupStatusResponse{" +
                        "data = '" + data + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}