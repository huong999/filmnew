package com.example.newfilm.adim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newfilm.Model.Account;
import com.example.newfilm.R;

import java.util.List;

public class AdapterListUser extends RecyclerView.Adapter<AdapterListUser.ViewHolder> {
    private List<Account> list;
    private Context context;

    public AdapterListUser(List<Account> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public AdapterListUser.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_admin_user, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListUser.ViewHolder holder, int position) {
        holder.tvName.setText(list.get(position).getFullName());
        holder.tvSdt.setText(list.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvSdt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = (itemView).findViewById(R.id.usernameTextView);
            tvSdt = (itemView).findViewById(R.id.txt_sdt);
        }
    }
}
