package com.envent.bottlesup.mvp.model.server_response.topup_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TopupHistoryResponse implements Serializable {

	@SerializedName("data")
	private TopupHistoryData data;

	@SerializedName("status")
	private String status;

	public void setData(TopupHistoryData data){
		this.data = data;
	}

	public TopupHistoryData getData(){
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
			"TopupHistoryResponse{" + 
			"TopupHistoryData = '" + data + '\'' +
			",status = '" + status + '\'' + 
			"}";
		}
}