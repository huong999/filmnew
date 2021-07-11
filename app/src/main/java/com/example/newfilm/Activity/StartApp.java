package com.example.newfilm.Activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.newfilm.Connect.MyReciver;
import com.example.newfilm.Fragment.FilmBo;
import com.example.newfilm.Fragment.FragmentAcc;
import com.example.newfilm.Fragment.FragmentHome;
import com.example.newfilm.Fragment.FragmentKHVT;
import com.example.newfilm.Fragment.FragmentLichSu;
import com.example.newfilm.Fragment.FragmentSearch;
import com.example.newfilm.Fragment.FragmentVideoOff;
import com.example.newfilm.Fragment.FragmentXemSau;
import com.example.newfilm.Map.GGmap;
import com.example.newfilm.R;
import com.facebook.stetho.Stetho;
import com.google.android.material.navigation.NavigationView;

import static com.example.newfilm.Model.AccountAttribute.ACCOUNT_FULL_NAME;
import static com.example.newfilm.Model.AccountAttribute.ACCOUNT_PHONE;
import static com.example.newfilm.Model.AccountAttribute.ACCOUNT_STATUS;
import static com.example.newfilm.Model.AccountAttribute.SHARE_PRE_NAME;

public class StartApp extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    private static final String TAG = "StartApp";
    Toolbar toolbar;
    ProgressBar cardView;
    FrameLayout frameLayout;
    TextView name, sdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_app);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARE_PRE_NAME, MODE_PRIVATE);
        String fullname = sharedPreferences.getString(ACCOUNT_FULL_NAME, "");
        Stetho.initializeWithDefaults(this);

        MyReciver myReciver = new MyReciver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
        intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        frameLayout = findViewById(R.id.frame);
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
        getFragment(FragmentHome.newInstance());
        View view =navigationView.inflateHeaderView(R.layout.header);
        name = view.findViewById(R.id.tent);
        sdt = view.findViewById(R.id.sdt);

        if (getStatus()){
            name.setText(fullname+"");
            sdt.setText(sharedPreferences.getString(ACCOUNT_PHONE,"")+"");

        }
        else {

        }

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
                toolbar.setTitle("New Film");
                getFragment(FragmentHome.newInstance());

                break;
            case R.id.xemsau:
                toolbar.setTitle("Xem sau");
                getFragment(FragmentXemSau.newInstance());
                break;
            case R.id.timkiem:

                toolbar.setTitle("Tìm kiếm");
                getFragment(FragmentSearch.newInstance());
                break;
            case R.id.filmbo:
                toolbar.setTitle("Phim dài tập");
                getFragment(FilmBo.newInstance());

                break;
            case R.id.lichsu:
                toolbar.setTitle("Lịch sử");
                getFragment(FragmentLichSu.newInstance());

                break;
            case R.id.phimKhoaHoc:
                toolbar.setTitle("Phim khoa học viễn tưởng");
                getFragment(FragmentKHVT.newInstance());

                break;
            case R.id.vitri:
//                toolbar.setTitle("Phim khoa học viễn tưởng");
//                getFragment(FragmentKHVT.newInstance());
                Intent intent = new Intent(getBaseContext(), GGmap.class);
                startActivity(intent);
                break;
            case R.id.acc:
                toolbar.setTitle("Tài khoản");
                getFragment(FragmentAcc.newInstance());
                break;
            case R.id.off:
                toolbar.setTitle("Dowloaded");
                getFragment(FragmentVideoOff.newInstance());
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getFragment(Fragment fragment) {
        try {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment)
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "getFragment: " + e.getMessage());
        }
    }

    public boolean getStatus() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARE_PRE_NAME, MODE_PRIVATE);
        boolean check = sharedPreferences.getBoolean(ACCOUNT_STATUS, false);
        return check;
    }
}