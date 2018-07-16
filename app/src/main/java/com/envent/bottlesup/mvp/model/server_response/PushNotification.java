package com.envent.bottlesup.mvp.model.server_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PushNotification implements Serializable {

	@SerializedName("amount")
	private int amount;

	@SerializedName("message")
	private String message;

	@SerializedName("title")
	private String title;

	@SerializedName("createdOn")
	private String createdOn;

	public void setAmount(int amount){
		this.amount = amount;
	}

	public int getAmount(){
		return amount;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setCreatedOn(String createdOn){
		this.createdOn = createdOn;
	}

	public String getCreatedOn(){
		return createdOn;
	}

	@Override
 	public String toString(){
		return 
			"PushNotification{" + 
			"amount = '" + amount + '\'' + 
			",message = '" + message + '\'' + 
			",title = '" + title + '\'' + 
			",createdOn = '" + createdOn + '\'' + 
			"}";
		}
}