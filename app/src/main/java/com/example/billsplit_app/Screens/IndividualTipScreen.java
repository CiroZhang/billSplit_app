package com.example.billsplit_app.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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
import com.example.billsplit_app.MainActivity;
import com.example.billsplit_app.R;
import com.example.billsplit_app.Screens.IndividualBillScreen;
import com.example.billsplit_app.User;

public class IndividualTipScreen extends AppCompatActivity {

    com.example.billsplit_app.Adapters.TipAdapter TipAdapter;
    RecyclerView IndividualTipRecyclerView;
    int allTip = 0;

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

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_individual_bill_screen();
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
                if (!s.toString().isEmpty()) {
                    allTip = Integer.parseInt(s.toString());
                }
                else {
                    allTip = 0;
                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sameTipButton.isChecked()) {
                    for (User user : MainActivity.usersList) {
                        user.setTips(allTip);
                    }
                }
                for (User user : MainActivity.usersList) {
                    System.out.println(user.getTips());
                }

                open_final_screen();
            }
        });

        setupRecyclerView();
    }

    private void open_individual_bill_screen() {
        Intent open_individual_bill_screen = new Intent(this, IndividualBillScreen.class);
        startActivity(open_individual_bill_screen);
    }
    private void open_final_screen() {
        Intent open_even_final_screen = new Intent(this, FinalScreen.class);
        startActivity(open_even_final_screen);
    }

    void setupRecyclerView() {
        TipAdapter = new TipAdapter();

        IndividualTipRecyclerView = findViewById(R.id.individual_tip_list_view);
        IndividualTipRecyclerView.setAdapter(TipAdapter);
        IndividualTipRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    public void CheckPopup() {
    }
}