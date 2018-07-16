package com.envent.bottlesup.mvp.model.server_response.event_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EventVenue implements Serializable {

    @SerializedName("address")
    private String address;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return
                "EventVenue{" +
                        "address = '" + address + '\'' +
                        ",name = '" + name + '\'' +
                        ",id = '" + id + '\'' +
                        "}";
    }
}