package com.envent.bottlesup.mvp.model.mycart;

/**
 * Created by ronem on 4/20/18.
 */

public class RequestDrinkItem extends RequestItem {
    private int servingTypeId;
    private int mixerId;


    public RequestDrinkItem(int itemId, int quantity, String remark, int servingType, int mixtureType) {
        super(itemId, quantity, remark);
        this.servingTypeId = servingType;
        this.mixerId = mixtureType;
    }

    public int getServingType() {
        return servingTypeId;
    }

    public int getMixtureType() {
        return mixerId;
    }


}
