package com.envent.bottlesup.mvp.model.server_response;

/**
 * Created by ronem on 6/13/18.
 */

public class PendingBalanceResponse {
    private String status;
    private double pendingBalance;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPendingBalance() {
        return pendingBalance;
    }

    public void setPendingBalance(double pendingBalance) {
        this.pendingBalance = pendingBalance;
    }
}
