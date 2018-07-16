package com.envent.bottlesup.mvp.model.server_response.venue_list;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AdditionalImagesItem implements Serializable {

    @SerializedName("image")
    private String image;

    @SerializedName("id")
    private int id;

    @SerializedName("order")
    private int order;

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return
                "AdditionalImagesItem{" +
                        "image = '" + image + '\'' +
                        ",id = '" + id + '\'' +
                        ",order = '" + order + '\'' +
                        "}";
    }
}