package com.envent.bottlesup.mvp.model.server_response.notification_response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ItemsItem implements Parcelable{

	@SerializedName("id")
	private int id;

	@SerializedName("from")
	private String from;

	@SerializedName("title")
	private String title;

	@SerializedName("type")
	private String type;

	@SerializedName("message")
	private String message;

	@SerializedName("createdOn")
	private String createdOn;

	protected ItemsItem(Parcel in) {
		id = in.readInt();
		title = in.readString();
		type = in.readString();
		message = in.readString();
		createdOn = in.readString();
	}

	public ItemsItem(int id, String from, String title, String type, String message, String createdOn) {
		this.id = id;
		this.from = from;
		this.title = title;
		this.type = type;
		this.message = message;
		this.createdOn = createdOn;
	}

	public static final Creator<ItemsItem> CREATOR = new Creator<ItemsItem>() {
		@Override
		public ItemsItem createFromParcel(Parcel in) {
			return new ItemsItem(in);
		}

		@Override
		public ItemsItem[] newArray(int size) {
			return new ItemsItem[size];
		}
	};

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setFrom(String from){
		this.from = from;
	}

	public String getFrom() {
		return from;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setCreatedOn(String createdOn){
		this.createdOn = createdOn;
	}

	public String getCreatedOn(){
		return createdOn;
	}

	@Override
 	public String toString(){
		return 
			"ItemsItem{" + 
			"id = '" + id + '\'' + 
			",title = '" + title + '\'' + 
			",type = '" + type + '\'' + 
			",message = '" + message + '\'' + 
			",createdOn = '" + createdOn + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(title);
		dest.writeString(type);
		dest.writeString(message);
		dest.writeString(createdOn);
	}
}