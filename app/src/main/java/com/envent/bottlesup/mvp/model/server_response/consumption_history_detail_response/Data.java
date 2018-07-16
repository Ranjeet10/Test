package com.envent.bottlesup.mvp.model.server_response.consumption_history_detail_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {

    @SerializedName("venue")
    private Venue venue;

    @SerializedName("serviceCharge")
    private double serviceCharge;

    @SerializedName("orderId")
    private int orderId;

    @SerializedName("grandTotal")
    private double grandTotal;

    @SerializedName("vat")
    private double vat;

    @SerializedName("subTotal")
    private double subTotal;

    @SerializedName("orderedOn")
    private String orderedOn;

    @SerializedName("items")
    private List<ItemsItem> items;

    @SerializedName("customer")
    private Customer customer;

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setServiceCharge(double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public double getServiceCharge() {
        return serviceCharge;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public double getVat() {
        return vat;
    }

    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setOrderedOn(String orderedOn) {
        this.orderedOn = orderedOn;
    }

    public String getOrderedOn() {
        return orderedOn;
    }

    public void setItems(List<ItemsItem> items) {
        this.items = items;
    }

    public List<ItemsItem> getItems() {
        return items;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    @Override
    public String toString() {
        return
                "Data{" +
                        "venue = '" + venue + '\'' +
                        ",serviceCharge = '" + serviceCharge + '\'' +
                        ",orderId = '" + orderId + '\'' +
                        ",grandTotal = '" + grandTotal + '\'' +
                        ",vat = '" + vat + '\'' +
                        ",subTotal = '" + subTotal + '\'' +
                        ",orderedOn = '" + orderedOn + '\'' +
                        ",items = '" + items + '\'' +
                        ",customer = '" + customer + '\'' +
                        "}";
    }
}