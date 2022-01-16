package com.example.billsplit_app.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.billsplit_app.Screens.IndividualTipScreen;
import com.example.billsplit_app.User;

public class TipAdapter extends RecyclerView.Adapter<TipAdapter.TipViewHolder> {
    Boolean popupShown = false;
    Context context;

    public TipAdapter(Context context) {
        this.context = context;
    }

    public class TipViewHolder extends RecyclerView.ViewHolder {
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tip, parent, false);
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
            holder.name_str.setTextColor(context.getResources().getColor(R.color.grey2));
            holder.layoutGreyed.setVisibility(View.VISIBLE);
        } else {
            holder.tips.setEnabled(true);
            holder.transitButton.setEnabled(true);
            holder.name_str.setTextColor(context.getResources().getColor(R.color.black));
            holder.layoutGreyed.setVisibility(View.INVISIBLE);
        }

        holder.transitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopup(v, current);
            }
        });

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
                } else {
                    MainActivity.usersList.get(MainActivity.usersList.indexOf(current)).setTips(0);
//                    current.setTips(0);
                }
            }
        };
        holder.tips.addTextChangedListener(holder.tw);
    }

    public void ShowPopup(View view, User u) {
        View popupView = LayoutInflater.from(view.getContext()).inflate(R.layout.open_tip_popup, null, false);
        popupShown = true;
        CheckPopup();

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                popupShown = false;
                CheckPopup();
                return true;
            }
        });

        ImageView zeroButton = popupView.findViewById(R.id.tip_button1);
        ImageView tenButton = popupView.findViewById(R.id.tip_button2);
        ImageView twelveButton = popupView.findViewById(R.id.tip_button3);
        ImageView fifteenButton = popupView.findViewById(R.id.tip_button4);
        EditText popupTipText = popupView.findViewById(R.id.popup_tip_edit_text);
        Button popupSubmitButton = popupView.findViewById(R.id.popup_submit_button);

        zeroButton.setOnClickListener(v -> {
            u.setTips(0);
            popupWindow.dismiss();
            popupShown = false;
            CheckPopup();
        });

        tenButton.setOnClickListener(v -> {
            u.setTips(10);
            popupWindow.dismiss();
            popupShown = false;
            CheckPopup();
        });

        twelveButton.setOnClickListener(v -> {
            u.setTips(12);
            popupWindow.dismiss();
            popupShown = false;
            CheckPopup();
        });

        fifteenButton.setOnClickListener(v -> {
            u.setTips(15);
            popupWindow.dismiss();
            popupShown = false;
            CheckPopup();
        });

        popupSubmitButton.setOnClickListener(v -> {
            popupWindow.dismiss();
            popupShown = false;
            CheckPopup();
        });

        popupTipText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    u.setTips(Integer.parseInt(s.toString()));
                } else {
                    u.setTips(0);
                }
            }
        });
    }

    public void CheckPopup() {
    }

    @Override
    public int getItemCount() {
        return MainActivity.usersList.size();
    }
}
