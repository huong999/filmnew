package com.example.newfilm.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;

import com.example.newfilm.Fragment.LoginFragmrnt;
import com.example.newfilm.R;
import com.example.newfilm.databinding.ActivityDangNhapBinding;

public class DangNhap extends AppCompatActivity {

    private static final String TAG = "";
ActivityDangNhapBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dang_nhap);
        getFragment(LoginFragmrnt.newInstance());
    }
    public void getFragment(Fragment fragment){
        try {
            getSupportFragmentManager().beginTransaction().replace(R.id.dangnhap,fragment).commit();
        }catch (Exception e){
            e.printStackTrace();
            Log.d(TAG,"getFragment"+e.getMessage());
        }
    }
}