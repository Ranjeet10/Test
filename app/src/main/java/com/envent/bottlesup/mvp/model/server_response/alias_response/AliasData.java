package com.envent.bottlesup.mvp.model.server_response.alias_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AliasData implements Serializable {

	@SerializedName("alias")
	private String alias;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	public void setAlias(String alias){
		this.alias = alias;
	}

	public String getAlias(){
		return alias;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	@Override
 	public String toString(){
		return 
			"AliasData{" +
			"alias = '" + alias + '\'' + 
			",description = '" + description + '\'' + 
			",id = '" + id + '\'' + 
			",title = '" + title + '\'' + 
			"}";
		}
}