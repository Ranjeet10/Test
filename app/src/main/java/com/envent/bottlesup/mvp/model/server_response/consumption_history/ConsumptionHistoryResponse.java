package com.envent.bottlesup.mvp.model.server_response.consumption_history;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ConsumptionHistoryResponse implements Serializable {

    @SerializedName("data")
    private ConsumptionHistoryData consumptionHistoryData;

    @SerializedName("status")
    private String status;

    public void setConsumptionHistoryData(ConsumptionHistoryData consumptionHistoryData) {
        this.consumptionHistoryData = consumptionHistoryData;
    }

    public ConsumptionHistoryData getConsumptionHistoryData() {
        return consumptionHistoryData;
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
                "ConsumptionHistoryResponse{" +
                        "consumptionHistoryData = '" + consumptionHistoryData + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}