package com.example.billsplit_app.Adapters;

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
                    MainActivity.dishList.get(MainActivity.dishList.indexOf(adapterDish)).removeUser(u1);
                    u1.removeDish(adapterDish);
//                    updateUserTaxes(u1);
                    u1.refreshTotal();
                }
                else {
                    holder.shared_checkmark.setVisibility(View.VISIBLE);

                    MainActivity.dishList.get(MainActivity.dishList.indexOf(adapterDish)).addUser(u1);
                    u1.addDish(adapterDish);
//                    updateUserTaxes(u1);
                    u1.refreshTotal();
                }
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

    public void updateUserTaxes(User u1) {
        // getting the # of users sharing this dish, then adding current user's all shared dishes' prices together
        int sharedNum = 0;
        double rawDishesPriceTotal = 0.0;
        for (Dish dish : u1.getSharedDishes()) {
            sharedNum = dish.getNOfSharedUsers();
            rawDishesPriceTotal += Double.parseDouble(dish.getPrice());
        }

        // setting user's # of shared users between this dish
        u1.setSharedNum(sharedNum);

        // setting user's total price of all shared dishes
        u1.setDishesRawPriceTotal(rawDishesPriceTotal);

        // setting user's updated raw tax amount (taxPercentage * total price of all of user's dishes)
        try {
            u1.setTax_total((rawDishesPriceTotal / sharedNum) * InternalFiles.getSavedTax());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
