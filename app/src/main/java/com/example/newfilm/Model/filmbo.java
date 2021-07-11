package com.example.newfilm.Model;

public class filmbo {
    String  totalResults, publishedAt, playlistId,videoId,title,description,url;

    public filmbo(String totalResults, String publishedAt, String playlistId, String videoId, String title, String description, String url) {
        this.totalResults = totalResults;
        this.publishedAt = publishedAt;
        this.playlistId = playlistId;
        this.videoId = videoId;
        this.title = title;
        this.description = description;
        this.url = url;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
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
}
