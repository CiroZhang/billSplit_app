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

    String people;
    String cost;
    TextView tax_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);

        editPeopleText = (EditText) findViewById(R.id.people_edit_text);
        editCostText = (EditText) findViewById(R.id.cost_edit_text);

        Spinner locationSpin = (Spinner) findViewById(R.id.province_list);
        Button individual_split_button = findViewById(R.id.individual_split_button);
        tax_text = (TextView) findViewById(R.id.tax_text);

        individual_split_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject data = new JSONObject();
                people = editPeopleText.getText().toString();
                cost = editCostText.getText().toString();
                // check data plz


                try {
                    data.put("people", people);
                    data.put("cost", cost);
                    writeToJson("datas.json", data.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                open_individual_split_screen();
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
        int pst, gst, hst;

        try {
            JSONObject tax_details = new JSONObject(readJson("res/tax_details.json"));
            String province = parent.getItemAtPosition(position).toString();
            JSONObject current = tax_details.getJSONObject(province);

            pst = current.getInt("PST");
            gst = current.getInt("GST");
            hst = current.getInt("HST");
            tax_text.setText(pst + "% PST  " + gst + "% GST  " + hst + "% HST");

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}