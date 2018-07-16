package com.envent.bottlesup.mvp.model.server_response.profile_image;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProfileImageResponse implements Serializable {

	@SerializedName("data")
	private Data data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private String status;

	public Data getData() {
		return data;
	}

	public String getMessage() {
		return message;
	}

	public String getStatus() {
		return status;
	}

	public void setData(Data data){
		this.data = data;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public void setStatus(String status){
		this.status = status;
	}
}