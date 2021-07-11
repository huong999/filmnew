package com.example.newfilm.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.newfilm.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class Playlist extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    String API_KEY = "AIzaSyCFi6Ctl4thP43H3PkOYJyV9ipOYDiSPAY";
    String VIDEO_ID="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.playFilm);
        youTubePlayerView.initialize(API_KEY, this);
        Intent intent = getIntent();
        VIDEO_ID = intent.getStringExtra("idPlayList");
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if(null== youTubePlayer) return;

        // Start buffering
        if (!b) {
            youTubePlayer.loadPlaylist(VIDEO_ID);
        }
        youTubePlayer.seekToMillis(1000);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Failed to initialize.", Toast.LENGTH_LONG).show();
    }
}