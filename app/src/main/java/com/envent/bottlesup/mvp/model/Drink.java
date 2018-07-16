package com.envent.bottlesup.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by ronem on 3/19/18.
 */

public class Drink implements Parcelable {
    private int venueId;
    private int categoryId;
    private int drinkItemId;
    private String drinkImageUrl;
    private String drinkName;
    private double drinkPricePerUnit;
    private double drinkPricePerUnitBottlesUp;
    private List<DrinkOption> drinkOptions;
    private List<DrinkMixture> drinkMixtures;

    public Drink(int venueId, int categoryId, int drinkItemId, String drinkImageUrl, String drinkName, List<DrinkOption> drinkOptions,
                 List<DrinkMixture> drinkMixtures, double drinkPricePerUnit, double drinkPricePerUnitBottlesUp) {
        this.venueId = venueId;
        this.categoryId = categoryId;
        this.drinkItemId = drinkItemId;
        this.drinkImageUrl = drinkImageUrl;
        this.drinkName = drinkName;
        this.drinkOptions = drinkOptions;
        this.drinkMixtures = drinkMixtures;
        this.drinkPricePerUnit = drinkPricePerUnit;
        this.drinkPricePerUnitBottlesUp = drinkPricePerUnitBottlesUp;
    }

    protected Drink(Parcel in) {
        venueId = in.readInt();
        categoryId = in.readInt();
        drinkItemId = in.readInt();
        drinkImageUrl = in.readString();
        drinkName = in.readString();
        drinkPricePerUnit = in.readDouble();
        drinkPricePerUnitBottlesUp = in.readDouble();
        drinkOptions = in.createTypedArrayList(DrinkOption.CREATOR);
        drinkMixtures = in.createTypedArrayList(DrinkMixture.CREATOR);
    }

    public static final Creator<Drink> CREATOR = new Creator<Drink>() {
        @Override
        public Drink createFromParcel(Parcel in) {
            return new Drink(in);
        }

        @Override
        public Drink[] newArray(int size) {
            return new Drink[size];
        }
    };

    public List<DrinkOption> getDrinkOptions() {
        return drinkOptions;
    }

    public List<DrinkMixture> getDrinkMixtures() {
        return drinkMixtures;
    }

    public int getVenueId() {
        return venueId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getDrinkItemId() {
        return drinkItemId;
    }

    public String getDrinkImageUrl() {
        return drinkImageUrl;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public double getDrinkPricePerUnit() {
        return drinkPricePerUnit;
    }

    public double getDrinkPricePerUnitBottlesUp() {
        return drinkPricePerUnitBottlesUp;
    }

    @Override
    public String toString() {
        return "Drink{" +
                "venueId=" + venueId +
                "categoryId=" + categoryId +
                "drinkItemId=" + drinkItemId +
                ", drinkImageUrl='" + drinkImageUrl + '\'' +
                ", drinkName='" + drinkName + '\'' +
                ", drinkPricePerUnit=" + drinkPricePerUnit +
                ", drinkPricePerUnitBottlesUp=" + drinkPricePerUnitBottlesUp +
                ", drinkOptions=" + drinkOptions +
                ", drinkMixtures=" + drinkMixtures +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(venueId);
        dest.writeInt(categoryId);
        dest.writeInt(drinkItemId);
        dest.writeString(drinkImageUrl);
        dest.writeString(drinkName);
        dest.writeDouble(drinkPricePerUnit);
        dest.writeDouble(drinkPricePerUnitBottlesUp);
        dest.writeTypedList(drinkOptions);
        dest.writeTypedList(drinkMixtures);
    }
}
