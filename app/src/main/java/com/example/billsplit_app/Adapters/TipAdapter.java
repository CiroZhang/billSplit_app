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
    Context context;
    TextView totalTextView;
    String customTip = "";

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

        if (MainActivity.allTipsSelected) {
            holder.tips.setText("0");
            holder.tips.getText().clear();
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
                ShowPopup(v, current, holder);
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

            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            @Override
            public void afterTextChanged(Editable s) {
                MainActivity.tipsChanged = true;
                // indiv screen
                if (!MainActivity.check()) {
                    // getting the # of users sharing this dish, then adding current user's all shared dishes' prices together
                    double rawDishesPriceTotal = 0.0;
                    for (Dish dish : current.getSharedDishes()) {
                        rawDishesPriceTotal += Double.parseDouble(dish.getPrice()) / (double)dish.getNOfSharedUsers();
                    }

                    // setting user's total price of all shared dishes
                    current.setDishesRawPriceTotal(rawDishesPriceTotal);

                    // setting user's updated raw tax amount (taxPercentage * total price of all of user's dishes)
                    try {
                        current.setTax_total(rawDishesPriceTotal * InternalFiles.getSavedTax());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (!MainActivity.allTipsSelected) {
                        if (!s.toString().isEmpty()) {
                            int enteredTipPercentage = Integer.parseInt(s.toString());

                            // setting user's updated tip percentage
                            current.setTipsPercentage(enteredTipPercentage);

                            // setting user's updated raw tip amount (tipPercentage * total price of all of user's dishes)
                            current.setTips_times_total((rawDishesPriceTotal * enteredTipPercentage) / 100.0);
                        }
                        else {
                            // setting user's updated tip percentage to 0
                            current.setTipsPercentage(0);

                            // setting user's updated raw tip amount to 0
                            current.setTips_times_total(0);
                        }
                    }
                    try {
                        MainActivity.finalTipTotal = updateTipsTotal();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    totalTextView.setText("$ " + String.format("%.2f",MainActivity.finalTipTotal));
                }

                // even screen
                else {
                    // setting user's tax total
                    try {
                        current.setTax_total(((InternalFiles.getSavedCost() * InternalFiles.getSavedTax()) / (double)MainActivity.nOfUsers));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (!MainActivity.allTipsSelected) {
                        if (!s.toString().isEmpty()) {
                            int enteredTipPercentage = Integer.parseInt(s.toString());

                            // setting user's updated tip percentage
                            current.setTipsPercentage(enteredTipPercentage);

                            // setting user's updated raw tip amount (tipPercentage * saved total cost)
                            try {
                                current.setTips_times_total(InternalFiles.getSavedCost() * (enteredTipPercentage / 100.0));
                                System.out.println(current.getTips_times_total());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            // setting user's updated tip percentage to 0
                            current.setTipsPercentage(0);

                            // setting user's updated raw tip amount to 0
                            current.setTips_times_total(0);
                        }
                    }
                    try {
                        MainActivity.finalTipTotal = updateTipsTotal();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        totalTextView.setText("$ " + String.format("%.2f",(InternalFiles.getSavedCost() + MainActivity.finalTipTotal)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                MainActivity.finalTaxTotal = updateTaxTotal();
            }
        };
        holder.tips.addTextChangedListener(holder.tw);
    }

    public double updateTipsTotal() throws JSONException {
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

    public void ShowPopup(View view, User u, TipViewHolder holder) {
        View popupView = LayoutInflater.from(view.getContext()).inflate(R.layout.open_tip_popup, null, false);
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
            holder.tips.setText("0");
            this.notifyDataSetChanged();
            popupWindow.dismiss();
            CheckPopup();
        });

        tenButton.setOnClickListener(v -> {
            holder.tips.setText("10");
            this.notifyDataSetChanged();
            popupWindow.dismiss();
            CheckPopup();
        });

        twelveButton.setOnClickListener(v -> {
            holder.tips.setText("12");
            this.notifyDataSetChanged();
            popupWindow.dismiss();
            CheckPopup();
        });

        fifteenButton.setOnClickListener(v -> {
            holder.tips.setText("15");
            this.notifyDataSetChanged();
            popupWindow.dismiss();
            CheckPopup();
        });

        popupSubmitButton.setOnClickListener(v -> {
            holder.tips.setText(customTip);
            this.notifyDataSetChanged();
            popupWindow.dismiss();
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
                    customTip = s.toString();
                }
                else {
                    customTip = "";
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
