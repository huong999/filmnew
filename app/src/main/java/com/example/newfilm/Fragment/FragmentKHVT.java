package com.example.newfilm.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.newfilm.Activity.Detail;
import com.example.newfilm.Adapter.Adapter;
import com.example.newfilm.Model.Video;
import com.example.newfilm.OnClick.OnItemClickListener;
import com.example.newfilm.R;
import com.example.newfilm.SQL.SqlHelper;
import com.example.newfilm.databinding.FragmentkhvtBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentKHVT extends Fragment {
    String API_KEY = "AIzaSyCFi6Ctl4thP43H3PkOYJyV9ipOYDiSPAY";
    String idPlayList1 = "PLxu-S05deEMb_Vic6Jspjlc0n27GtwN_-";
    String urlHan = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=" + idPlayList1 +
            "&key=" + API_KEY + "&maxResults=50";
    FragmentkhvtBinding binding;
    List<Video> list;
    Adapter adapter;
SqlHelper sqlHelper;
    public static FragmentKHVT newInstance() {
        Bundle bundle = new Bundle();
        FragmentKHVT fragmentKHVT = new FragmentKHVT();
        fragmentKHVT.setArguments(bundle);
        return fragmentKHVT;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragmentkhvt, container, false);
        list = new ArrayList<>();
        sqlHelper = new SqlHelper(getContext());
        DataHh(urlHan,getContext());
        return binding.getRoot();
    }

    public void DataHh(final String url, final Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Json(response, list);
                setAdapter(list, binding.rc);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void Json(JSONObject response, List<Video> videos) {
        try {


            JSONArray jsonArrayItems = response.getJSONArray("items");
            for (int i = 0; i < jsonArrayItems.length(); i++) {
                JSONObject jsonItem = jsonArrayItems.getJSONObject(i);
                JSONObject jsonsnippet = jsonItem.getJSONObject("snippet");
                String publishedAt = jsonsnippet.getString("publishedAt");
                String playlistId = jsonsnippet.getString("playlistId");
                String title = jsonsnippet.getString("title");
                String description = jsonsnippet.getString("description");

                JSONObject jsonthumbnails = jsonsnippet.getJSONObject("thumbnails");

                JSONObject jsonmedium = jsonthumbnails.getJSONObject("medium");
                String urlimg = jsonmedium.getString("url");

                JSONObject jsonresourceId = jsonsnippet.getJSONObject("resourceId");
                String kind = jsonresourceId.getString("kind");
                String videoId = jsonresourceId.getString("videoId");
                Video v = new Video(publishedAt, title, description, urlimg, kind, videoId, playlistId);
                videos.add(v);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setAdapter(List<Video> videos, RecyclerView rc) {

        adapter = new Adapter(videos, getContext(), new OnItemClickListener() {
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
        rc.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
        rc.setLayoutManager(layoutManager1);

    }
}
