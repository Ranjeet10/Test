package com.envent.bottlesup.mvp.model.server_response.drink_item_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DrinkItemsItem implements Serializable {

	@SerializedName("itemName")
	private String itemName;

	@SerializedName("featured")
	private boolean featured;

	@SerializedName("mixers")
	private List<MixersItem> mixers;

	@SerializedName("servingTypes")
	private List<ServingTypesItem> servingTypes;

	@SerializedName("id")
	private int id;

	@SerializedName("itemDescription")
	private String itemDescription;

	@SerializedName("itemImage")
	private String itemImage;

	public void setItemName(String itemName){
		this.itemName = itemName;
	}

	public String getItemName(){
		return itemName;
	}

	public void setFeatured(boolean featured){
		this.featured = featured;
	}

	public boolean isFeatured(){
		return featured;
	}

	public void setMixers(List<MixersItem> mixers){
		this.mixers = mixers;
	}

	public List<MixersItem> getMixers(){
		return mixers;
	}

	public void setServingTypes(List<ServingTypesItem> servingTypes){
		this.servingTypes = servingTypes;
	}

	public List<ServingTypesItem> getServingTypes(){
		return servingTypes;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setItemDescription(String itemDescription){
		this.itemDescription = itemDescription;
	}

	public String getItemDescription(){
		return itemDescription;
	}

	public void setItemImage(String itemImage){
		this.itemImage = itemImage;
	}

	public String getItemImage(){
		return itemImage;
	}

	@Override
 	public String toString(){
		return 
			"DrinkItemsItem{" +
			"itemName = '" + itemName + '\'' + 
			",featured = '" + featured + '\'' + 
			",mixers = '" + mixers + '\'' + 
			",servingTypes = '" + servingTypes + '\'' + 
			",id = '" + id + '\'' + 
			",itemDescription = '" + itemDescription + '\'' + 
			",itemImage = '" + itemImage + '\'' + 
			"}";
		}
}