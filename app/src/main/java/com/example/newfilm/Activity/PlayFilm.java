package com.example.newfilm.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.newfilm.Model.Video;
import com.example.newfilm.R;
import com.example.newfilm.Utils.Utils;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_film);
        videos = new ArrayList<>();
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.playFilm);
        youTubePlayerView.initialize(API_KEY, this);
        Intent intent = getIntent();
        VIDEO_ID = intent.getStringExtra("idvideo");
        name = intent.getStringExtra("name");
        idPlayList = intent.getStringExtra("idPlayList");
        des = intent.getStringExtra("description");
        time = intent.getStringExtra("time");
        img = intent.getStringExtra("img");
        kind = intent.getStringExtra("kind");
        video = new Video(time,name,des,img,kind,VIDEO_ID,idPlayList);
        Utils2.getList().add(video);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (null == youTubePlayer) return;

        // Start buffering
        if (!b) {
            youTubePlayer.loadVideo(VIDEO_ID);
        }
        youTubePlayer.seekToMillis(1000);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Failed to initialize.", Toast.LENGTH_LONG).show();
    }
}