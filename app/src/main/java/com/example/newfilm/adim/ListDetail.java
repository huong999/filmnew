package com.example.newfilm.adim;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newfilm.Model.AttributeVideo;
import com.example.newfilm.R;
import com.example.newfilm.SQL.SqlHelper;
import com.example.newfilm.databinding.ActivityListUserBinding;

public class ListDetail extends AppCompatActivity {
//    private ActivityListUserBinding binding;
//    private String category;
//    private AdapterListUser adapter;
//    private SqlHelper sqlHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_user);
//        sqlHelper = new SqlHelper(this);
//        adapter = new AdapterListUser(sqlHelper.getAllAccount(), this);
//        binding.rc1.setAdapter(adapter);
//        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(getBaseContext(), 10, RecyclerView.VERTICAL, false);
//        binding.rc1.setLayoutManager(layoutManager1);
//        category = getIntent().getStringExtra(AttributeVideo.USER);
//        if (category.equals(AttributeVideo.USER)) {
//
//        }
    }
}
