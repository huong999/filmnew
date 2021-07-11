package com.example.newfilm.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newfilm.Activity.Detail;
import com.example.newfilm.Adapter.AdapterXemSau;
import com.example.newfilm.Model.Video;
import com.example.newfilm.OnClick.OnItemClickListener;
import com.example.newfilm.R;
import com.example.newfilm.SQL.SqlHelper;
import com.example.newfilm.Utils.Utils;
import com.example.newfilm.databinding.FragmentxemsauBinding;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.newfilm.Model.AccountAttribute.ACCOUNT_ID;
import static com.example.newfilm.Model.AccountAttribute.ACCOUNT_STATUS;
import static com.example.newfilm.Model.AccountAttribute.SHARE_PRE_NAME;

public class FragmentXemSau extends Fragment {
    FragmentxemsauBinding binding;
    List<Video> list, listxemsau, listTemp, listcuoi;

    String title, id, idPlayList, description, kind, pub, url;
    AdapterXemSau adapter;
    SqlHelper sqlHelper;

    public static FragmentXemSau newInstance() {
        Bundle bundle = new Bundle();
        FragmentXemSau fragmentXemSau = new FragmentXemSau();
        fragmentXemSau.setArguments(bundle);
        return fragmentXemSau;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragmentxemsau, container, false);
        sqlHelper = new SqlHelper(getContext());
        listxemsau = new ArrayList<>();
        listTemp = new ArrayList<>();
        listcuoi = new ArrayList<>();
        list = new ArrayList<>();
        list = sqlHelper.getAllFilmXemSau();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARE_PRE_NAME, MODE_PRIVATE);
        listTemp = locIdAcc(list,listTemp,sharedPreferences.getInt(ACCOUNT_ID,0));
        adapter = new AdapterXemSau(listTemp, getContext(), new OnItemClickListener() {
            @Override
            public void onItemClick(Video item) {
                Intent intent = new Intent(getContext(), Detail.class);
                intent.putExtra("name", item.getTitle());
                intent.putExtra("id", item.getVideoID());
                intent.putExtra("idPlayList", item.getPlaylistId());
                intent.putExtra("description", item.getDescription());
                intent.putExtra("time", item.getPublishedAt());
                intent.putExtra("img", item.getUrl());
                intent.putExtra("kind", item.getKind());
                startActivity(intent);
            }

            @Override
            public void onClickMore(Video video) {

            }
        });
        binding.rc.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
        binding.rc.setLayoutManager(layoutManager1);

        return binding.getRoot();
    }

    private void initDATA() {
        for (Video v : Utils.getList()) {
            if (!list.contains(v)) {
                list.add(v);
            }
        }
    }

    public boolean getStatus() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARE_PRE_NAME, MODE_PRIVATE);
        boolean check = sharedPreferences.getBoolean(ACCOUNT_STATUS, false);
        return check;
    }

    private List<Video> locIdAcc(List<Video> list, List<Video> listTemp, int id) {

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIdAcc() == id) {
                listTemp.add(list.get(i));
            }
        }
        return listTemp;

    }

    private List<Video> locList(List<Video> list, List<Video> listTemp) {
        for (int i = 0; i < list.size(); i++) {
            if (!listTemp.contains(list.get(i))) {
                listTemp.add(list.get(i));
            }
        }
        return listTemp;

    }
}
