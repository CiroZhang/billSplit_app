package com.example.billsplit_app;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SharedAdapter extends RecyclerView.Adapter<SharedAdapter.SharedViewHolder>{

    private Dish adapterDish;
    public SharedAdapter(){}

    public class SharedViewHolder extends RecyclerView.ViewHolder{
        private TextView shared_profile_user_name;
        private ImageView shared_profile_background;
        private TextView shared_profile_short_user_name;
        private ImageView shared_checkmark;

        public SharedViewHolder(@NonNull View itemView) {
            super(itemView);
            shared_profile_background = itemView.findViewById(R.id.shared_profile_background);
            shared_profile_user_name = itemView.findViewById(R.id.shared_profile_user_name);
            shared_profile_short_user_name = itemView.findViewById(R.id.shared_profile_short_user_name);
            shared_checkmark = itemView.findViewById(R.id.shared_checkmark);

        }
    }

    @NonNull
    @Override
    public SharedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shared_profile, parent,false);
        return new SharedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SharedViewHolder holder, int position) {
        User u1 = MainActivity.usersList.get(position);
        String name = u1.getUsername();
        holder.shared_profile_user_name.setText(name);
        if (!name.isEmpty()) { holder.shared_profile_short_user_name.setText(name.substring(0,1)); }
        holder.shared_profile_background.getBackground().setTint(u1.getColor());


        holder.shared_profile_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.shared_checkmark.getVisibility() == View.VISIBLE) {
                    holder.shared_checkmark.setVisibility(View.INVISIBLE);
                    u1.removeDish(adapterDish);
                }
                else {
                    holder.shared_checkmark.setVisibility(View.VISIBLE);
                    u1.addDish(adapterDish);
                }
                System.out.println(u1.getUsername() + ": " + u1.getDishes());
            }
        });
    }

    @Override
    public int getItemCount() {
        return MainActivity.usersList.size();
    }

    public void adapterDish(Dish dish) {
        adapterDish = dish;
    }
}
