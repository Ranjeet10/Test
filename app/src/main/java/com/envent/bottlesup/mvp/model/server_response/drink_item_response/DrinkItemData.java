package com.envent.bottlesup.mvp.model.server_response.drink_item_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DrinkItemData implements Serializable {

	@SerializedName("total")
	private int total;

	@SerializedName("_links")
	private Links links;

	@SerializedName("count")
	private int count;

	@SerializedName("total_page")
	private int totalPage;

	@SerializedName("items")
	private List<DrinkItemsItem> items;

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

	public void setItems(List<DrinkItemsItem> items){
		this.items = items;
	}

	public List<DrinkItemsItem> getItems(){
		return items;
	}

	@Override
 	public String toString(){
		return 
			"DrinkItemData{" +
			"total = '" + total + '\'' + 
			",_links = '" + links + '\'' + 
			",count = '" + count + '\'' + 
			",total_page = '" + totalPage + '\'' + 
			",items = '" + items + '\'' + 
			"}";
		}
}