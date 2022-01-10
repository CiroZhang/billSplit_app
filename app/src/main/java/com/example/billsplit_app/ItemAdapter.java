package com.example.billsplit_app;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>{
    SharedAdapter SharedAdapter = new SharedAdapter();

    public ItemAdapter(){}

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        private ImageButton delete_button;
        private TextView name_str;
        private TextView price_str;
        private ImageButton expand_collapse_button;
        private TextView shared_with_text;
        private ImageButton alcohol_image;
        private RecyclerView SharedRecyclerView;
        private Boolean IsCollapsed = false;
        private Boolean alcoholImageClicked = false;

        public ItemViewHolder(final View view) {
            super(view);
            delete_button = view.findViewById(R.id.delete_button);
            name_str = view.findViewById(R.id.dish_name);
            price_str = view.findViewById(R.id.dish_price);
            expand_collapse_button = view.findViewById(R.id.expand_collapse_button);
            shared_with_text = view.findViewById(R.id.shared_with_text);
            alcohol_image = view.findViewById(R.id.alcohol_image);
            SharedRecyclerView = view.findViewById(R.id.shared_profile_list_view);

            setupRecyclerView(view.getContext(), SharedRecyclerView);
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
        Dish dishItem = MainActivity.dishList.get(position);
        String name = dishItem.getName();
        String price = dishItem.getPrice();

        holder.name_str.setText(name);
        holder.price_str.setText(price);

        holder.delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.dishList.remove(dishItem);
                ItemAdapter.this.notifyDataSetChanged();
                System.out.println(MainActivity.dishList.size());
            }
        });

        holder.expand_collapse_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.IsCollapsed) {
                    holder.expand_collapse_button.setBackgroundResource(R.drawable.expand_button);
                    holder.SharedRecyclerView.setVisibility(View.GONE);
                    holder.shared_with_text.setVisibility(View.GONE);
                    holder.alcohol_image.setVisibility(View.GONE);
                    holder.IsCollapsed = true;
                }
                else {
                    holder.expand_collapse_button.setBackgroundResource(R.drawable.collapse_button);
                    holder.SharedRecyclerView.setVisibility(View.VISIBLE);
                    holder.shared_with_text.setVisibility(View.VISIBLE);
                    holder.alcohol_image.setVisibility(View.VISIBLE);
                    holder.IsCollapsed = false;
                }
            }
        });

        holder.alcohol_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.alcoholImageClicked) {
                    holder.alcohol_image.setBackgroundResource(R.drawable.alcohol_checked);
                    holder.alcoholImageClicked = true;
                }
                else {
                    holder.alcohol_image.setBackgroundResource(R.drawable.alcohol_unchecked);
                    holder.alcoholImageClicked = false;
                }
            }
        });

        SharedAdapter.adapterDish(dishItem);
        SharedAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return MainActivity.dishList.size();
    }

    void setupRecyclerView(Context context, RecyclerView SharedRecyclerView) {
        SharedRecyclerView.setAdapter(SharedAdapter);
        SharedRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
    }

    public void UpdateSharedAdapter() {
        if (SharedAdapter == null) {
            return;
        }
        SharedAdapter.notifyDataSetChanged();
    }
}
