package com.envent.bottlesup.mvp.model.server_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CheckInCheckOut implements Serializable {

    @SerializedName("checkInCheckOutId")
    private int checkInCheckOutId;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private String status;

    public void setCheckInCheckOutId(int checkInCheckOutId) {
        this.checkInCheckOutId = checkInCheckOutId;
    }

    public int getCheckInCheckOutId() {
        return checkInCheckOutId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
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
                "CheckInCheckOut{" +
                        "checkInCheckOutId = '" + checkInCheckOutId + '\'' +
                        ",message = '" + message + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}