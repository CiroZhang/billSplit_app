package com.example.billsplit_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class WelcomeScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText editPeopleText;
    EditText editCostText;
    TextView tax_text;
    Spinner locationSpin;
    Button individual_split_button;
    ImageButton scan_button;

    String people;
    String cost;
    String province;
    JSONObject tax;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);

        editPeopleText = (EditText) findViewById(R.id.people_edit_text);
        editCostText = (EditText) findViewById(R.id.cost_edit_text);
        locationSpin = (Spinner) findViewById(R.id.province_list);
        tax_text = (TextView) findViewById(R.id.tax_text);

        individual_split_button = (Button) findViewById(R.id.individual_split_button);
        scan_button = (ImageButton) findViewById(R.id.scan_button);

        scan_button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                scan_button.setBackground(getDrawable(R.drawable.scan_bill_clicked));

            }
        });

        individual_split_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject data = new JSONObject();
                boolean ready = false;
                people = editPeopleText.getText().toString();
                cost = editCostText.getText().toString();

                // check data plz
                if(true) {
                    try {
                        data.put("people", people);
                        data.put("cost", cost);
                        data.put("province", province);
                        data.put("tax", tax);

                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }

                    writeToJson("datas.json", data.toString());
                    open_individual_split_screen();
                }
            }
        });

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(WelcomeScreen.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.locations));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpin.setAdapter(myAdapter);
        locationSpin.setOnItemSelectedListener(this);

    }

    public void writeToJson(String fileName, String content) {
        File path = getApplicationContext().getFilesDir();

        try {
            FileOutputStream writer = new FileOutputStream(new File(path, fileName));
            writer.write(content.getBytes());
            writer.flush();
            writer.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readJson(String fileName) {
        try {
            InputStream is = getAssets().open(fileName);
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

            return responseStrBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    private void open_individual_split_screen() {
        Intent open_individual_split_screen = new Intent(this, IndividualBillScreen.class);
        startActivity(open_individual_split_screen);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        try {
            JSONObject tax_details = new JSONObject(readJson("res/tax_details.json"));
            String province = parent.getItemAtPosition(position).toString();
            tax = tax_details.getJSONObject(province);
            tax_text.setText(tax.getInt("PST") + "% PST  " + tax.getInt("GST") + "% GST  " + tax.getInt("HST")  + "% HST");


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}