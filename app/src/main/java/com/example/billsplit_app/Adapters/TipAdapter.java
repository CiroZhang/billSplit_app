package com.example.billsplit_app.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
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

import com.example.billsplit_app.Dish;
import com.example.billsplit_app.InternalFiles;
import com.example.billsplit_app.MainActivity;
import com.example.billsplit_app.R;
import com.example.billsplit_app.User;

import org.json.JSONException;

public class TipAdapter extends RecyclerView.Adapter<TipAdapter.TipViewHolder> {
    Boolean popupShown = false;
    Context context;
    TextView totalTextView;

    public TipAdapter(Context context) {
        this.context = context;
    }
    public TipAdapter(Context context, TextView currentTotalText) {
        this.context = context;
        this.totalTextView = currentTotalText;
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
        User current = MainActivity.usersList.get(position);
        holder.name_str.setText(current.getUsername());
        holder.tips.setText("0");
        holder.tips.getText().clear();

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
        holder.tips.setText("0");
        holder.tips.getText().clear();
        holder.tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            @Override
            public void afterTextChanged(Editable s) {
                // getting the # of users sharing this dish, then adding current user's all shared dishes' prices together
                int sharedNum = 0;
                double rawDishesPriceTotal = 0.0;
                for (Dish dish : current.getSharedDishes()) {
                    sharedNum = dish.getNOfSharedUsers();
                    rawDishesPriceTotal += Double.parseDouble(dish.getPrice());
                }

                // setting user's # of shared users between this dish
                current.setSharedNum(sharedNum);

                // setting user's total price of all shared dishes
                current.setDishesRawPriceTotal(rawDishesPriceTotal);

                // setting user's updated raw tax amount (taxPercentage * total price of all of user's dishes)
                try {
                    current.setTax_total((rawDishesPriceTotal / sharedNum) * InternalFiles.getSavedTax());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (!s.toString().isEmpty()) {
                    int enteredTipPercentage = Integer.parseInt(s.toString());

                    // setting user's updated tip percentage
                    current.setTipsPercentage(enteredTipPercentage);

                    // setting user's updated raw tip amount (tipPercentage * total price of all of user's dishes)
                    current.setTips_times_total(((rawDishesPriceTotal / sharedNum) * enteredTipPercentage) / 100.0);
                }
                else {
                    // setting user's updated tip percentage to 0
                    current.setTipsPercentage(0);

                    // setting user's updated raw tip amount to 0
                    current.setTips_times_total(0);
                }

                // updating MainActivity's final tip total
                MainActivity.finalTipTotal = updateTipsTotal();
                // updating MainActivity's final tax total
                MainActivity.finalTaxTotal = updateTaxTotal();

                totalTextView.setText("$ " + String.format("%.2f",MainActivity.finalTipTotal));
            }
        };
        holder.tips.addTextChangedListener(holder.tw);
    }

    public double updateTipsTotal() {
        double total = 0.0;
        for (User u : MainActivity.usersList) {
            total += u.getTips_times_total();
        }
        return total;
    }

    public double updateTaxTotal() {
        double total = 0.0;
        for (User u : MainActivity.usersList) {
            total += u.getTax_total();
        }
        return total;
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

        ImageView popupBackground = popupView.findViewById(R.id.popup_background);
        ImageButton popupCloseButton = popupView.findViewById(R.id.back_button);
        ImageView zeroButton = popupView.findViewById(R.id.tip_button1);
        ImageView tenButton = popupView.findViewById(R.id.tip_button2);
        ImageView twelveButton = popupView.findViewById(R.id.tip_button3);
        ImageView fifteenButton = popupView.findViewById(R.id.tip_button4);
        EditText popupTipText = popupView.findViewById(R.id.popup_tip_edit_text);
        Button popupSubmitButton = popupView.findViewById(R.id.popup_submit_button);

        popupBackground.setOnClickListener(v -> {
            // This is just here to prevent popup from closing when clicking the background
        });

        popupCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        zeroButton.setOnClickListener(v -> {
            u.setTipsPercentage(0);

            popupWindow.dismiss();
            popupShown = false;
            CheckPopup();
        });

        tenButton.setOnClickListener(v -> {
            u.setTipsPercentage(10);
            popupWindow.dismiss();
            popupShown = false;
            CheckPopup();
        });

        twelveButton.setOnClickListener(v -> {
            u.setTipsPercentage(12);
            popupWindow.dismiss();
            popupShown = false;
            CheckPopup();
        });

        fifteenButton.setOnClickListener(v -> {
            u.setTipsPercentage(15);
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
                    u.setTipsPercentage(Integer.parseInt(s.toString()));
                } else {
                    u.setTipsPercentage(0);
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
