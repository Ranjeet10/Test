package com.envent.bottlesup.mvp.model.server_response.saving_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Data implements Serializable {

	@SerializedName("totalExpenses")
	private int totalExpenses;

	@SerializedName("savings")
	private double savings;

	public void setTotalExpenses(int totalExpenses){
		this.totalExpenses = totalExpenses;
	}

	public int getTotalExpenses(){
		return totalExpenses;
	}

	public void setSavings(double savings){
		this.savings = savings;
	}

	public double getSavings(){
		return savings;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"totalExpenses = '" + totalExpenses + '\'' + 
			",savings = '" + savings + '\'' + 
			"}";
		}
}