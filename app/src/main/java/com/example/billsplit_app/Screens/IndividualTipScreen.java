package com.example.billsplit_app.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.billsplit_app.Adapters.TipAdapter;
import com.example.billsplit_app.Dish;
import com.example.billsplit_app.InternalFiles;
import com.example.billsplit_app.MainActivity;
import com.example.billsplit_app.R;
import com.example.billsplit_app.User;

import org.json.JSONException;

public class IndividualTipScreen extends AppCompatActivity {

    com.example.billsplit_app.Adapters.TipAdapter TipAdapter;
    RecyclerView IndividualTipRecyclerView;
    int allTip = 0;
    String customTip = "";
    boolean tipsChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_bill_tip);

        MainActivity.allTipsSelected = false;

        ImageButton backButton = findViewById(R.id.back_button);
        Button submitButton = findViewById(R.id.submit_button);
        CheckBox sameTipButton = findViewById(R.id.same_tip_button);
        ImageView tipFrame = findViewById(R.id.indiv_tip_frame);
        EditText tipText = findViewById(R.id.indiv_tip_edit_text);
        TextView tipPercentage = findViewById(R.id.indiv_tip_percentage_sign);
        ImageButton tipTransit = findViewById(R.id.indiv_transit_enter_exit);
        TextView currentTotalText = findViewById(R.id.individual_tip_total);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_individual_bill_screen();
            }
        });

        tipTransit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopup(v,tipText);
                refreshPrereqs();
            }
        });

        sameTipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipText.getText().clear();
                if (sameTipButton.isChecked()) {
                    tipFrame.setVisibility(View.VISIBLE);
                    tipText.setVisibility(View.VISIBLE);
                    tipPercentage.setVisibility(View.VISIBLE);
                    tipTransit.setVisibility(View.VISIBLE);

                    MainActivity.allTipsSelected = true;
                }
                else{
                    tipFrame.setVisibility(View.INVISIBLE);
                    tipText.setVisibility(View.INVISIBLE);
                    tipPercentage.setVisibility(View.INVISIBLE);
                    tipTransit.setVisibility(View.INVISIBLE);

                    MainActivity.allTipsSelected = false;
                }
                allTip = 0;
                refreshPrereqs();
                updateTipsPercentage();
                MainActivity.finalTipTotal = 0.0;
                currentTotalText.setText("$ 0.00");
                TipAdapter.notifyDataSetChanged();
            }
        });

        tipText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
                if (!s1.isEmpty()) {
                    allTip = Integer.parseInt(s.toString());
                }
                else {
                    allTip = 0;
                }
                refreshPrereqs();
                TipAdapter.notifyDataSetChanged();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tipsChanged) {
                    for (User user : MainActivity.usersList) {
                        // getting the # of users sharing this dish, then adding current user's all shared dishes' prices together
                        double rawDishesPriceTotal = 0.0;
                        for (Dish dish : user.getSharedDishes()) {
                            rawDishesPriceTotal += Double.parseDouble(dish.getPrice()) / (double)dish.getNOfSharedUsers();
                        }

                        // setting user's total price of all shared dishes
                        user.setDishesRawPriceTotal(rawDishesPriceTotal);

                        // setting user's updated raw tax amount (taxPercentage * total price of all of user's dishes)
                        try {
                            user.setTax_total(rawDishesPriceTotal * InternalFiles.getSavedTax());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        user.setTips_times_total(0);
                    }
                    MainActivity.finalTaxTotal = updateTaxTotal();
                }

                for (User user : MainActivity.usersList) {
                    user.refreshTotal();
                }
                open_final_screen();
            }
        });

        setupRecyclerView(currentTotalText);
    }

    public void refreshPrereqs() {
        for (User u : MainActivity.usersList) {
            u.setTipsPercentage(allTip);
            u.refreshTotal();
        }
    }

    public double updateTipsPercentage() {
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

    private void open_individual_bill_screen() {
        Intent open_individual_bill_screen = new Intent(this, IndividualBillScreen.class);
        startActivity(open_individual_bill_screen);
    }
    private void open_final_screen() {
        Intent open_even_final_screen = new Intent(this, FinalScreen.class);
        startActivity(open_even_final_screen);
    }

    void setupRecyclerView(TextView currentTotalText) {
        TipAdapter = new TipAdapter(this,currentTotalText,tipsChanged);

        IndividualTipRecyclerView = findViewById(R.id.individual_tip_list_view);
        IndividualTipRecyclerView.setAdapter(TipAdapter);
        IndividualTipRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    public void ShowPopup(View view, EditText tipText) {
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
            tipText.setText("0");
            TipAdapter.notifyDataSetChanged();
            popupWindow.dismiss();
            CheckPopup();
        });

        tenButton.setOnClickListener(v -> {
            tipText.setText("10");
            TipAdapter.notifyDataSetChanged();
            popupWindow.dismiss();
            CheckPopup();
        });

        twelveButton.setOnClickListener(v -> {
            tipText.setText("12");
            TipAdapter.notifyDataSetChanged();
            popupWindow.dismiss();
            CheckPopup();
        });

        fifteenButton.setOnClickListener(v -> {
            tipText.setText("15");
            TipAdapter.notifyDataSetChanged();
            popupWindow.dismiss();
            CheckPopup();
        });

        popupSubmitButton.setOnClickListener(v -> {
            tipText.setText(customTip);
            TipAdapter.notifyDataSetChanged();
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
}