package com.example.newfilm.adim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newfilm.Model.Video;
import com.example.newfilm.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterFilmList extends RecyclerView.Adapter<AdapterFilmList.ViewHolder> {
    private Context context;
    private List<Video> list;

    public AdapterFilmList(Context context, List<Video> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public AdapterFilmList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_admin_user, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFilmList.ViewHolder holder, int position) {
        holder.tvName.setText(list.get(position).getTitle());
        Glide.with(context).load(list.get(position).getUrl()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvSdt;
        CircleImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = (itemView).findViewById(R.id.usernameTextView1);
            tvSdt = (itemView).findViewById(R.id.txt_sdt1);
            img = (itemView).findViewById(R.id.img);
        }
    }
}
