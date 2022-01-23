package com.example.billsplit_app.Adapters;

<<<<<<< HEAD
import android.annotation.SuppressLint;
=======
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
>>>>>>> 99d5c924caf4132165fe11b4a8db7394960a5b1a
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.billsplit_app.Dish;
import com.example.billsplit_app.InternalFiles;
import com.example.billsplit_app.MainActivity;
import com.example.billsplit_app.R;
import com.example.billsplit_app.User;

import org.json.JSONException;

public class SharedAdapter extends RecyclerView.Adapter<SharedAdapter.SharedViewHolder>{
    private Dish adapterDish;
    public int num;

    public SharedAdapter(){
    }

    public class SharedViewHolder extends RecyclerView.ViewHolder{
        private TextView shared_profile_user_name;
        private ImageView shared_profile_background;
        private TextView shared_profile_short_user_name;
        private ImageView shared_checkmark;
        private TextView stuff;

        public SharedViewHolder(@NonNull View itemView) {
            super(itemView);
            shared_profile_background = itemView.findViewById(R.id.shared_profile_background);
            shared_profile_user_name = itemView.findViewById(R.id.shared_profile_user_name);
            shared_profile_short_user_name = itemView.findViewById(R.id.shared_profile_short_user_name);
            shared_checkmark = itemView.findViewById(R.id.shared_checkmark);
            stuff = itemView.findViewById(R.id.stuff);
            stuff.setText(Integer.toString(MainActivity.dishList.size()-1));
        }
    }

    @NonNull
    @Override
    public SharedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shared_profile, parent,false);
        return new SharedViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SharedViewHolder holder, int position) {

        User u1 = MainActivity.usersList.get(position);
        String name = u1.getUsername();
        holder.shared_profile_user_name.setText(name);

//        GradientDrawable gd = new GradientDrawable();
//        gd.setColor(Color.parseColor("#f9f9f9"));
//        gd.setShape(GradientDrawable.OVAL);
//        gd.setStroke(2,Color.parseColor("#D1D1D1"));
//        holder.shared_profile_background.setBackground(gd);
        holder.shared_profile_background.getBackground().setTint(u1.getColor());
        holder.shared_profile_short_user_name.setTextColor(Color.BLACK);

        if (!name.isEmpty()) { holder.shared_profile_short_user_name.setText(name.substring(0,1)); }

        holder.shared_profile_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.shared_checkmark.getVisibility() == View.VISIBLE) {
<<<<<<< HEAD
                    System.out.println(holder.stuff.getText().toString());
=======
//                    GradientDrawable gd = new GradientDrawable();
//                    gd.setColor(Color.parseColor("#f9f9f9"));
//                    gd.setShape(GradientDrawable.OVAL);
//                    gd.setStroke(2,Color.parseColor("#D1D1D1"));
//                    holder.shared_profile_background.setBackground(gd);
//                    holder.shared_profile_short_user_name.setTextColor(Color.BLACK);
>>>>>>> 99d5c924caf4132165fe11b4a8db7394960a5b1a
                    holder.shared_checkmark.setVisibility(View.INVISIBLE);
                    MainActivity.dishList.get(Integer.parseInt(holder.stuff.getText().toString())).removeUser(u1);
                    u1.removeDish(adapterDish);
                }
                else {
<<<<<<< HEAD
                    System.out.println(holder.stuff.getText().toString());
=======
//                    holder.shared_profile_short_user_name.setTextColor(Color.WHITE);
>>>>>>> 99d5c924caf4132165fe11b4a8db7394960a5b1a
                    holder.shared_checkmark.setVisibility(View.VISIBLE);
                    MainActivity.dishList.get(Integer.parseInt(holder.stuff.getText().toString())).addUser(u1);
                    u1.addDish(adapterDish);
                }
                u1.refreshTotal();
            }
        });
    }

    @Override
    public int getItemCount() {
        return MainActivity.usersList.size();
    }

    public void adapterDish(Dish dish) {
        adapterDish = dish;
        System.out.println(dish);
    }

    public void setAllColour(SharedViewHolder holder) {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor("#f9f9f9"));
        gd.setShape(GradientDrawable.OVAL);
        gd.setStroke(2,Color.parseColor("#D1D1D1"));
        holder.shared_profile_background.setBackground(gd);
        holder.shared_profile_short_user_name.setTextColor(Color.BLACK);
        holder.shared_checkmark.setVisibility(View.INVISIBLE);
    }
}
