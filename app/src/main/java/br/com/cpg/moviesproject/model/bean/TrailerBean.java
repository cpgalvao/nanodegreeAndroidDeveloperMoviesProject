package br.com.cpg.moviesproject.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TrailerBean implements Parcelable {

    private String name;

    private String key;

    @SuppressWarnings("unused")
    public TrailerBean() {

    }

    private TrailerBean(Parcel parcel) {
        name = parcel.readString();
        key = parcel.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(key);
    }

    @SuppressWarnings("unused")
    public static final Creator<TrailerBean> CREATOR = new Creator<TrailerBean>() {
        @Override
        public TrailerBean createFromParcel(Parcel in) {
            return new TrailerBean(in);
        }

        @Override
        public TrailerBean[] newArray(int size) {
            return new TrailerBean[size];
        }
    };
}