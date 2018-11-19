package br.com.cpg.moviesproject.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ReviewBean implements Parcelable, DetailsInterface {

    @SuppressWarnings("unused")
    public static final Creator<ReviewBean> CREATOR = new Creator<ReviewBean>() {
        @Override
        public ReviewBean createFromParcel(Parcel in) {
            return new ReviewBean(in);
        }

        @Override
        public ReviewBean[] newArray(int size) {
            return new ReviewBean[size];
        }
    };
    private String author;
    private String content;

    @SuppressWarnings("unused")
    public ReviewBean() {

    }

    private ReviewBean(Parcel parcel) {
        author = parcel.readString();
        content = parcel.readString();
    }

    public String getAuthor() {
        return author;
    }

    @SuppressWarnings("unused")
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    @SuppressWarnings("unused")
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(author);
        parcel.writeString(content);
    }
}
