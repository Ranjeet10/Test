package com.envent.bottlesup.mvp.model.server_response.venue_list;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class VenueResponseData implements Serializable {

	@SerializedName("total")
	private int total;

	@SerializedName("_links")
	private Links links;

	@SerializedName("count")
	private int count;

	@SerializedName("total_page")
	private int totalPage;

	@SerializedName("items")
	private List<VenueDataItem> items;

	public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
	}

	public void setLinks(Links links){
		this.links = links;
	}

	public Links getLinks(){
		return links;
	}

	public void setCount(int count){
		this.count = count;
	}

	public int getCount(){
		return count;
	}

	public void setTotalPage(int totalPage){
		this.totalPage = totalPage;
	}

	public int getTotalPage(){
		return totalPage;
	}

	public void setItems(List<VenueDataItem> items){
		this.items = items;
	}

	public List<VenueDataItem> getItems(){
		return items;
	}

	@Override
 	public String toString(){
		return 
			"VenueResponseData{" +
			"total = '" + total + '\'' + 
			",_links = '" + links + '\'' + 
			",count = '" + count + '\'' + 
			",total_page = '" + totalPage + '\'' + 
			",items = '" + items + '\'' + 
			"}";
		}
}