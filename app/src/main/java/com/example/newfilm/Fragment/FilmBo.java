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

import com.example.newfilm.Activity.DetailBo;
import com.example.newfilm.Adapter.AdapterFilmBo;
import com.example.newfilm.Model.filmbo;
import com.example.newfilm.OnClick.OnClickTap;
import com.example.newfilm.R;
import com.example.newfilm.databinding.FragmentfilmboBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FilmBo extends Fragment {
    FragmentfilmboBinding binding;
    List<filmbo> list;
    AdapterFilmBo adapter;
    String u = "http://demo2986287.mockable.io/phimbo";

    public static FilmBo newInstance() {
        Bundle bundle = new Bundle();
        FilmBo filmBo = new FilmBo();
        filmBo.setArguments(bundle);
        return filmBo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragmentfilmbo, container, false);
        list = new ArrayList<>();
        Data(u, getContext());
        return binding.getRoot();
    }

    public void Data(final String url, final Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Json(response, list);
                setAdapterDai(list, binding.rc);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void Json(JSONObject response, List<filmbo> videos) {
        try {
            JSONArray jsonArrayItems = response.getJSONArray("items");
            for (int i = 0; i < jsonArrayItems.length(); i++) {
                JSONObject jsonItem = jsonArrayItems.getJSONObject(i);
                String totalResults = jsonItem.getString("totalResults");
                String publishedAt = jsonItem.getString("publishedAt");
                String playlistId = jsonItem.getString("playlistId");
                String videoId = jsonItem.getString("videoId");
                String title = jsonItem.getString("title");
                String description = jsonItem.getString("description");
                String url = jsonItem.getString("url");

                filmbo ob = new filmbo(totalResults, publishedAt, playlistId, videoId, title, description, url);
                list.add(ob);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setAdapterDai(List<filmbo> filmbos, RecyclerView rc) {

        adapter = new AdapterFilmBo(filmbos, getContext(), new OnClickTap() {
            @Override
            public void OnClick(filmbo filmbo) {
                Intent intent = new Intent(getContext(), DetailBo.class);
                intent.putExtra("name", filmbo.getTitle());
                intent.putExtra("id", filmbo.getVideoId());
                intent.putExtra("idPlayList", filmbo.getPlaylistId());
                intent.putExtra("description", filmbo.getDescription());
                intent.putExtra("time", filmbo.getPublishedAt());
                intent.putExtra("img", filmbo.getUrl());

                startActivity(intent);
            }
        });
        rc.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
        rc.setLayoutManager(layoutManager1);

    }
}

