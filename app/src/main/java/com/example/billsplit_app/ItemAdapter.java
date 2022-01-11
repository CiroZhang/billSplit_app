package com.example.billsplit_app;



import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

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
        private Boolean alcoholTaxApplicable = false;

        private TextWatcher textWatcherName = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        private TextWatcher textWatcherPrice = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

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
        holder.name_str.removeTextChangedListener(holder.textWatcherName);
        holder.price_str.removeTextChangedListener(holder.textWatcherPrice);
        Dish dishItem = MainActivity.dishList.get(position);
        String name = dishItem.getName();
        String price = dishItem.getPrice();

        holder.name_str.setText(name);
        holder.price_str.setText(price);

        if (dishItem.isCollapsed()) {
            holder.expand_collapse_button.setBackgroundResource(R.drawable.expand_button);
            holder.SharedRecyclerView.setVisibility(View.GONE);
            holder.shared_with_text.setVisibility(View.GONE);
            holder.alcohol_image.setVisibility(View.GONE);
        }
        else {
            holder.expand_collapse_button.setBackgroundResource(R.drawable.collapse_button);
            holder.SharedRecyclerView.setVisibility(View.VISIBLE);
            holder.shared_with_text.setVisibility(View.VISIBLE);
            holder.alcohol_image.setVisibility(View.VISIBLE);
        }

        holder.delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.dishList.remove(dishItem);
                notifyDataSetChanged();
                System.out.println(MainActivity.dishList.size());
            }
        });

        holder.expand_collapse_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dishItem.isCollapsed()) {
                    dishItem.setCollapsed(true);
                }
                else {
                    dishItem.setCollapsed(false);
                }
                notifyDataSetChanged();
            }
        });

        holder.alcohol_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.alcoholImageClicked) {
                    holder.alcohol_image.setBackgroundResource(R.drawable.alcohol_checked);
                    holder.alcoholImageClicked = true;
                    holder.alcoholTaxApplicable = true;
                }
                else {
                    holder.alcohol_image.setBackgroundResource(R.drawable.alcohol_unchecked);
                    holder.alcoholImageClicked = false;
                }
            }
        });

        holder.textWatcherName = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                dishItem.setName(s.toString());
                System.out.println(s.toString());
            }
        };

        holder.textWatcherPrice = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                dishItem.setPrice(s.toString());
                System.out.println(s.toString());
            }
        };

        holder.name_str.addTextChangedListener(holder.textWatcherName);
        holder.price_str.addTextChangedListener(holder.textWatcherPrice);

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
