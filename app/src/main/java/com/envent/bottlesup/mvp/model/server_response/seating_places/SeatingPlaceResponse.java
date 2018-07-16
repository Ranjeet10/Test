package com.envent.bottlesup.mvp.model.server_response.seating_places;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SeatingPlaceResponse implements Serializable {

	@SerializedName("data")
	private List<SeatingPlace> data;

	@SerializedName("status")
	private String status;

	public void setData(List<SeatingPlace> data){
		this.data = data;
	}

	public List<SeatingPlace> getData(){
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
			"SeatingPlaceResponse{" + 
			"data = '" + data + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}