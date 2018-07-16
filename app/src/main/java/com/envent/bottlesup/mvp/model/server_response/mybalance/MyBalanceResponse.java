package com.envent.bottlesup.mvp.model.server_response.mybalance;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MyBalanceResponse implements Serializable {

    @SerializedName("data")
    private MyBalance myBalance;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private String status;

    public void setMyBalance(MyBalance myBalance) {
        this.myBalance = myBalance;
    }

    public MyBalance getMyBalance() {
        return myBalance;
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
                "MyBalanceResponse{" +
                        "myBalance = '" + myBalance + '\'' +
                        ",message = '" + message + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}