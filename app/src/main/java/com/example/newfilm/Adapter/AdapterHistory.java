package com.example.newfilm.Adapter;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newfilm.Model.Video;
import com.example.newfilm.OnClick.OnItemClickListener;
import com.example.newfilm.R;
import com.example.newfilm.SQL.SqlHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import at.huber.youtubeExtractor.YouTubeUriExtractor;
import at.huber.youtubeExtractor.YtFile;

import static android.content.Context.MODE_PRIVATE;
import static com.example.newfilm.Model.AccountAttribute.ACCOUNT_ID;
import static com.example.newfilm.Model.AccountAttribute.ACCOUNT_STATUS;
import static com.example.newfilm.Model.AccountAttribute.SHARE_PRE_NAME;

public class AdapterHistory extends RecyclerView.Adapter<AdapterHistory.ViewHolder> {
    OnItemClickListener listener;
    List<Video> list;
    Context context;
    Video video;
    List<Video> videos;
    String newlink;

    public AdapterHistory( List<Video> list, Context context,OnItemClickListener listener) {
        this.listener = listener;
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterHistory.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.itemhistory, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterHistory.ViewHolder holder, final int position) {
        video = list.get(position);
        final String tt = list.get(position).getTitle();
        String avatar = list.get(position).getUrl();
        Glide.with(context).load(avatar).into(holder.imageView);
        holder.tvName.setText(tt);
        holder.dates.setText(video.getDate());
        holder.dates.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        holder.dates.setHorizontallyScrolling(true);
        holder.dates.setSelected(true);
        holder.dates.setMarqueeRepeatLimit(-1);
        holder.dates.setFocusable(true);
        holder.bind(list.get(position), listener);
        holder.imb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu popupMenu = new PopupMenu(context, holder.imb);
                popupMenu.inflate(R.menu.menu_more);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.xemsau:
//
                                SqlHelper sqlHelper = new SqlHelper(context);
                                SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_PRE_NAME, MODE_PRIVATE);
                                Video videov = new Video(sharedPreferences.getInt(ACCOUNT_ID, 0), list.get(position).getPublishedAt(), list.get(position).getTitle(), list.get(position).getDescription(), list.get(position).getUrl(), list.get(position).getKind(), list.get(position).getVideoID(), list.get(position).getPlaylistId());
                                sqlHelper.InsertFilmToXemSau(videov);
                                Toast.makeText(context, "Đã Thêm vào xem sau", Toast.LENGTH_SHORT).show();
                                return true;


                            case R.id.chiase:

                                Intent sendIntent = new Intent();
                                sendIntent.setAction(Intent.ACTION_SEND);
                                String link = ("https://www.youtube.com/watch?v=" + list.get(position).getVideoID());
                                // this is the text that will be shared
                                sendIntent.putExtra(Intent.EXTRA_TEXT, link);
                                sendIntent.putExtra(Intent.EXTRA_SUBJECT, video.getTitle()
                                        + "Share");

                                sendIntent.setType("text/plain");
                                context.startActivity(Intent.createChooser(sendIntent, "Share"));

                                return true;
                            case R.id.taixuong:
                                String youtubeLink = ("https://www.youtube.com/watch?v=" + list.get(position).getVideoID());
                                String t = list.get(position).getTitle();
                                Dowload(v, youtubeLink, t);
                                return true;
                            case R.id.nolike:
                                SqlHelper sqlHelper1 = new SqlHelper(context);
                                sqlHelper1.deleteItemInHistory(list.get(position).getId());
                                list.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                                return true;

                        }


                        return false;
                    }
                });
                popupMenu.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvTime, tvName, dates;
        ImageButton imb;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.avatar);
            tvName = itemView.findViewById(R.id.time);
            tvName = itemView.findViewById(R.id.name);
            dates = itemView.findViewById(R.id.dateD);
            imb = itemView.findViewById(R.id.option);

        }

        public void bind(final Video item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    private ProgressDialog pDialog;


    private class RequestDownloadVideoStream extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Downloading file. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setMax(100);
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            InputStream is = null;
            URL u = null;
            int len1 = 0;
            int temp_progress = 0;
            int progress = 0;
            try {
                u = new URL(params[0]);
                is = u.openStream();
                URLConnection huc = (URLConnection) u.openConnection();
                huc.connect();
                int size = huc.getContentLength();

                if (huc != null) {
                    String file_name = params[1] + ".mp4";
                    String storagePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/YoutubeVideos";
                    File f = new File(storagePath);
                    if (!f.exists()) {
                        f.mkdir();
                    }

                    FileOutputStream fos = new FileOutputStream(f + "/" + file_name);
                    byte[] buffer = new byte[1024];
                    int total = 0;
                    if (is != null) {
                        while ((len1 = is.read(buffer)) != -1) {
                            total += len1;
                            // publishing the progress....
                            // After this onProgressUpdate will be called
                            progress = (int) ((total * 100) / size);
                            if (progress >= 0) {
                                temp_progress = progress;
                                publishProgress("" + progress);
                            } else
                                publishProgress("" + temp_progress + 1);

                            fos.write(buffer, 0, len1);
                        }
                    }

                    if (fos != null) {
                        publishProgress("" + 100);
                        fos.close();
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            pDialog.setProgress(Integer.parseInt(values[0]));
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (pDialog.isShowing())
                pDialog.dismiss();
        }
    }

    public void Dowload(View view, String a, final String t) {

        YouTubeUriExtractor youTubeUriExtractor = new YouTubeUriExtractor(context) {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onUrisAvailable(String videoId, String videoTitle, SparseArray<YtFile> ytFiles) {

                if (ytFiles != null) {
                    int tag = 22;
                    newlink = ytFiles.get(tag).getUrl();

                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(newlink));
                    request.setTitle(t);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, t + ".mp4");
                    DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                    request.allowScanningByMediaScanner();
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
                    downloadManager.enqueue(request);

                }

            }
        };
        youTubeUriExtractor.execute(a);
    }

    public boolean getStatus() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_PRE_NAME, MODE_PRIVATE);
        boolean check = sharedPreferences.getBoolean(ACCOUNT_STATUS, false);
        return check;
    }
}
