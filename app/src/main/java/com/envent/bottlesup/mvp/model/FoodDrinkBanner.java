package com.envent.bottlesup.mvp.model;

/**
 * Created by ronem on 3/18/18.
 */

public class FoodDrinkBanner {
    private String imageUrl;
    private String foodId;

    public FoodDrinkBanner(String imageUrl, String foodId) {
        this.imageUrl = imageUrl;
        this.foodId = foodId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getFoodId() {
        return foodId;
    }
}
