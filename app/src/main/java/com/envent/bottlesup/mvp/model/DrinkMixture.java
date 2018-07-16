package com.envent.bottlesup.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ronem on 3/22/18.
 */

public class DrinkMixture implements Parcelable {
    private int dmId;
    private String dmName;

    public DrinkMixture(int dmId, String dmName) {
        this.dmId = dmId;
        this.dmName = dmName;
    }

    protected DrinkMixture(Parcel in) {
        dmId = in.readInt();
        dmName = in.readString();
    }

    public static final Creator<DrinkMixture> CREATOR = new Creator<DrinkMixture>() {
        @Override
        public DrinkMixture createFromParcel(Parcel in) {
            return new DrinkMixture(in);
        }

        @Override
        public DrinkMixture[] newArray(int size) {
            return new DrinkMixture[size];
        }
    };

    @Override
    public String toString() {
        return "DrinkMixture{" +
                "dmId='" + dmId + '\'' +
                ", dmName='" + dmName + '\'' +
                '}';
    }

    public int getDmId() {
        return dmId;
    }

    public String getDmName() {
        return dmName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(dmId);
        dest.writeString(dmName);
    }
}
