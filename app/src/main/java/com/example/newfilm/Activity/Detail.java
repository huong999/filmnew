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

import com.example.newfilm.Adapter.Adapter;
import com.example.newfilm.Adapter.CommentAdapter;
import com.example.newfilm.Model.Video;
import com.example.newfilm.Model.YoutubeCommentModel;
import com.example.newfilm.OnClick.OnItemClickListener;
import com.example.newfilm.R;
import com.example.newfilm.SQL.SqlHelper;
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
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.example.newfilm.Model.AccountAttribute.ACCOUNT_ID;
import static com.example.newfilm.Model.AccountAttribute.ACCOUNT_STATUS;
import static com.example.newfilm.Model.AccountAttribute.SHARE_PRE_NAME;

public class Detail extends AppCompatActivity {
    Toolbar toolbar;
    TextView txname, txTime, txhome,txlike,txDislike,txView,cmttt;
    ExpandableTextView expTv1;
    List<Video> listTT;
    RecyclerView rc;
    String idPlayList = "";
    String name;
    List<Video> list1;
    List<YoutubeCommentModel> listCount;
    String des, kind;
    String id = "";
    String time;
    String img;
    String API_KEY = "AIzaSyCFi6Ctl4thP43H3PkOYJyV9ipOYDiSPAY";
    Adapter adapter;
    ImageView imgV;
    Button bt;
    ImageView btsent;
    private ArrayList<YoutubeCommentModel> mListData = new ArrayList<>();
    private CommentAdapter mAdapter = null;
    private RecyclerView mList_videos = null;
    ImageView back;
    ArrayList<YoutubeCommentModel> mList;
    EditText et;
    String cmt;
    SqlHelper sqlHelper;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        list1 = new ArrayList<>();
        listCount = new ArrayList<>();
        listTT = new ArrayList<>();
        toolbar = findViewById(R.id.toolbar);
        txname = findViewById(R.id.name);
        cmttt = findViewById(R.id.cmtt);
        txTime = findViewById(R.id.time);
        txlike = findViewById(R.id.like);
        txDislike = findViewById(R.id.dislike);
        txView = findViewById(R.id.eye);
        expTv1 = (ExpandableTextView) findViewById(R.id.expand_text_view);
        rc = findViewById(R.id.rc);
        imgV = findViewById(R.id.img);
        mList_videos = (RecyclerView) findViewById(R.id.cmt);
        bt = findViewById(R.id.bt);
//
        btsent = findViewById(R.id.sent);
        txhome = findViewById(R.id.home);
        et = findViewById(R.id.etcmt);
        sqlHelper = new SqlHelper(getBaseContext());

        txhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), StartApp.class);
                startActivity(intent);
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        final SharedPreferences sharedPreferences = getSharedPreferences(SHARE_PRE_NAME, MODE_PRIVATE);
        final Intent intent = getIntent();
        name = intent.getStringExtra("name");
        id = intent.getStringExtra("id");
        des = intent.getStringExtra("description");
        idPlayList = intent.getStringExtra("idPlayList");
        time = intent.getStringExtra("time");
        des = intent.getStringExtra("description");
        img = intent.getStringExtra("img");
        kind = intent.getStringExtra("kind");
        list1.add(new Video(name, des, id, idPlayList));

        Glide.with(getBaseContext()).load(img).into(imgV);
        txname.setText(name);
        txTime.setText(time);
        expTv1.setText(des);
getJson(listCount);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), PlayFilm.class);
                intent.putExtra("idvideo", id);
                intent.putExtra("name", name);
                intent.putExtra("idPlayList", idPlayList);
                intent.putExtra("description", des);
                intent.putExtra("time", time);
                intent.putExtra("img", img);
                intent.putExtra("kind", kind);
                startActivity(intent);
                DateFormat df = new SimpleDateFormat("HH:mm dd/MM/yyyy");
                String date = df.format(Calendar.getInstance().getTime());
                sqlHelper.InsertFilmToHistory(new Video(idPlayList, name, des, img, kind, id, idPlayList, date, sharedPreferences.getInt(ACCOUNT_ID, 0)));

            }
        });


        String url = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=" + idPlayList +
                "&key=" + API_KEY + "&maxResults=50";
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

        Data(url, getBaseContext());

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

        adapter = new Adapter(videos, getBaseContext(), new OnItemClickListener() {
            @Override
            public void onItemClick(Video item) {

                Intent intent = new Intent(getBaseContext(), Detail.class);
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
        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(getBaseContext(), 1, RecyclerView.HORIZONTAL, false);
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

    public boolean getStatus() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARE_PRE_NAME, MODE_PRIVATE);
        boolean check = sharedPreferences.getBoolean(ACCOUNT_STATUS, false);
        return check;
    }

    private void onDialogLoginShow() {
        AlertDialog alertDialog = new AlertDialog.Builder(Detail.this)
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
                        txlike.setText(""+ dcf.format(like));
                        txDislike.setText(""+ dcf.format(dislike));
                        txView.setText(""+ dcf.format(view));
                        cmttt.setText("Comment " +"( "+ dcf.format(numComment) +" )");
                      //  list.add(new YoutubeCommentModel(like, dislike, numComment, view));
                      //  Toast.makeText(Detail.this, like + dislike + "", Toast.LENGTH_SHORT).show();
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

}