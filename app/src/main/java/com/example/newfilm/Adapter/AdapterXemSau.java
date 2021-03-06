package com.example.newfilm.Adapter;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import java.util.List;

import at.huber.youtubeExtractor.YouTubeUriExtractor;
import at.huber.youtubeExtractor.YtFile;

public class AdapterXemSau extends RecyclerView.Adapter<AdapterXemSau.ViewHolder> {
    OnItemClickListener listener;
    List<Video> list;
    Context context;
    Video video;

    String newlink;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public AdapterXemSau(List<Video> list, Context context, OnItemClickListener listener) {
        this.listener = listener;
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public AdapterXemSau.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.itemhistory, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterXemSau.ViewHolder holder, final int position) {
        video = list.get(position);
        String tt = list.get(position).getTitle();
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
                popupMenu.inflate(R.menu.menumore2);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {

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
                                Dowload(v, youtubeLink,t);
                                return true;
                            case R.id.nolike:

                                SqlHelper sqlHelper = new SqlHelper(context);
                                sqlHelper.deleteItemInXemSau(list.get(position).getId());
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
        TextView tvTime, tvName,dates;
        ImageButton imb;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.avatar);
            tvName = itemView.findViewById(R.id.time);
            tvName = itemView.findViewById(R.id.name);
            imb = itemView.findViewById(R.id.option);
            dates = itemView.findViewById(R.id.dateD);

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
    public void Dowload(View view, String a,final String t) {

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
}
