package com.envent.bottlesup.mvp.model.server_response.mybalance;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MyBalance implements Serializable {

	@SerializedName("balance")
	private Double balance;

	public void setBalance(double balance){
		this.balance = balance;
	}

	public Double getBalance(){
		return balance;
	}

	@Override
 	public String toString(){
		return 
			"MyBalance{" +
			"balance = '" + balance + '\'' + 
			"}";
		}
}