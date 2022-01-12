package com.example.billsplit_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;

public class IndividualBillTIp extends AppCompatActivity {

    EvenTipAdapter IndividualTipViewAdapter;
    RecyclerView IndividualTipRecyclerView;
    Boolean same_tip = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_bill_tip);

        ImageButton backButton = findViewById(R.id.back_button);
        CheckBox sameTipButton = findViewById(R.id.same_tip_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_individual_bill_screen();
            }
        });

        sameTipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                same_tip = sameTipButton.isChecked();

            }
        });

        setupRecyclerView();
    }

    private void open_individual_bill_screen() {
        Intent open_individual_bill_screen = new Intent(this, IndividualBillScreen.class);
        startActivity(open_individual_bill_screen);
    }

    void setupRecyclerView() {
        IndividualTipViewAdapter = new EvenTipAdapter();

        IndividualTipRecyclerView = findViewById(R.id.even_tip_list_view);
        IndividualTipRecyclerView.setAdapter(IndividualTipViewAdapter);
        IndividualTipRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }
}