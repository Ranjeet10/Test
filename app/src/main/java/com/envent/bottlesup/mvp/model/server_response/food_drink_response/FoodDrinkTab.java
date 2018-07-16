package com.envent.bottlesup.mvp.model.server_response.food_drink_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FoodDrinkTab implements Serializable {

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    public FoodDrinkTab(int id,String name){
        this.id = id;
        this.name = name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return
                "FoodDrinkTab{" +
                        "name = '" + name + '\'' +
                        ",id = '" + id + '\'' +
                        "}";
    }
}