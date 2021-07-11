package com.example.newfilm.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.newfilm.Adapter.AdapterFilmBo;
import com.example.newfilm.Adapter.AdapterTap;
import com.example.newfilm.Adapter.CommentAdapter;
import com.example.newfilm.Model.Video;
import com.example.newfilm.Model.Video2;
import com.example.newfilm.Model.YoutubeCommentModel;
import com.example.newfilm.Model.filmbo;
import com.example.newfilm.OnClick.OnClickTap;
import com.example.newfilm.OnClick.OnClickVideo2;
import com.example.newfilm.R;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.newfilm.Model.AccountAttribute.ACCOUNT_STATUS;
import static com.example.newfilm.Model.AccountAttribute.SHARE_PRE_NAME;

public class DetailBo extends AppCompatActivity {
    Toolbar toolbar;
    TextView txname, txTime, txhome, txlike, txDislike, viewtx, cmttt;
    ExpandableTextView expTv1;
    RecyclerView rc, rc1;
    RecyclerView mList_videos = null;
    String idPlayList = "", name, des, id = "", time, img, cmt;
    List<Video> list1;
    List<filmbo> list;
    List<Video2> listTT;
    ArrayList<YoutubeCommentModel> mList, listcmttt;
    ArrayList<YoutubeCommentModel> mListData = new ArrayList<>();
    ImageView imgV, back;
    Button bt;
    ImageView btsent;
    EditText et;
    AdapterTap adapterTap;
    AdapterFilmBo adapter;
    CommentAdapter mAdapter = null;

