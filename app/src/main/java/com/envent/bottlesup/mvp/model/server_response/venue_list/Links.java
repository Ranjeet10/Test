package com.envent.bottlesup.mvp.model.server_response.venue_list;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Links implements Serializable {

	@SerializedName("next")
	private String next;

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}
}