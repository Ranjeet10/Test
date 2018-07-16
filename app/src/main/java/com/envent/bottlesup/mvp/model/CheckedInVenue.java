package com.envent.bottlesup.mvp.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

/**
 * Created by ronem on 3/26/18.
 */

@Table(name = "checked_in")
public class CheckedInVenue extends Model {
    private final String VENUE_ID = "venue_id";
    private final String VENUE_NAME = "venue_name";
    private final String VENUE_ADDRESS = "venue_address";
    private final String LATITUDE = "latitude";
    private final String LONGITUDE = "longitude";

    @Column(name = VENUE_ID)
    private int venueId;

    @Column(name = VENUE_NAME)
    private String venueName;

    @Column(name = VENUE_ADDRESS)
    private String venueAddress;

    @Column(name = "outlet_type")
    private String outLetType;

    @Column(name = "city")
    private String city;

    @Column(name = "service_charge")
    private double serviceCharge;

    @Column(name = "vat")
    private int vat;

    @Column(name = "venue_image")
    private String venueImage;

    @Column(name = "venue_detail")
    private String venueDetail;

    @Column(name = LATITUDE)
    private String lat;

    @Column(name = LONGITUDE)
    private String longi;

    @Column(name = "tax_inclusive")
    private boolean taxInclusive;

    @Column(name = "ssid")
    private String ssid;

    @Column(name = "wifi_password")
    private String wifiPassword;

    @Column(name = "immediateContact")
    private String immediateContact;

    @Column(name = "manager")
    private String manager;

    @Column(name = "checkInCheckoutId")
    private int checkInCheckOutId;

    public CheckedInVenue() {
        super();
    }

    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getVenueAddress() {
        return venueAddress;
    }

    public void setVenueAddress(String venueAddress) {
        this.venueAddress = venueAddress;
    }

    public String getVenueImage() {
        return venueImage;
    }

    public void setVenueImage(String venueImage) {
        this.venueImage = venueImage;
    }

    public String getVenueDetail() {
        return venueDetail;
    }

    public void setVenueDetail(String venueDetail) {
        this.venueDetail = venueDetail;
    }

    public String getOutLetType() {
        return outLetType;
    }

    public void setOutLetType(String outLetType) {
        this.outLetType = outLetType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public int getVat() {
        return vat;
    }

    public void setVat(int vat) {
        this.vat = vat;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }

    public boolean isTaxInclusive() {
        return taxInclusive;
    }

    public void setTaxInclusive(boolean taxInclusive) {
        this.taxInclusive = taxInclusive;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getWifiPassword() {
        return wifiPassword;
    }

    public void setWifiPassword(String wifiPassword) {
        this.wifiPassword = wifiPassword;
    }

    public String getImmediateContact() {
        return immediateContact;
    }

    public void setImmediateContact(String immediateContact) {
        this.immediateContact = immediateContact;
    }

    public int getCheckInCheckOutId() {
        return checkInCheckOutId;
    }

    public void setCheckInCheckOutId(int checkInCheckOutId) {
        this.checkInCheckOutId = checkInCheckOutId;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public static CheckedInVenue getCheckedInVenue() {
        return new Select().from(CheckedInVenue.class).executeSingle();
    }

    public static void checkoutVenue() {
        new Delete().from(CheckedInVenue.class).execute();
    }
}
