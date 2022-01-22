package com.example.billsplit_app.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.media.MediaTimestamp;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.billsplit_app.MainActivity;
import com.example.billsplit_app.R;
import com.example.billsplit_app.Screens.IndividualBillScreen;
import com.example.billsplit_app.User;

import java.util.ArrayList;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>{

    private Context context;

    public ProfileAdapter(@NonNull Context context){
        this.context = context;
    }

    public class ProfileViewHolder extends RecyclerView.ViewHolder{
        private TextView name_str;
        private ImageButton profile_background;
        private TextView profile_short_user_name;

        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_background = itemView.findViewById(R.id.profile_background);
            name_str = itemView.findViewById(R.id.profile_user_name);
            profile_short_user_name = itemView.findViewById(R.id.profile_short_user_name);
        }
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_profile, parent,false);
        return new ProfileViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        User currentUser = MainActivity.usersList.get(position);
        String name = currentUser.getUsername();
        holder.name_str.setText(name);
        holder.profile_background.getBackground().setTint(currentUser.getColor());
        holder.profile_short_user_name.setText(name.substring(0,1));

        holder.profile_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopup(v,currentUser);
                CheckPopup();
            }
        });
    }

    @Override
    public int getItemCount() {
        return MainActivity.usersList.size();
    }

    public void ShowPopup(View view, User user) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View popupView = inflater.inflate(R.layout.change_remove_profile_popup, null, false);
        CheckPopup();

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                CheckPopup();
                return true;
            }
        });
        Button delete = popupView.findViewById(R.id.delete);
        Button edit = popupView.findViewById(R.id.edit);
        EditText editName = popupView.findViewById(R.id.new_name);

        delete.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                MainActivity.usersList.remove(user);
                notifyDataSetChanged();
                ((IndividualBillScreen)context).refreshAdapters();
                popupWindow.dismiss();
                CheckPopup();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.usersList.get(MainActivity.usersList.indexOf(user)).setUsername(editName.getText().toString());
                notifyDataSetChanged();
                ((IndividualBillScreen)context).refreshAdapters();
                popupWindow.dismiss();
                CheckPopup();
            }
        });
    }

    private void CheckPopup() {
    }
}
