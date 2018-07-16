package com.envent.bottlesup.mvp.model.server_response.login_register_verified_blocked;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {

	@SerializedName("data")
	private LoginRegisterData registerData;

	@SerializedName("status")
	private String status;

	public void setRegisterData(LoginRegisterData registerData){
		this.registerData = registerData;
	}

	public LoginRegisterData getRegisterData(){
		return registerData;
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
			"RegisterResponse{" + 
			"registerData = '" + registerData + '\'' +
			",status = '" + status + '\'' + 
			"}";
		}
}