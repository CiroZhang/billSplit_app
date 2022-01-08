package com.example.billsplit_app;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>{
    private ArrayList<Dish> dishList;
    RecyclerView SharedRecyclerView;
    SharedAdapter SharedAdapter;

    public ItemAdapter(ArrayList<Dish> dishList){
        this.dishList = dishList;
    }
    public class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView name_str;
        private TextView price_str;

        public ItemViewHolder(final View view) {
            super(view);
            name_str = view.findViewById(R.id.dish_name);
            price_str = view.findViewById(R.id.dish_price);

            setupRecyclerView(view.getContext(), view);
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dish, parent,false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        String name = dishList.get(position).getName();
        String price = dishList.get(position).getPrice();

        holder.name_str.setText(name);
        holder.price_str.setText(price);
        SharedAdapter.adapterDish(dishList.get(position));
        SharedAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dishList.size();
    }

    void setupRecyclerView(Context context, View view) {
        SharedRecyclerView = view.findViewById(R.id.shared_profile_list_view);
        SharedAdapter = new SharedAdapter();
        SharedRecyclerView.setAdapter(SharedAdapter);
        SharedRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
    }
}
