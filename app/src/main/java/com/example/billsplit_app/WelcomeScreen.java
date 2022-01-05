package com.example.billsplit_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
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

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(WelcomeScreen.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.locations));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpin.setAdapter(myAdapter);



    }
}