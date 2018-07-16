package com.envent.bottlesup.mvp.model.server_response.fooditems;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FoodItemResponse implements Serializable {

	@SerializedName("data")
	private FoodItemData data;

	@SerializedName("status")
	private String status;

	public void setFoodItemData(FoodItemData data){
		this.data = data;
	}

	public FoodItemData getFoodItemData(){
		return data;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"FoodItemResponse{" + 
			"data = '" + data + '\'' +
			",status = '" + status + '\'' + 
			"}";
		}
}