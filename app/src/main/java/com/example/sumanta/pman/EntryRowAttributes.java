package com.example.sumanta.pman;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sumanta on 18-06-2017.
 */

public class EntryRowAttributes implements Parcelable {
    private String entryName;
    private String entryValue;

    public EntryRowAttributes(String entryName, String entryValue) {
        this.entryName = entryName;
        this.entryValue = entryValue;
    }

    protected EntryRowAttributes(Parcel in) {
        super();
        entryName = in.readString();
        entryValue = in.readString();
    }

    public static final Creator<EntryRowAttributes> CREATOR = new Creator<EntryRowAttributes>() {
        @Override
        public EntryRowAttributes createFromParcel(Parcel in) {
            return new EntryRowAttributes(in);
        }

        @Override
        public EntryRowAttributes[] newArray(int size) {
            return new EntryRowAttributes[size];
        }
    };

    public String getEntryName() {
        return entryName;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }

    public String getEntryValue() {
        return entryValue;
    }

    public void setEntryValue(String entryValue) {
        this.entryValue = entryValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(entryName);
        dest.writeString(entryValue);
    }
}
