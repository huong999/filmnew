package com.example.newfilm.Model;

public class VideoReplay {
    private Integer timeReplay;
    private String videoID;

    public VideoReplay(Integer timeReplay, String videoID) {
        this.timeReplay = timeReplay;
        this.videoID = videoID;
    }

    public Integer getTimeReplay() {
        return timeReplay;
    }

    public void setTimeReplay(Integer timeReplay) {
        this.timeReplay = timeReplay;
    }

    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }
}
