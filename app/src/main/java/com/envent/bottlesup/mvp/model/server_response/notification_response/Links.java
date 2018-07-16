package com.envent.bottlesup.mvp.model.server_response.notification_response;

import com.google.gson.annotations.SerializedName;

public class Links{

	@SerializedName("last")
	private String last;

	@SerializedName("self")
	private String self;

	@SerializedName("first")
	private String first;

	public void setLast(String last){
		this.last = last;
	}

	public String getLast(){
		return last;
	}

	public void setSelf(String self){
		this.self = self;
	}

	public String getSelf(){
		return self;
	}

	public void setFirst(String first){
		this.first = first;
	}

	public String getFirst(){
		return first;
	}

	@Override
 	public String toString(){
		return 
			"Links{" + 
			"last = '" + last + '\'' + 
			",self = '" + self + '\'' + 
			",first = '" + first + '\'' + 
			"}";
		}
}