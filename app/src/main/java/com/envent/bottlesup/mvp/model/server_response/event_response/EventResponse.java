package com.envent.bottlesup.mvp.model.server_response.event_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class EventResponse implements Serializable {

	@SerializedName("data")
	private List<EventData> data;

	@SerializedName("status")
	private String status;

	public void setData(List<EventData> data){
		this.data = data;
	}

	public List<EventData> getData(){
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
			"EventResponse{" + 
			"data = '" + data + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}