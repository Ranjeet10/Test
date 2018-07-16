package com.envent.bottlesup.mvp.model.server_response.topup_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TopupHistoryDataItem implements Serializable {

	@SerializedName("topUpOn")
	private String topUpOn;

	@SerializedName("venue")
	private String venue;

	@SerializedName("amount")
	private double amount;

	@SerializedName("id")
	private int id;

	@SerializedName("particulars")
	private String particulars;

	public void setTopUpOn(String topUpOn){
		this.topUpOn = topUpOn;
	}

	public String getTopUpOn(){
		return topUpOn;
	}

	public void setVenue(String venue){
		this.venue = venue;
	}

	public String getVenue(){
		return venue;
	}

	public void setAmount(int amount){
		this.amount = amount;
	}

	public double getAmount(){
		return amount;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setParticulars(String particulars){
		this.particulars = particulars;
	}

	public String getParticulars(){
		return particulars;
	}

	@Override
 	public String toString(){
		return 
			"TopupHistoryDataItem{" +
			"topUpOn = '" + topUpOn + '\'' + 
			",venue = '" + venue + '\'' + 
			",amount = '" + amount + '\'' + 
			",id = '" + id + '\'' + 
			",particulars = '" + particulars + '\'' + 
			"}";
		}
}