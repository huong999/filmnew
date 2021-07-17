package com.example.newfilm.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.Lifecycle;

import com.example.newfilm.Model.TimeReplayVideoUtils;
import com.example.newfilm.Model.Video;
import com.example.newfilm.Model.VideoReplay;
import com.example.newfilm.R;
import com.example.newfilm.Utils.Utils2;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class PlayFilm extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    String API_KEY = "AIzaSyCFi6Ctl4thP43H3PkOYJyV9ipOYDiSPAY";
    String VIDEO_ID = "", name, idPlayList, des, time, kind, img;
    Video video;
    List<Video> videos;
    private Integer currentTimeVideo = 0;
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer youTubePlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_film);
        videos = new ArrayList<>();
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.playFilm);
        youTubePlayerView.initialize(API_KEY, this);
        Intent intent = getIntent();
        VIDEO_ID = intent.getStringExtra("idvideo");
        name = intent.getStringExtra("name");
        idPlayList = intent.getStringExtra("idPlayList");
        des = intent.getStringExtra("description");
        time = intent.getStringExtra("time");
        img = intent.getStringExtra("img");
        kind = intent.getStringExtra("kind");
        video = new Video(time, name, des, img, kind, VIDEO_ID, idPlayList, "");
        Utils2.getList().add(video);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (null == youTubePlayer) return;

        // Start buffering
        /*if (!b) {
            youTubePlayer.loadVideo(VIDEO_ID);
            youTubePlayer.seekToMillis(5000);
        }*/
        if (TimeReplayVideoUtils.timeVideos.size() == 0) {
            youTubePlayer.loadVideo(VIDEO_ID, 0);
        } else {
            for (int i = 0; i < TimeReplayVideoUtils.timeVideos.size(); i++) {
                if (TimeReplayVideoUtils.timeVideos.get(i).getVideoID().equals(VIDEO_ID)) {
                    youTubePlayer.loadVideo(VIDEO_ID, TimeReplayVideoUtils.timeVideos.get(i).getTimeReplay());
                }
            }
        }

        this.youTubePlayer = youTubePlayer;
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Failed to initialize.", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        VideoReplay videoReplay = null;
        if (TimeReplayVideoUtils.timeVideos.size() != 0) {
            for (int i = 0; i < TimeReplayVideoUtils.timeVideos.size(); i++) {
                if (TimeReplayVideoUtils.timeVideos.get(i).getVideoID().equals(VIDEO_ID)) {
                    videoReplay = new VideoReplay(youTubePlayer.getCurrentTimeMillis(), VIDEO_ID);
                    TimeReplayVideoUtils.timeVideos.set(i, videoReplay);
                } else {
                    videoReplay = new VideoReplay(youTubePlayer.getCurrentTimeMillis(), VIDEO_ID);
                    TimeReplayVideoUtils.timeVideos.add(videoReplay);
                }
            }
        } else {
            videoReplay = new VideoReplay(youTubePlayer.getCurrentTimeMillis(), VIDEO_ID);
            TimeReplayVideoUtils.timeVideos.add(videoReplay);
        }

        //Log.d("KMFG", youTubePlayer.getCurrentTimeMillis() + "==");
    }
}