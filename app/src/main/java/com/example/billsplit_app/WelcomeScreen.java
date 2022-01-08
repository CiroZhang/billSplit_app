package com.example.billsplit_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

String people;
String cost;
TextView tax_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);
        editPeopleText = (EditText)findViewById(R.id.people_edit_text);
        editCostText = (EditText)findViewById(R.id.cost_edit_text);
        people = editPeopleText.getText().toString();
        cost = editCostText.getText().toString();

        Spinner locationSpin = (Spinner) findViewById(R.id.province_list);
        Button individual_split_button = findViewById(R.id.individual_split_button);
<<<<<<< HEAD
        tax_text = (TextView) findViewById(R.id.tax_text);
=======
>>>>>>> 19bec17e924a649a2a6006c14f5dae6c3fbde999

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
<<<<<<< HEAD

    @SuppressLint("SetTextI18n")
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int pst = 0;
        int gst = 0;
        int hst = 0;

        String province = parent.getItemAtPosition(position).toString();
        if (province.equals("Alberta")){gst = 5;}
        if (province.equals("British Columbia")){pst = 7; gst = 5; }
        if (province.equals("Manitoba")){pst = 7; gst = 5; }
        if (province.equals("New Brunswick")){hst = 15; }
        if (province.equals("Newfoundland and Labrador")){hst = 15; }
        if (province.equals("Northwest Territories")){gst = 5;}
        if (province.equals("Nova Scotia")){hst = 15; }
        if (province.equals("Nunavut")){gst = 5;}
        if (province.equals("Ontario")){hst = 13; }
        if (province.equals("Prince Edward Island")){hst = 15; }
        if (province.equals("Quebec")){pst = 10; gst = 5; }
        if (province.equals("Saskatchewan")){pst = 6; gst = 5; }
        if (province.equals("Yukon")){gst = 5;}
        tax_text.setText(pst +"% PST  " + gst +"% GST  " + hst +"% HST");



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
=======
>>>>>>> 19bec17e924a649a2a6006c14f5dae6c3fbde999
}