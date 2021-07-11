package com.example.newfilm.Activity;

import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.newfilm.Fragment.FragmentVideoOff;
import com.example.newfilm.R;
import com.khizar1556.mkvideoplayer.MKPlayerActivity;


public class PlayOff extends AppCompatActivity {

    private static final String TAG = "";
    Long urll;
    String tt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_off);
      //
        Intent intent = getIntent();
        tt = intent.getStringExtra("ttvideo");
        Uri videoUri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, intent.getExtras().getLong("videoIda"));
        MKPlayerActivity.configPlayer(this).play(videoUri.toString());
        Log.d("videois", videoUri.toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //getFragment(FragmentVideoOff.newInstance());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}