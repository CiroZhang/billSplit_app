package com.example.billsplit_app;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemView_adapter extends RecyclerView.Adapter<ItemView_adapter.ItemView_viewHolder>{
    private ArrayList<Dish> dishList;

    public ItemView_adapter(ArrayList<Dish> dishList){
        this.dishList = dishList;
    }
    public class ItemView_viewHolder extends RecyclerView.ViewHolder{
        private TextView name_str;
        public ItemView_viewHolder(final View view) {
            super(view);
            name_str = view.findViewById(R.id.profile_user_name);
        }
    }

    @NonNull
    @Override
    public ItemView_adapter.ItemView_viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dish, parent,false);
        return new ItemView_viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemView_adapter.ItemView_viewHolder holder, int position) {
        String name = dishList.get(position).getName();
        String price = dishList.get(position).getPrice();
        holder.name_str.setText(name);
    }

    @Override
    public int getItemCount() {
        return dishList.size();
    }
}
