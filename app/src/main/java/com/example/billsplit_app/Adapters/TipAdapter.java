package com.example.billsplit_app.Adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.billsplit_app.MainActivity;
import com.example.billsplit_app.R;
import com.example.billsplit_app.User;

public class TipAdapter extends RecyclerView.Adapter<TipAdapter.TipViewHolder> {
    Boolean popupShown = false;

    public class TipViewHolder extends RecyclerView.ViewHolder{
        private TextView name_str;
        private EditText tips;
        private ImageView textFrame;
        private TextView percentageSign;
        private ImageButton transitButton;
        private ImageView layoutGreyed;
        private TextWatcher tw = new TextWatcher() {
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



        public TipViewHolder(@NonNull View itemView) {
            super(itemView);
            name_str = itemView.findViewById(R.id.tip_user_name);
            tips = itemView.findViewById(R.id.tip_edit_text);
            textFrame = itemView.findViewById(R.id.tip_frame);
            percentageSign = itemView.findViewById(R.id.tip_percentage_sign);
            transitButton = itemView.findViewById(R.id.transit_enter_exit);
            layoutGreyed = itemView.findViewById(R.id.tip_layout_greyed);
        }
    }

    @NonNull
    @Override
    public TipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tip, parent,false);
        return new TipViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TipViewHolder holder, int position) {
        String name = MainActivity.usersList.get(position).getUsername();
        User current = MainActivity.usersList.get(position);
        holder.name_str.setText(name);

        if (MainActivity.allTipsSelected) {
            holder.tips.setEnabled(false);
            holder.transitButton.setEnabled(false);
            holder.layoutGreyed.setVisibility(View.VISIBLE);
        }
        else {
            holder.tips.setEnabled(true);
            holder.transitButton.setEnabled(true);
            holder.layoutGreyed.setVisibility(View.INVISIBLE);
        }

        holder.tips.removeTextChangedListener(holder.tw);
        holder.tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    MainActivity.usersList.get(MainActivity.usersList.indexOf(current)).setTips(Integer.parseInt(s.toString()));
//                    current.setTips(Integer.parseInt(s.toString()));
                }
                else {
                    MainActivity.usersList.get(MainActivity.usersList.indexOf(current)).setTips(0);
//                    current.setTips(0);
                }
            }
        };
        holder.tips.addTextChangedListener(holder.tw);
    }

    @Override
    public int getItemCount() {
        return MainActivity.usersList.size();
    }
}