    String API_KEY = "AIzaSyCFi6Ctl4thP43H3PkOYJyV9ipOYDiSPAY";
    String u = "http://demo2986287.mockable.io/phimbo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bo);
        initData();
        txhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), StartApp.class);
                startActivity(intent);
            }
        });
        et = findViewById(R.id.etcmt);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        id = intent.getStringExtra("id");
        des = intent.getStringExtra("description");
        idPlayList = intent.getStringExtra("idPlayList");
        time = intent.getStringExtra("time");
        des = intent.getStringExtra("description");
        img = intent.getStringExtra("img");
        list1.add(new Video(name, des, id, idPlayList));

        Glide.with(getBaseContext()).load(img).into(imgV);
        txname.setText(name);
        txTime.setText(time);
        expTv1.setText(des);
        getJson(listcmttt);
        new RequestYoutubeCommentAPI().execute();
        btsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getStatus()) {
                    cmt = et.getText().toString();
                    mList.add(new YoutubeCommentModel("Trang", cmt, "unkown", "http://xemanhdep.com/wp-content/uploads/2016/05/avatar-dep-de-thuong.jpg", id));
                    et.setText("");
                } else {
                    onDialogLoginShow();
                }
            }
        });
        String url = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=" + idPlayList +
                "&key=" + API_KEY + "&maxResults=50";
        Data(url, getBaseContext());
        Data1(u, getBaseContext());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void Data(final String url, final Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Json(response, listTT);
                setAdapter(listTT, rc);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void Json(JSONObject response, List<Video2> videos) {
        try {
            JSONArray jsonArrayItems = response.getJSONArray("items");
            for (int i = 0; i < jsonArrayItems.length(); i++) {
                JSONObject jsonItem = jsonArrayItems.getJSONObject(i);
                JSONObject jsonsnippet = jsonItem.getJSONObject("snippet");
                String publishedAt = jsonsnippet.getString("publishedAt");
                String playlistId = jsonsnippet.getString("playlistId");
                int position = jsonsnippet.getInt("position");
                String title = jsonsnippet.getString("title");
                String description = jsonsnippet.getString("description");
                JSONObject jsonthumbnails = jsonsnippet.getJSONObject("thumbnails");

                JSONObject jsonmedium = jsonthumbnails.getJSONObject("medium");
                String urlimg = jsonmedium.getString("url");

                JSONObject jsonresourceId = jsonsnippet.getJSONObject("resourceId");
                String kind = jsonresourceId.getString("kind");
                String videoId = jsonresourceId.getString("videoId");
                Video2 v = new Video2(publishedAt, title, description, urlimg, kind, videoId, playlistId, position);
                videos.add(v);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setAdapter(List<Video2> videos, RecyclerView rc) {

        adapterTap = new AdapterTap(videos, getBaseContext(), new OnClickVideo2() {
            @Override
            public void OnClickVideo(Video2 video2) {
                Intent intent = new Intent(getBaseContext(), PlayFilm.class);
                intent.putExtra("idvideo", video2.getVideoID());
                startActivity(intent);

            }
        });
        rc.setAdapter(adapterTap);
        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(getBaseContext(), 10, RecyclerView.VERTICAL, false);
        rc.setLayoutManager(layoutManager1);

    }

    public class RequestYoutubeCommentAPI extends AsyncTask<Void, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            String VIDEO_COMMENT_URL = "https://www.googleapis.com/youtube/v3/commentThreads?part=snippet&maxResults=100&videoId=" + id + "&key=" + API_KEY;
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(VIDEO_COMMENT_URL);
            Log.e("url: ", VIDEO_COMMENT_URL);
            try {
                HttpResponse response = httpClient.execute(httpGet);
                HttpEntity httpEntity = response.getEntity();
                String json = EntityUtils.toString(httpEntity);
                return json;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (response != null) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.e("response", jsonObject.toString());
                    mListData = parseJson(jsonObject);
                    initVideoList(mListData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ArrayList<YoutubeCommentModel> parseJson(JSONObject jsonObject) {
        mList = new ArrayList<>();

        if (jsonObject.has("items")) {
            try {
                JSONArray jsonArray = jsonObject.getJSONArray("items");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);

                    YoutubeCommentModel youtubeObject = new YoutubeCommentModel();
                    JSONObject jsonTopLevelComment = json.getJSONObject("snippet").getJSONObject("topLevelComment");
                    JSONObject jsonSnippet = jsonTopLevelComment.getJSONObject("snippet");

                    String title = jsonSnippet.getString("authorDisplayName");
                    String thumbnail = jsonSnippet.getString("authorProfileImageUrl");
                    String comment = jsonSnippet.getString("textDisplay");
                    youtubeObject.setTitle(title);
                    youtubeObject.setComment(comment);
                    youtubeObject.setThumbnail(thumbnail);
                    mList.add(youtubeObject);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return mList;

    }

    public void initVideoList(ArrayList<YoutubeCommentModel> mListData) {
        mList_videos.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CommentAdapter(this, mListData);
        mList_videos.setAdapter(mAdapter);
    }

    public void Data1(final String url, final Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Json1(response, list);
                setAdapterDai(list, rc1);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void Json1(JSONObject response, List<filmbo> videos) {
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
                videos.add(ob);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setAdapterDai(List<filmbo> filmbos, RecyclerView rc) {

        adapter = new AdapterFilmBo(filmbos, getBaseContext(), new OnClickTap() {
            @Override
            public void OnClick(filmbo filmbo) {
                Intent intent = new Intent(getBaseContext(), DetailBo.class);
                intent.putExtra("name", filmbo.getTitle());
                intent.putExtra("id", filmbo.getVideoId());
                intent.putExtra("idPlayList", filmbo.getPlaylistId());
                intent.putExtra("description", filmbo.getDescription());
                intent.putExtra("time", filmbo.getPublishedAt());
                intent.putExtra("img", filmbo.getUrl());

                startActivity(intent);
                finish();
            }
        });
        rc.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(getBaseContext(), 1, RecyclerView.HORIZONTAL, false);
        rc.setLayoutManager(layoutManager1);

    }

    private void getJson(final List<YoutubeCommentModel> list) {
        String url = "https://www.googleapis.com/youtube/v3/videos?part=statistics&id=" + id + "&key=AIzaSyCFi6Ctl4thP43H3PkOYJyV9ipOYDiSPAY";
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArrayItems = response.getJSONArray("items");
                    for (int i = 0; i < jsonArrayItems.length(); i++) {
                        JSONObject jsonObject = jsonArrayItems.getJSONObject(i);
                        JSONObject jsonObject1 = jsonObject.getJSONObject("statistics");
                        int like = jsonObject1.getInt("likeCount");
                        int dislike = jsonObject1.getInt("dislikeCount");
                        int view = jsonObject1.getInt("viewCount");
                        int numComment = jsonObject1.getInt("commentCount");

                        Locale locale = new Locale("en", "EN");
                        String pattern = "###,###";
                        DecimalFormat dcf = (DecimalFormat) NumberFormat.getNumberInstance(locale);
                        dcf.applyPattern(pattern);
                        txlike.setText("" + dcf.format(like));
                        txDislike.setText("" + dcf.format(dislike));
                        viewtx.setText("" + dcf.format(view));
                        cmttt.setText("Comment " + "( " + dcf.format(numComment) + " )");
                        //  list.add(new YoutubeCommentModel(like, dislike, numComment, view));
                        Toast.makeText(getBaseContext(), like + dislike + "", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);

    }
    public boolean getStatus() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARE_PRE_NAME, MODE_PRIVATE);
        boolean check = sharedPreferences.getBoolean(ACCOUNT_STATUS, false);
        return check;
    }

    private void onDialogLoginShow() {
        AlertDialog alertDialog = new AlertDialog.Builder(DetailBo.this)
                .setTitle(getString(R.string.un_login))
                .setMessage(getString(R.string.sign_in_now))
                .setIcon(R.drawable.user)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getBaseContext(), DangNhap.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        alertDialog.show();
    }
private void initData(){
    list = new ArrayList<>();
    list1 = new ArrayList<>();
    listcmttt = new ArrayList<>();
    listTT = new ArrayList<>();
    toolbar = findViewById(R.id.toolbar);
    txname = findViewById(R.id.name);
    txTime = findViewById(R.id.time);
    expTv1 = (ExpandableTextView) findViewById(R.id.expand_text_view);
    rc = findViewById(R.id.rc1);
    rc1 = findViewById(R.id.rc);
    imgV = findViewById(R.id.img);
    mList_videos = (RecyclerView) findViewById(R.id.cmt);
    bt = findViewById(R.id.bt);
    cmttt = findViewById(R.id.cmtt);
    txTime = findViewById(R.id.time);
    txlike = findViewById(R.id.like);
    viewtx = findViewById(R.id.eye);
    txDislike = findViewById(R.id.dislike);
    btsent = findViewById(R.id.sent);
    txhome = findViewById(R.id.home);
}
}