package com.envent.bottlesup.mvp.model.server_response.venue_list;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Event implements Serializable {

	@SerializedName("bannerImage")
	private String bannerImage;

	@SerializedName("additionalImages")
	private List<AdditionalImagesItem> additionalImages;

	@SerializedName("ends")
	private String ends;

	@SerializedName("name")
	private String name;

	@SerializedName("details")
	private String details;

	@SerializedName("id")
	private int id;

	@SerializedName("startsFrom")
	private String startsFrom;

	public void setBannerImage(String bannerImage){
		this.bannerImage = bannerImage;
	}

	public String getBannerImage(){
		return bannerImage;
	}

	public void setAdditionalImages(List<AdditionalImagesItem> additionalImages){
		this.additionalImages = additionalImages;
	}

	public List<AdditionalImagesItem> getAdditionalImages(){
		return additionalImages;
	}

	public void setEnds(String ends){
		this.ends = ends;
	}

	public String getEnds(){
		return ends;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setDetails(String details){
		this.details = details;
	}

	public String getDetails(){
		return details;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setStartsFrom(String startsFrom){
		this.startsFrom = startsFrom;
	}

	public String getStartsFrom(){
		return startsFrom;
	}

	@Override
 	public String toString(){
		return 
			"Event{" + 
			"bannerImage = '" + bannerImage + '\'' + 
			",additionalImages = '" + additionalImages + '\'' + 
			",ends = '" + ends + '\'' + 
			",name = '" + name + '\'' + 
			",details = '" + details + '\'' + 
			",id = '" + id + '\'' + 
			",startsFrom = '" + startsFrom + '\'' + 
			"}";
		}
}