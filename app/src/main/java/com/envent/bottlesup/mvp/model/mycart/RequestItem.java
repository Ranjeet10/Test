package com.envent.bottlesup.mvp.model.mycart;

/**
 * Created by ronem on 4/20/18.
 */

public abstract class RequestItem {
    private int itemId;
    private int quantity;
    private String remark;

    public RequestItem(int itemId, int quantity,String remark) {
        this.itemId = itemId;
        this.quantity = quantity;
        this.remark = remark;
    }

    public int getItemId() {
        return itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getRemark() {
        return remark;
    }
}
