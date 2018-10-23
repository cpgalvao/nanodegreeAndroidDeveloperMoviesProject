package br.com.cpg.moviesproject.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class HeaderBean implements Parcelable, DetailsInterface {

    @SuppressWarnings("unused")
    public static final Creator<HeaderBean> CREATOR = new Creator<HeaderBean>() {
        @Override
        public HeaderBean createFromParcel(Parcel in) {
            return new HeaderBean(in);
        }

        @Override
        public HeaderBean[] newArray(int size) {
            return new HeaderBean[size];
        }
    };
    private String title;

    @SuppressWarnings("unused")
    public HeaderBean(String title) {
        this.title = title;
    }

    private HeaderBean(Parcel parcel) {
        title = parcel.readString();
    }

    public String getTitle() {
        return title;
    }

    @SuppressWarnings("unused")
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
    }
}
