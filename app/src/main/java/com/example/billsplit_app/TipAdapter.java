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

    private Context context;
    private ArrayList<User> userList;

    public TipAdapter(@NonNull Context context, @NonNull ArrayList<User> userList){
        this.context = context;
        this.userList = userList;
    }

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
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_tip, parent,false);
        return new TipViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TipViewHolder holder, int position) {
        String name = userList.get(position).getUsername();
        holder.name_str.setText(name);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
