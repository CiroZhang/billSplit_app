package com.example.billsplit_app;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recycleAdapter extends RecyclerView.Adapter<recycleAdapter.myViewHolder>{
    private ArrayList<User> userLists;

    public recycleAdapter(ArrayList<User> userList){
        this.userLists = userList;
    }
    public class myViewHolder extends RecyclerView.ViewHolder{
        private TextView name_str;
        public myViewHolder(final View view) {
            super(view);
            name_str = view.findViewById(R.id.opportunity_name);
        }



    }


    @NonNull
    @Override
    public recycleAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.individual_bill_screen, parent,false);
        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull recycleAdapter.myViewHolder holder, int position) {
        String name = userLists.get(position).getUsername();
        holder.name_str.setText(name);

    }

    @Override
    public int getItemCount() {
        return userLists.size();
    }
}
