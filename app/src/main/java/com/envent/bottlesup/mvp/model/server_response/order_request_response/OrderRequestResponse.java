package com.envent.bottlesup.mvp.model.server_response.order_request_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderRequestResponse implements Serializable {

	@SerializedName("balance")
	private double balance;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private String status;


	@SerializedName("id")
	private int orderId;

	public void setBalance(double balance){
		this.balance = balance;
	}

	public double getBalance(){
		return balance;
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

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	@Override
 	public String toString(){
		return 
			"OrderRequestResponse{" + 
			"balance = '" + balance + '\'' + 
			",message = '" + message + '\'' + 
			",orderId = '" + orderId + '\'' +
			",status = '" + status + '\'' +
			"}";
		}
}