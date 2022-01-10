package com.example.billsplit_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TipAdapter extends RecyclerView.Adapter<TipAdapter.TipViewHolder>{

    public TipAdapter(){ }

    public class TipViewHolder extends RecyclerView.ViewHolder{
        private TextView name_str;


        public TipViewHolder(@NonNull View itemView) {
            super(itemView);
            name_str = itemView.findViewById(R.id.tip_user_name);

        }
    }

    @NonNull
    @Override
    public TipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tip, parent,false);
        return new TipViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TipViewHolder holder, int position) {
        String name = MainActivity.usersList.get(position).getUsername();
        holder.name_str.setText(name);
    }

    @Override
    public int getItemCount() {
        return MainActivity.usersList.size();
    }
}
