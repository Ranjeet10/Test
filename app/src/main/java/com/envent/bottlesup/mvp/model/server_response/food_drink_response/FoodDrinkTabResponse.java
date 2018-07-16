package com.envent.bottlesup.mvp.model.server_response.food_drink_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FoodDrinkTabResponse implements Serializable {

	@SerializedName("data")
	private List<FoodDrinkTab> data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private String status;

	public void setData(List<FoodDrinkTab> data){
		this.data = data;
	}

	public List<FoodDrinkTab> getData(){
		return data;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
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
			"FoodDrinkTabResponse{" + 
			"data = '" + data + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}