package com.example.billsplit_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class WelcomeScreen extends AppCompatActivity {

EditText editPeopleText;
EditText editCostText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);
        editPeopleText = (EditText)findViewById(R.id.people_edit_text);
        editCostText = (EditText)findViewById(R.id.cost_edit_text);
        String people = editPeopleText.getText().toString();
        String cost = editCostText.getText().toString();

        Spinner locationSpin = (Spinner) findViewById(R.id.province_list);
        Button individual_split_button = findViewById(R.id.individual_split_button);

        individual_split_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_individual_split_screen();
            }
        });

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(WelcomeScreen.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.locations));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpin.setAdapter(myAdapter);



    }

    private void open_individual_split_screen() {
        Intent open_individual_split_screen = new Intent(this, IndividualBillScreen.class);
        startActivity(open_individual_split_screen);
    }
}