package com.example.newfilm.adim;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.newfilm.Model.AttributeVideo;
import com.example.newfilm.Model.Video;
import com.example.newfilm.R;
import com.example.newfilm.SQL.SqlHelper;
import com.example.newfilm.databinding.ActivityReportBinding;

import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends Fragment {
    private SqlHelper sqlHelper;
    private ActivityReportBinding binding;
    List<Video> arrayList, list, listHan, listViet, listAc, listhh, listKinh;
    public static ReportActivity newInstance() {
        Bundle bundle = new Bundle();
        ReportActivity acc = new ReportActivity();
        acc.setArguments(bundle);
        return acc;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable  Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_report, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sqlHelper = new SqlHelper(getContext());
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
                Intent i = new Intent(getContext(), ListAdDetail.class);
                i.putExtra(AttributeVideo.USER, AttributeVideo.USER);
                startActivity(i);
            }
        });
        binding.idfilmhd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ListAdDetail.class);
                i.putExtra(AttributeVideo.USER, AttributeVideo.ACTION);
                startActivity(i);
            }
        });
        binding.filmvn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ListAdDetail.class);
                i.putExtra(AttributeVideo.USER, AttributeVideo.VN);
                startActivity(i);
            }
        });

        binding.filmhq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ListAdDetail.class);
                i.putExtra(AttributeVideo.USER, AttributeVideo.Han);
                startActivity(i);
            }
        });

        binding.idfilmkd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ListAdDetail.class);
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
        binding.idfilmhd.setText(listAc.size() + "");
    }

//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_report);
//        sqlHelper = new SqlHelper(this);
//        arrayList = new ArrayList<>();
//        listHan = new ArrayList<>();
//        listViet = new ArrayList<>();
//        listAc = new ArrayList<>();
//        listhh = new ArrayList<>();
//        listKinh = new ArrayList<>();
//        arrayList = sqlHelper.getAllFilm();
//        binding.txtProgress.setText(sqlHelper.getAllAccount().size() + "");
//        binding.txtProgress.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(ReportActivity.this, ListAdDetail.class);
//                i.putExtra(AttributeVideo.USER, AttributeVideo.USER);
//                startActivity(i);
//            }
//        });
//        binding.idfilmhd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(ReportActivity.this, ListAdDetail.class);
//                i.putExtra(AttributeVideo.USER, AttributeVideo.ACTION);
//                startActivity(i);
//            }
//        });
//        binding.filmvn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(ReportActivity.this, ListAdDetail.class);
//                i.putExtra(AttributeVideo.USER, AttributeVideo.VN);
//                startActivity(i);
//            }
//        });
//
//        binding.filmhq.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(ReportActivity.this, ListAdDetail.class);
//                i.putExtra(AttributeVideo.USER, AttributeVideo.Han);
//                startActivity(i);
//            }
//        });
//
//        binding.idfilmkd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(ReportActivity.this, ListAdDetail.class);
//                i.putExtra(AttributeVideo.USER, AttributeVideo.KING_DI);
//                startActivity(i);
//            }
//        });
//
//
//        for (int i = 0; i < sqlHelper.getAllFilm().size(); i++) {
//            //      Toast.makeText(getContext(), sqlHelper.getAllFilm().get(i).getCategory(), Toast.LENGTH_SHORT).show();
//            if (sqlHelper.getAllFilm().get(i).getCategory().trim().equals("vn")) {
//                listViet.add(sqlHelper.getAllFilm().get(i));
//            } else if (sqlHelper.getAllFilm().get(i).getCategory().trim().equals(AttributeVideo.ACTION)) {
//                listAc.add(sqlHelper.getAllFilm().get(i));
//            } else if (sqlHelper.getAllFilm().get(i).getCategory().trim().equals(AttributeVideo.Han)) {
//                listHan.add(sqlHelper.getAllFilm().get(i));
//            } else if (sqlHelper.getAllFilm().get(i).getCategory().trim().equals(AttributeVideo.KING_DI)) {
//                listKinh.add(sqlHelper.getAllFilm().get(i));
//            } else if (sqlHelper.getAllFilm().get(i).getCategory().trim().equals(AttributeVideo.HOAT_HINH)) {
//                listhh.add(sqlHelper.getAllFilm().get(i));
//            }
//        }
//        binding.filmvn.setText(listViet.size() + "");
//        binding.filmhq.setText(listHan.size() + "");
//        binding.filmhh.setText(listhh.size() + "");
//        binding.idfilmkd.setText(listKinh.size() + "");
//        binding.idfilmhd.setText(listAc.size() + "");
//
//    }
}
