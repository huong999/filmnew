package com.example.newfilm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.newfilm.Model.filmbo;
import com.example.newfilm.OnClick.OnClickTap;
import com.example.newfilm.R;

import java.util.List;

public class AdapterFilmBo extends RecyclerView.Adapter<AdapterFilmBo.ViewHolder> {
    List<filmbo> list;
    Context context;
    OnClickTap onClickTap;

    public AdapterFilmBo(List<filmbo> list, Context context, OnClickTap onClickTap) {
        this.list = list;
        this.context = context;
        this.onClickTap = onClickTap;
    }


    @NonNull
    @Override
    public AdapterFilmBo.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.filmtap, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFilmBo.ViewHolder holder, int position) {
        final String tt = list.get(position).getTitle();
        String avatar = list.get(position).getUrl();
        Glide.with(context).load(avatar).into(holder.imageView);
        holder.tvName.setText(tt);
        holder.tvTong.setText(list.get(position).getTotalResults()+" tập");
        holder.bind(list.get(position),onClickTap);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvName, tvTong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.avatar);
            tvTong = itemView.findViewById(R.id.tong);
            tvName = itemView.findViewById(R.id.name);
        }
        public void bind(final filmbo item, final OnClickTap listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnClick(item);
                }
            });
        }
    }
}
