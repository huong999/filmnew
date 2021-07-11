package com.example.newfilm.Model;

public class Video2 {
    String publishedAt,title,description,url,kind;
    String videoID,playlistId;
    int posittion;

    public Video2(String publishedAt, String title, String description, String url, String kind, String videoID, String playlistId, int posittion) {
        this.publishedAt = publishedAt;
        this.title = title;
        this.description = description;
        this.url = url;
        this.kind = kind;
        this.videoID = videoID;
        this.playlistId = playlistId;
        this.posittion = posittion;
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

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public int getPosittion() {
        return posittion;
    }

    public void setPosittion(int posittion) {
        this.posittion = posittion;
    }
}
