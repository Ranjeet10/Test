package com.envent.bottlesup.mvp.model.server_response.fooditems;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class FoodItems implements Parcelable {

	@SerializedName("menuPrice")
	private Double menuPrice;

	@SerializedName("itemName")
	private String itemName;

	@SerializedName("featured")
	private boolean featured;

	@SerializedName("taxInclusive")
	private boolean taxInclusive;

	@SerializedName("venueToBottlesUpPrice")
	private double venueToBottlesUpPrice;

	@SerializedName("bottlesUpPrice")
	private double bottlesUpToCustomerPrice;

	@SerializedName("id")
	private int id;

	@SerializedName("itemDescription")
	private String itemDescription;

	@SerializedName("itemImage")
	private String itemImage;

	protected FoodItems(Parcel in) {
		menuPrice = in.readDouble();
		itemName = in.readString();
		featured = in.readByte() != 0;
		taxInclusive = in.readByte() != 0;
		venueToBottlesUpPrice = in.readDouble();
		bottlesUpToCustomerPrice = in.readDouble();
		id = in.readInt();
		itemDescription = in.readString();
		itemImage = in.readString();
	}

	public static final Creator<FoodItems> CREATOR = new Creator<FoodItems>() {
		@Override
		public FoodItems createFromParcel(Parcel in) {
			return new FoodItems(in);
		}

		@Override
		public FoodItems[] newArray(int size) {
			return new FoodItems[size];
		}
	};

	public void setMenuPrice(Double menuPrice){
		this.menuPrice = menuPrice;
	}

	public Double getMenuPrice(){
		return menuPrice;
	}

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

	public void setTaxInclusive(boolean taxInclusive){
		this.taxInclusive = taxInclusive;
	}

	public boolean isTaxInclusive(){
		return taxInclusive;
	}

	public void setVenueToBottlesUpPrice(double venueToBottlesUpPrice){
		this.venueToBottlesUpPrice = venueToBottlesUpPrice;
	}

	public double getVenueToBottlesUpPrice(){
		return venueToBottlesUpPrice;
	}

	public void setBottlesUpToCustomerPrice(double bottlesUpToCustomerPrice){
		this.bottlesUpToCustomerPrice = bottlesUpToCustomerPrice;
	}

	public double getBottlesUpToCustomerPrice(){
		return bottlesUpToCustomerPrice;
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
			"FoodItems{" +
			"menuPrice = '" + menuPrice + '\'' + 
			",itemName = '" + itemName + '\'' + 
			",featured = '" + featured + '\'' + 
			",taxInclusive = '" + taxInclusive + '\'' + 
			",venueToBottlesUpPrice = '" + venueToBottlesUpPrice + '\'' + 
			",bottlesUpToCustomerPrice = '" + bottlesUpToCustomerPrice + '\'' + 
			",id = '" + id + '\'' + 
			",itemDescription = '" + itemDescription + '\'' + 
			",itemImage = '" + itemImage + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeDouble(menuPrice);
		dest.writeString(itemName);
		dest.writeByte((byte) (featured ? 1 : 0));
		dest.writeByte((byte) (taxInclusive ? 1 : 0));
		dest.writeDouble(venueToBottlesUpPrice);
		dest.writeDouble(bottlesUpToCustomerPrice);
		dest.writeInt(id);
		dest.writeString(itemDescription);
		dest.writeString(itemImage);
	}
}