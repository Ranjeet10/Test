package com.envent.bottlesup.mvp.model.server_response.drink_item_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ServingTypesItem implements Serializable {

	@SerializedName("volume")
	private int volume;

	@SerializedName("menuPrice")
	private double menuPrice;

	@SerializedName("bottlesUpPrice")
	private double bottlesUpPrice;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	public void setVolume(int volume){
		this.volume = volume;
	}

	public int getVolume(){
		return volume;
	}

	public void setMenuPrice(int menuPrice){
		this.menuPrice = menuPrice;
	}

	public double getMenuPrice(){
		return menuPrice;
	}

	public void setBottlesUpPrice(int bottlesUpPrice){
		this.bottlesUpPrice = bottlesUpPrice;
	}

	public double getBottlesUpPrice(){
		return bottlesUpPrice;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"ServingTypesItem{" + 
			"volume = '" + volume + '\'' + 
			",menuPrice = '" + menuPrice + '\'' + 
			",bottlesUpPrice = '" + bottlesUpPrice + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}