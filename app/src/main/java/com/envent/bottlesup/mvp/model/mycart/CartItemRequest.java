package com.envent.bottlesup.mvp.model.mycart;

import java.util.List;

/**
 * Created by ronem on 4/19/18.
 */

public class CartItemRequest {
    private int venueId;
    private int seatingPlace;
    private String onTheWay;
    private double orderAmount;
    private List<RequestFoodItem> foods;
    private List<RequestDrinkItem> drinks;

    public CartItemRequest(int venueId, int seatingPlace, String onTheWay,double orderAmount,List<RequestFoodItem> foods, List<RequestDrinkItem> drinks) {
        this.venueId = venueId;
        this.seatingPlace = seatingPlace;
        this.onTheWay = onTheWay;
        this.orderAmount = orderAmount;
        this.foods = foods;
        this.drinks = drinks;
    }

    public int getVenueId() {
        return venueId;
    }

    public int getSeatingPlace() {
        return seatingPlace;
    }

    public String getOnTheWay() {
        return onTheWay;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public List<RequestFoodItem> getFoods() {
        return foods;
    }

    public List<RequestDrinkItem> getDrinks() {
        return drinks;
    }
}
