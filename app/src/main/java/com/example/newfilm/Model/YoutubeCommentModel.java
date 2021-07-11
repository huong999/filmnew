package com.example.newfilm.Model;

import android.os.Parcel;
import android.os.Parcelable;



public class YoutubeCommentModel implements Parcelable {
    private String title = "";
    private String comment = "";
    private String publishedAt = "";
    private String thumbnail = "";
    private String video_id = "";
    int like, dislike, numcomment, view;

    public YoutubeCommentModel(int like, int dislike, int numcomment, int view) {
        this.like = like;
        this.dislike = dislike;
        this.numcomment = numcomment;
        this.view = view;
    }

    public YoutubeCommentModel(String title, String comment, String publishedAt, String thumbnail, String video_id, int like, int dislike, int numcomment, int view) {
        this.title = title;
        this.comment = comment;
        this.publishedAt = publishedAt;
        this.thumbnail = thumbnail;
        this.video_id = video_id;
        this.like = like;
        this.dislike = dislike;
        this.numcomment = numcomment;
        this.view = view;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public int getNumcomment() {
        return numcomment;
    }

    public void setNumcomment(int numcomment) {
        this.numcomment = numcomment;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public static Creator<YoutubeCommentModel> getCREATOR() {
        return CREATOR;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(comment);
        dest.writeString(publishedAt);
        dest.writeString(thumbnail);
        dest.writeString(video_id);
    }

    public YoutubeCommentModel() {
        super();
    }


    protected YoutubeCommentModel(Parcel in) {
        this();
        readFromParcel(in);
    }

    public void readFromParcel(Parcel in) {
        this.title = in.readString();
        this.comment = in.readString();
        this.publishedAt = in.readString();
        this.thumbnail = in.readString();
        this.video_id = in.readString();

    }

    public static final Creator<YoutubeCommentModel> CREATOR = new Creator<YoutubeCommentModel>() {
        @Override
        public YoutubeCommentModel createFromParcel(Parcel in) {
            return new YoutubeCommentModel(in);
        }

        @Override
        public YoutubeCommentModel[] newArray(int size) {
            return new YoutubeCommentModel[size];
        }
    };

    public YoutubeCommentModel(String title, String comment, String publishedAt, String thumbnail, String video_id) {
            this.title = title;
        this.comment = comment;
        this.publishedAt = publishedAt;
        this.thumbnail = thumbnail;
        this.video_id = video_id;
    }
}
