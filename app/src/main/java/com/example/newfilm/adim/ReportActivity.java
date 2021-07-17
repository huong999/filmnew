package com.example.newfilm.adim;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.newfilm.Model.AttributeVideo;
import com.example.newfilm.Model.Video;
import com.example.newfilm.R;
import com.example.newfilm.SQL.SqlHelper;
import com.example.newfilm.databinding.ActivityReportBinding;

import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends AppCompatActivity {
    private SqlHelper sqlHelper;
    private ActivityReportBinding binding;
    List<Video> arrayList, list, listHan, listViet, listAc, listhh, listKinh;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report);
        sqlHelper = new SqlHelper(this);
        arrayList = new ArrayList<>();
        listHan = new ArrayList<>();
        listViet = new ArrayList<>();
        listAc = new ArrayList<>();
        listhh = new ArrayList<>();
        listKinh = new ArrayList<>();
        arrayList = sqlHelper.getAllFilm();
        binding.txtProgress.setText(sqlHelper.getAllAccount().size() + "");
        binding.txtProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReportActivity.this, ListAdDetail.class);
                i.putExtra(AttributeVideo.USER, AttributeVideo.USER);
                startActivity(i);
            }
        });
        binding.idfilmhd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReportActivity.this, ListAdDetail.class);
                i.putExtra(AttributeVideo.USER, AttributeVideo.ACTION);
                startActivity(i);
            }
        });
        binding.filmvn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReportActivity.this, ListAdDetail.class);
                i.putExtra(AttributeVideo.USER, AttributeVideo.VN);
                startActivity(i);
            }
        });

        binding.filmhq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReportActivity.this, ListAdDetail.class);
                i.putExtra(AttributeVideo.USER, AttributeVideo.Han);
                startActivity(i);
            }
        });

        binding.idfilmkd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReportActivity.this, ListAdDetail.class);
                i.putExtra(AttributeVideo.USER, AttributeVideo.KING_DI);
                startActivity(i);
            }
        });


        for (int i = 0; i < sqlHelper.getAllFilm().size(); i++) {
            //      Toast.makeText(getContext(), sqlHelper.getAllFilm().get(i).getCategory(), Toast.LENGTH_SHORT).show();
            if (sqlHelper.getAllFilm().get(i).getCategory().trim().equals("vn")) {
                listViet.add(sqlHelper.getAllFilm().get(i));
            } else if (sqlHelper.getAllFilm().get(i).getCategory().trim().equals(AttributeVideo.ACTION)) {
                listAc.add(sqlHelper.getAllFilm().get(i));
            } else if (sqlHelper.getAllFilm().get(i).getCategory().trim().equals(AttributeVideo.Han)) {
                listHan.add(sqlHelper.getAllFilm().get(i));
            } else if (sqlHelper.getAllFilm().get(i).getCategory().trim().equals(AttributeVideo.KING_DI)) {
                listKinh.add(sqlHelper.getAllFilm().get(i));
            } else if (sqlHelper.getAllFilm().get(i).getCategory().trim().equals(AttributeVideo.HOAT_HINH)) {
                listhh.add(sqlHelper.getAllFilm().get(i));
            }
        }
        binding.filmvn.setText(listViet.size() + "");
        binding.filmhq.setText(listHan.size() + "");
        binding.filmhh.setText(listhh.size() + "");
        binding.idfilmkd.setText(listKinh.size() + "");
        binding.filmkhvt.setText(listAc.size() + "");
        binding.idfilmhd.setText(listAc.size() + "");

    }
}
