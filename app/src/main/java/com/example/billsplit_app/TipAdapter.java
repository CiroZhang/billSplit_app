package com.example.billsplit_app;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TipAdapter extends RecyclerView.Adapter<TipAdapter.TipViewHolder>{

    public class TipViewHolder extends RecyclerView.ViewHolder{
        private TextView name_str;
        private EditText tips;
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
                current.setTips(Integer.parseInt(s.toString()));
            }
        };
        holder.tips.addTextChangedListener(holder.tw);
    }

    @Override
    public int getItemCount() {
        return MainActivity.usersList.size();
    }
}
