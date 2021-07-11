package com.example.newfilm.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Video implements Parcelable, Serializable {
    String publishedAt, title, description, url, kind;
    String videoID, playlistId;
    String date;
    int id;
    int idAcc;



    public Video(String publishedAt, String title, String description, String url, String kind, String videoID, String playlistId, String date, int idAcc) {

        this.publishedAt = publishedAt;
        this.title = title;
        this.description = description;
        this.url = url;
        this.kind = kind;
        this.videoID = videoID;
        this.playlistId = playlistId;
        this.date = date;
        this.idAcc = idAcc;
    }

    public int getIdAcc() {
        return idAcc;
    }

    public void setIdAcc(int idAcc) {
        this.idAcc = idAcc;
    }

    protected Video(Parcel in) {
        publishedAt = in.readString();
        title = in.readString();
        description = in.readString();
        url = in.readString();
        kind = in.readString();
        videoID = in.readString();
        playlistId = in.readString();
        date = in.readString();
        id = in.readInt();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Video(String publishedAt, String title, String description, String url, String kind, String videoID, String playlistId, String date, int id, int idAcc) {
        this.publishedAt = publishedAt;
        this.title = title;
        this.description = description;
        this.url = url;
        this.kind = kind;
        this.videoID = videoID;
        this.playlistId = playlistId;
        this.date = date;
        this.id = id;
        this.idAcc = idAcc;
    }

    public Video(int idAcc, String publishedAt, String title, String description, String url, String kind, String videoID, String playlistId) {
        this.publishedAt = publishedAt;
        this.title = title;
        this.description = description;
        this.url = url;
        this.kind = kind;
        this.videoID = videoID;
        this.playlistId = playlistId;

        this.idAcc = idAcc;
    }

    public Video(String publishedAt, String title, String description, String url, String kind, String videoID, String playlistId, int id) {
        this.publishedAt = publishedAt;
        this.title = title;
        this.description = description;
        this.url = url;
        this.kind = kind;
        this.videoID = videoID;
        this.playlistId = playlistId;
        this.id = id;
    }

    public Video(String publishedAt, String title, String description, String url, String kind, String videoID, String playlistId) {
        this.publishedAt = publishedAt;
        this.title = title;
        this.description = description;
        this.url = url;
        this.kind = kind;
        this.videoID = videoID;
        this.playlistId = playlistId;
        this.date = "";
    }

    public Video(String title, String description, String videoID, String playlistId) {
        this.title = title;
        this.description = description;

        this.videoID = videoID;
        this.playlistId = playlistId;
    }

    public Video(String publishedAt, String title, String description, String url, String kind, String videoID, String playlistId, int id, int idAcc) {
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(publishedAt);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(url);
        dest.writeString(kind);
        dest.writeString(videoID);
        dest.writeString(playlistId);
        dest.writeString(date);
        dest.writeInt(id);
    }
}
