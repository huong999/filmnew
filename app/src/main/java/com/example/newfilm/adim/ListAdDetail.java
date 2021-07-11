package com.example.newfilm.adim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.newfilm.Model.AttributeVideo;
import com.example.newfilm.R;
import com.example.newfilm.SQL.SqlHelper;
import com.example.newfilm.databinding.ActivityListAdDetailBinding;
import com.example.newfilm.databinding.ActivityListUserBinding;

public class ListAdDetail extends AppCompatActivity {
    private ActivityListAdDetailBinding binding;
    private String category;
    private AdapterListUser adapter;
    private SqlHelper sqlHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_ad_detail);
        sqlHelper = new SqlHelper(this);
        Toast.makeText(this, "size: " + sqlHelper.getAllAccount().size(), Toast.LENGTH_SHORT).show();
        adapter = new AdapterListUser(sqlHelper.getAllAccount(), this);
        binding.rc.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(getBaseContext(), 10, RecyclerView.VERTICAL, false);
        binding.rc.setLayoutManager(layoutManager1);
        category = getIntent().getStringExtra(AttributeVideo.USER);
        if (category.equals(AttributeVideo.USER)) {

        }
    }
}