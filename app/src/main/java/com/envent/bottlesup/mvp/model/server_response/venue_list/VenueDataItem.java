package com.envent.bottlesup.mvp.model.server_response.venue_list;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class VenueDataItem implements Serializable {

	@SerializedName("immediateContact")
	private Object immediateContact;

	@SerializedName("image")
	private String image;

	@SerializedName("address")
	private String address;

	@SerializedName("city")
	private String city;

	@SerializedName("latitude")
	private String latitude;

	@SerializedName("vat")
	private int vat;

	@SerializedName("description")
	private String description;

	@SerializedName("wifiPassword")
	private Object wifiPassword;

	@SerializedName("outletType")
	private String outletType;

	@SerializedName("taxInclusive")
	private boolean taxInclusive;

	@SerializedName("serviceCharge")
	private int serviceCharge;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("events")
	private List<Event> events;

	@SerializedName("longitude")
	private String longitude;

	private String distance;

	public void setImmediateContact(Object immediateContact){
		this.immediateContact = immediateContact;
	}

	public Object getImmediateContact(){
		return immediateContact;
	}

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setLatitude(String latitude){
		this.latitude = latitude;
	}

	public String getLatitude(){
		return latitude;
	}

	public void setVat(int vat){
		this.vat = vat;
	}

	public int getVat(){
		return vat;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setWifiPassword(Object wifiPassword){
		this.wifiPassword = wifiPassword;
	}

	public Object getWifiPassword(){
		return wifiPassword;
	}

	public void setOutletType(String outletType){
		this.outletType = outletType;
	}

	public String getOutletType(){
		return outletType;
	}

	public void setTaxInclusive(boolean taxInclusive){
		this.taxInclusive = taxInclusive;
	}

	public boolean isTaxInclusive(){
		return taxInclusive;
	}

	public void setServiceCharge(int serviceCharge){
		this.serviceCharge = serviceCharge;
	}

	public int getServiceCharge(){
		return serviceCharge;
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

	public void setEvents(List<Event> events){
		this.events = events;
	}

	public List<Event> getEvents(){
		return events;
	}

	public void setLongitude(String longitude){
		this.longitude = longitude;
	}

	public String getLongitude(){
		return longitude;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	@Override
 	public String toString(){
		return 
			"VenueDataItem{" +
			"immediateContact = '" + immediateContact + '\'' + 
			",image = '" + image + '\'' + 
			",address = '" + address + '\'' + 
			",city = '" + city + '\'' + 
			",latitude = '" + latitude + '\'' + 
			",vat = '" + vat + '\'' + 
			",description = '" + description + '\'' + 
			",wifiPassword = '" + wifiPassword + '\'' + 
			",outletType = '" + outletType + '\'' + 
			",taxInclusive = '" + taxInclusive + '\'' + 
			",serviceCharge = '" + serviceCharge + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",events = '" + events + '\'' + 
			",longitude = '" + longitude + '\'' + 
			"}";
		}
}