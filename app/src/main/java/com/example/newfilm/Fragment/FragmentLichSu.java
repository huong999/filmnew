package com.example.newfilm.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newfilm.Activity.Detail;
import com.example.newfilm.Adapter.Adapter;
import com.example.newfilm.Adapter.AdapterHistory;
import com.example.newfilm.Model.Video;
import com.example.newfilm.OnClick.OnItemClickListener;
import com.example.newfilm.R;
import com.example.newfilm.SQL.SqlHelper;
import com.example.newfilm.Utils.Utils2;
import com.example.newfilm.databinding.FragmentlichsuBinding;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.newfilm.Model.AccountAttribute.ACCOUNT_ID;
import static com.example.newfilm.Model.AccountAttribute.SHARE_PRE_NAME;

public class FragmentLichSu extends Fragment {

    FragmentlichsuBinding binding;
    List<Video> list,listTem;
    AdapterHistory adapter;
SqlHelper sqlHelper;
    public static FragmentLichSu newInstance() {
        Bundle bundle = new Bundle();
        FragmentLichSu fragmentLichSu = new FragmentLichSu();
        fragmentLichSu.setArguments(bundle);
        return fragmentLichSu;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragmentlichsu, container, false);
        list = new ArrayList<>();
        listTem = new ArrayList<>();
        sqlHelper = new SqlHelper(getContext());
        list = sqlHelper.getAllFilmHistory();
   //     initDATA();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARE_PRE_NAME, MODE_PRIVATE);
        listTem = locIdAcc(list,listTem,sharedPreferences.getInt(ACCOUNT_ID,0));
        adapter = new AdapterHistory(listTem, getContext(), new OnItemClickListener() {
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
        for (Video v: Utils2.getList()) {
            if (!list.contains(v)){
                list.add(v);
            }
        }
        Log.d("listSize: ",list.size()+"");

    }
    private List<Video> locIdAcc(List<Video> list, List<Video> listTemp, int id) {

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIdAcc() == id) {
                listTemp.add(list.get(i));
            }
        }
        return listTemp;

    }
}
