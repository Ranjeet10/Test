package com.envent.bottlesup.mvp.model.server_response.login_register_verified_blocked;

import com.google.gson.annotations.SerializedName;

public class LoginResponse{

	@SerializedName("data")
	private LoginRegisterData loginRegisterData;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private String status;

	public void setLoginRegisterData(LoginRegisterData loginRegisterData){
		this.loginRegisterData = loginRegisterData;
	}

	public LoginRegisterData getLoginRegisterData(){
		return loginRegisterData;
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

	@Override
 	public String toString(){
		return 
			"LoginResponse{" + 
			"loginRegisterData = '" + loginRegisterData + '\'' +
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}