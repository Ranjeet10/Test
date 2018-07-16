package com.envent.bottlesup.mvp.model.server_response.venue_list;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VenueResponse implements Serializable {

	@SerializedName("data")
	private VenueResponseData venueResponseData;

	@SerializedName("status")
	private String status;

	public void setVenueResponseData(VenueResponseData venueResponseData){
		this.venueResponseData = venueResponseData;
	}

	public VenueResponseData getVenueResponseData(){
		return venueResponseData;
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
			"VenueResponse{" + 
			"venueResponseData = '" + venueResponseData + '\'' +
			",status = '" + status + '\'' + 
			"}";
		}
}