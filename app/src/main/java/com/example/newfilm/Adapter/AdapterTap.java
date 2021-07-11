package com.example.newfilm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newfilm.Model.Video;
import com.example.newfilm.Model.Video2;
import com.example.newfilm.Model.filmbo;
import com.example.newfilm.OnClick.OnClickVideo2;
import com.example.newfilm.OnClick.OnItemClickListener;
import com.example.newfilm.R;

import java.util.List;

public class AdapterTap extends RecyclerView.Adapter<AdapterTap.ViewHolder> {
    List<Video2> list;
    Context context;
    OnClickVideo2 onClickVideo2;

    public AdapterTap(List<Video2> list, Context context, OnClickVideo2 onClickVideo2) {
        this.list = list;
        this.context = context;
        this.onClickVideo2 = onClickVideo2;
    }

    @NonNull
    @Override
    public AdapterTap.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.itemtap, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTap.ViewHolder holder, int position) {
        int i = list.get(position).getPosittion();
        int j = i + 1;
        holder.tap.setText(j + "");
        holder.bind(list.get(position),onClickVideo2);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tap;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tap = itemView.findViewById(R.id.tap);
        }
        public void bind(final Video2 item, final OnClickVideo2 listener) {
            tap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnClickVideo(item);
                }
            });
        }
    }
}
