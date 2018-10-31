package br.com.cpg.moviesproject.model.bean;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "movie")
public class MovieBean implements Parcelable, DetailsInterface {

    @SuppressWarnings("unused")
    public static final Creator<MovieBean> CREATOR = new Creator<MovieBean>() {
        @Override
        public MovieBean createFromParcel(Parcel in) {
            return new MovieBean(in);
        }

        @Override
        public MovieBean[] newArray(int size) {
            return new MovieBean[size];
        }
    };

    @PrimaryKey(autoGenerate = false)
    private int id;
    private String title;
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    private String posterPath;
    private String overview;
    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    private Double userRating;
    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    private String releaseDate;
    private boolean favorite;

    @SuppressWarnings("unused")
    @Ignore
    public MovieBean() {

    }

    public MovieBean(int id, String title, String posterPath, String overview, Double userRating, String releaseDate, boolean favorite) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
        this.favorite = favorite;
    }

    private MovieBean(Parcel parcel) {
        id = parcel.readInt();
        title = parcel.readString();
        posterPath = parcel.readString();
        overview = parcel.readString();
        userRating = parcel.readDouble();
        releaseDate = parcel.readString();
        favorite = parcel.readByte() != 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    @SuppressWarnings("unused")
    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    @SuppressWarnings("unused")
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    @SuppressWarnings("unused")
    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getUserRating() {
        return userRating;
    }

    @SuppressWarnings("unused")
    public void setUserRating(Double userRating) {
        this.userRating = userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    @SuppressWarnings("unused")
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(posterPath);
        parcel.writeString(overview);
        parcel.writeDouble(userRating);
        parcel.writeString(releaseDate);
        parcel.writeByte((byte) (favorite ? 1 : 0));
    }
}
