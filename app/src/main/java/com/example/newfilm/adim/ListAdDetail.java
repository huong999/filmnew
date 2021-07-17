package com.example.newfilm.adim;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newfilm.Model.AttributeVideo;
import com.example.newfilm.Model.Video;
import com.example.newfilm.R;
import com.example.newfilm.SQL.SqlHelper;
import com.example.newfilm.databinding.ActivityListAdDetailBinding;

import java.util.ArrayList;
import java.util.List;

public class ListAdDetail extends AppCompatActivity {
    private ActivityListAdDetailBinding binding;
    private String category;
    private AdapterListUser adapter;
    private SqlHelper sqlHelper;
    private AdapterFilmList adapterFilmList;
    List<Video> arrayList, list, listHan, listViet, listAc, listhh, listKinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_ad_detail);
        sqlHelper = new SqlHelper(this);
        arrayList = new ArrayList<>();
        listHan = new ArrayList<>();
        listViet = new ArrayList<>();
        listAc = new ArrayList<>();
        listhh = new ArrayList<>();
        listKinh = new ArrayList<>();
        arrayList = sqlHelper.getAllFilm();
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
        category = getIntent().getStringExtra(AttributeVideo.USER);

        if (category.equals(AttributeVideo.USER)) {
            binding.txtTt.setText("Danh sách người dùng");
            adapter = new AdapterListUser(sqlHelper.getAllAccount(), this);
            binding.rc.setAdapter(adapter);
        } else if (category.equals(AttributeVideo.ACTION)) {
            binding.txtTt.setText("Danh sách phim hành động");
            adapterFilmList = new AdapterFilmList(this, listAc);
            binding.rc.setAdapter(adapterFilmList);
        } else if (category.equals(AttributeVideo.VN)) {
            binding.txtTt.setText("Danh sách phim việt nam");
            adapterFilmList = new AdapterFilmList(this, listViet);
            binding.rc.setAdapter(adapterFilmList);
        } else if (category.equals(AttributeVideo.Han)) {
            binding.txtTt.setText("Danh sách phim hàn quốc");
            adapterFilmList = new AdapterFilmList(this, listHan);
            binding.rc.setAdapter(adapterFilmList);
        } else if (category.equals(AttributeVideo.KING_DI)) {
            binding.txtTt.setText("Danh sách phim kinh dị");
            adapterFilmList = new AdapterFilmList(this, listKinh);
            binding.rc.setAdapter(adapterFilmList);
        }
        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(getBaseContext(), 1, RecyclerView.VERTICAL, false);
        binding.rc.setLayoutManager(layoutManager1);
    }
}