package com.example.newfilm.adim;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.newfilm.Connect.MyReciver;
import com.example.newfilm.Fragment.FragmentHome;
import com.example.newfilm.Model.AttributeVideo;
import com.example.newfilm.R;
import com.google.android.material.navigation.NavigationView;

public class MenuAd extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    ProgressBar cardView;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_ad);

        MyReciver myReciver = new MyReciver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
        intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        frameLayout = findViewById(R.id.frame1);
        cardView = findViewById(R.id.cardview);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                cardView.setVisibility(View.GONE);
                frameLayout.setVisibility(View.VISIBLE);
            }
        }, 2000);
        //   registerReceiver(myReciver, intentFilter);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationview);
        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getFragment(new ReportActivity().newInstance());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.home:
                getFragment(new ReportActivity().newInstance());
                break;
            case R.id.acc:
                getFragment(new InfoAdmin().newInstance());
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getFragment(Fragment fragment) {
        try {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame1, fragment)
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}