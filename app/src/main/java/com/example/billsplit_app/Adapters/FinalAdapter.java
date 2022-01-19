package com.example.billsplit_app.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.billsplit_app.MainActivity;
import com.example.billsplit_app.R;
import com.example.billsplit_app.User;

import java.util.ArrayList;

public class FinalAdapter extends RecyclerView.Adapter<FinalAdapter.finalViewHolder>{

    private Context context;
    private ArrayList<User> userList;

    public FinalAdapter(@NonNull Context context, @NonNull ArrayList<User> userList){
        this.context = context;
        this.userList = userList;
    }

    public class finalViewHolder extends RecyclerView.ViewHolder{
        private TextView name_str;
        private ImageView profile_background;
        private TextView profile_short_user_name;
        private TextView profile_price;

        public finalViewHolder(@NonNull View itemView) {
            super(itemView);
            name_str = itemView.findViewById(R.id.final_profile_user_name);
            profile_background = itemView.findViewById(R.id.final_profile_background);
            profile_short_user_name = itemView.findViewById(R.id.final_profile_short_user_name);
            profile_price = itemView.findViewById(R.id.final_profile_total_price);
        }
    }

    @NonNull
    @Override
    public finalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_final_profile, parent,false);
        return new finalViewHolder(itemView);
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull FinalAdapter.finalViewHolder holder, int position) {
        User u1 = userList.get(position);
        u1.refreshTotal();
        holder.name_str.setText(u1.getUsername());
        holder.profile_background.getBackground().setTint(userList.get(position).getColor());
        holder.profile_short_user_name.setText(u1.getUsername().substring(0,1));
        holder.profile_price.setText("$ " + String.format("%.2f", userList.get(position).getTotal()));

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}

