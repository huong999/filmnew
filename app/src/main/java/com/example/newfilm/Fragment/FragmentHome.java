package com.example.newfilm.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

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
import com.bumptech.glide.Glide;
import com.example.newfilm.Activity.Detail;
import com.example.newfilm.Adapter.Adapter;
import com.example.newfilm.Model.Video;
import com.example.newfilm.OnClick.OnItemClickListener;
import com.example.newfilm.R;
import com.example.newfilm.SQL.SqlHelper;
import com.example.newfilm.databinding.FragmenthomeBinding;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment {
    FragmenthomeBinding binding;
    SqlHelper sqlHelper;
    List<Video> list, listHan, listViet, listAc, listhh, listKinh;
    Adapter adapter;
    String API_KEY = "AIzaSyCFi6Ctl4thP43H3PkOYJyV9ipOYDiSPAY";
    String idPlayList1 = "PLxu-S05deEMZkKbZwc9P3FTZ3wJv8rMCF";
    String idPlayList2 = "PLxu-S05deEMayTrCSJ3YM-PXjJNuxnjj4";
    String idPlayList3 = "PLxu-S05deEMY6IM0Ey4sFljOPSTf2vPeM";
    String idPlayList4 = "PLxu-S05deEMZb8_9AxP-8ackg8tKFNRah";
    String idPlayList5 = "PLxu-S05deEMZT7O8JfcKB0sGUaASnXAY5";
    String idPlayList6 = "PLxu-S05deEMaVgLDOJRWQ2kNAGmEqXwQx";

    String urlHan = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=" + idPlayList1 +
            "&key=" + API_KEY + "&maxResults=50";
    String urlViet = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=" + idPlayList2 +
            "&key=" + API_KEY + "&maxResults=50";
    String urlAction = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=" + idPlayList3 +
            "&key=" + API_KEY + "&maxResults=50";
    String urlHh = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=" + idPlayList4 +
            "&key=" + API_KEY + "&maxResults=50";
    String urlKinh = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=" + idPlayList5 +
            "&key=" + API_KEY + "&maxResults=50";
    String url = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=" + idPlayList6 +
            "&key=" + API_KEY + "&maxResults=50";


    public static FragmentHome newInstance() {
        Bundle bundle = new Bundle();
        FragmentHome homeFragment = new FragmentHome();
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragmenthome, container, false);
        sqlHelper = new SqlHelper(getContext());
        getData();
        list = new ArrayList<>();
        listHan = new ArrayList<>();
        listViet = new ArrayList<>();
        listAc = new ArrayList<>();
        listhh = new ArrayList<>();
        listKinh = new ArrayList<>();


        Data(url, getContext());
        DataAc(urlAction,getContext());
        DataKinh(urlKinh,getContext());
        DataHh(urlHh,getContext());
        DataViet(urlViet,getContext());
        DataHan(urlHan,getContext());



        return binding.getRoot();
    }

    private void getData() {

        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://i.ytimg.com/vi/qFXd0gCycZ4/maxresdefault.jpg");
        mangquangcao.add("https://i.ytimg.com/vi/cAa0wO1-1ls/maxresdefault.jpg");
        mangquangcao.add("https://i.ytimg.com/vi/MOb-QsIAOLU/maxresdefault.jpg");
        mangquangcao.add("https://i.ytimg.com/vi/qlhO7yLqYP0/maxresdefault.jpg");


        for (int i = 0; i < mangquangcao.size(); i++) {

            ImageView imageView = new ImageView(getActivity());
            Glide.with(getActivity()).load(mangquangcao.get(i)).into(imageView);

            //cho image tu can full
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            binding.Viewflipper.addView(imageView);
        }
        // cho tu chay trong 5s
        binding.Viewflipper.setFlipInterval(5000);
        binding.Viewflipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_right);
        binding.Viewflipper.setInAnimation(animation_slide_in);
        binding.Viewflipper.setOutAnimation(animation_slide_out);
    }

    public void Data(final String url, final Context context) {
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
    public void DataHan(final String url, final Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Json(response, listHan);
                setAdapter(listHan, binding.rc5);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }
    public void DataViet(final String url, final Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Json(response, listViet);
                setAdapter(listViet, binding.rc4);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }
    public void DataAc(final String url, final Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Json(response, listAc);
                setAdapter(listAc, binding.rc1);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }
    public void DataKinh(final String url, final Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Json(response, listKinh);
                setAdapter(listKinh, binding.rc2);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }
    public void DataHh(final String url, final Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Json(response, listhh);
                setAdapter(listhh, binding.rc3);
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
                sqlHelper.InsertFilmToAllFilm(v);
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
                startActivity(intent);

            }

            @Override
            public void onClickMore(Video video) {

            }
        });
        rc.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false);
        rc.setLayoutManager(layoutManager1);

    }


}