package com.envent.bottlesup.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ronem on 3/22/18.
 */

public class DrinkOption implements Parcelable {
    private int doID;
    private String doName;

    public DrinkOption(int doID, String doName) {
        this.doID = doID;
        this.doName = doName;
    }

    protected DrinkOption(Parcel in) {
        doID = in.readInt();
        doName = in.readString();
    }

    public static final Creator<DrinkOption> CREATOR = new Creator<DrinkOption>() {
        @Override
        public DrinkOption createFromParcel(Parcel in) {
            return new DrinkOption(in);
        }

        @Override
        public DrinkOption[] newArray(int size) {
            return new DrinkOption[size];
        }
    };

    public int getDoID() {
        return doID;
    }

    public String getDoName() {
        return doName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(doID);
        dest.writeString(doName);
    }

    @Override
    public String toString() {
        return "DrinkOption{" +
                "doID='" + doID + '\'' +
                ", doName='" + doName + '\'' +
                '}';
    }
}
