package com.example.billsplit_app.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.billsplit_app.InternalFiles;
import com.example.billsplit_app.MainActivity;
import com.example.billsplit_app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class WelcomeScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText editPeopleText;
    EditText editCostText;
    TextView tax_text;
    Spinner locationSpin;
    Button individual_split_button;
    Button even_split_button;
    ImageButton scan_button;
    ImageButton scan_cancel_button;

    String people;
    String cost;
    String province;
    JSONObject tax;
    double totalBillCost = 0.0;
    boolean editPeopleTextFilled = false;
    boolean editCostTextFilled = false;
    boolean editCostTextValid = false;
    boolean locationSpinFilled = false;
    InternalFiles internalFiles = new InternalFiles();
    ArrayList<String> list = new ArrayList<>();

    public WelcomeScreen() throws JSONException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);

        editPeopleText = findViewById(R.id.people_edit_text);
        editCostText = findViewById(R.id.cost_edit_text);
        locationSpin = findViewById(R.id.province_list);
        tax_text = findViewById(R.id.tax_text);

        individual_split_button = findViewById(R.id.individual_split_button);
        even_split_button = findViewById(R.id.even_split_button);
        Object obj = getResources().getStringArray(R.array.locations);
        try {
            list.add(InternalFiles.getSavedLocation());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        list.add("Alberta");
        list.add("British Columbia");
        list.add("Manitoba");
        list.add("New Brunswick");
        list.add("Newfoundland and Labrador");
        list.add("Northwest Territories");
        list.add("Nova Scotia");
        list.add("Nunavut");
        list.add("Ontario");
        list.add("Prince Edward Island");
        list.add("Quebec");
        list.add("Saskatchewan");
        list.add("Yukon");

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(WelcomeScreen.this,
                android.R.layout.simple_list_item_1, list);
        myAdapter.notifyDataSetChanged();


        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpin.setAdapter(myAdapter);
        locationSpin.setOnItemSelectedListener(this);

//        individual_split_button.setActivated(false);
//        even_split_button.setActivated(false);

//        scan_button.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("UseCompatLoadingForDrawables")
//            @Override
//            public void onClick(View v) {
//                scan_cancel_button.setVisibility(View.VISIBLE);
//                scan_button.setBackground(getDrawable(R.drawable.scan_bill_clicked));
//            }
//        });
//
//        scan_cancel_button.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("UseCompatLoadingForDrawables")
//            @Override
//            public void onClick(View v) {
//                scan_cancel_button.setVisibility(View.GONE);
//                scan_button.setBackground(getDrawable(R.drawable.scan_bill_unclicked));
//            }
//        });

        individual_split_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.usersList.clear();
                MainActivity.dishList.clear();
                people = editPeopleText.getText().toString();
                cost = editCostText.getText().toString();
                update_Internal();
                open_individual_split_screen();

            }
        });

        even_split_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.usersList.clear();
                MainActivity.dishList.clear();
                people = editPeopleText.getText().toString();
                cost = editCostText.getText().toString();
                update_Internal();
                open_even_split_screen();

            }
        });

        editPeopleText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    MainActivity.nOfUsers = Integer.parseInt(s.toString());
                    editPeopleTextFilled = true;
                } else {
                    MainActivity.nOfUsers = 1;
                    editPeopleTextFilled = false;
                }

                checkInputField();
            }
        });

        editCostText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    totalBillCost = Double.parseDouble(s.toString());
                    editCostTextFilled = true;
                } else {
                    totalBillCost = 0.0;
                    editCostTextFilled = false;
                }

                checkInputField();
            }
        });
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
        if (individual_split_button.isActivated()) {
            Intent open_individual_split_screen = new Intent(this, IndividualBillScreen.class);
            startActivity(open_individual_split_screen);
        }
    }

    private void open_even_split_screen() {
        if (even_split_button.isActivated()) {
            Intent open_even_split_screen = new Intent(this, EvenBillScreen.class);
            startActivity(open_even_split_screen);
        }
    }

    private void checkEditCostTextValid(Editable s) {
        int count = 0;
        String s1 = s.toString();
        System.out.println(s1.substring(s1.indexOf(".") + 1));

        if (s1.contains(".")) {
            count = s1.substring(s1.indexOf(".") + 1).length();
        }
        if (count > 2) {

            editCostTextValid = false;
        } else {
            editCostTextValid = true;
        }
    }

    private void checkInputField() {
        if (!editCostTextValid) {
            editCostText.setText("");
            Toast.makeText(WelcomeScreen.this, "Please Enter A Valid Number", Toast.LENGTH_LONG).show();
        }

        if (editPeopleTextFilled && editCostTextFilled && editCostTextValid && locationSpinFilled) {
            individual_split_button.setBackgroundResource(R.drawable.rounded_rectangle3);
            even_split_button.setBackgroundResource(R.drawable.rounded_rectangle3);
            individual_split_button.setActivated(true);
            even_split_button.setActivated(true);
        } else {
            individual_split_button.setBackgroundResource(R.drawable.rounded_rectangle2);
            even_split_button.setBackgroundResource(R.drawable.rounded_rectangle2);
            individual_split_button.setActivated(false);
            even_split_button.setActivated(false);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        try {
            JSONObject tax_details = new JSONObject(readJson("res/tax_details.json"));
            province = parent.getItemAtPosition(position).toString();
            tax = tax_details.getJSONObject(province);
            tax_text.setText(tax.getInt("PST") + "% PST  " + tax.getInt("GST") + "% GST  " + tax.getInt("HST") + "% HST");

            locationSpinFilled = !province.equals("Choose Category");

            checkInputField();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void update_Internal() {
        try {
            InternalFiles.setSomething("people", people);
            InternalFiles.setSomething("cost", cost);
            InternalFiles.setSomething("location", province);
            InternalFiles.setSomething("tax", tax);
            System.out.println("hello");
            InternalFiles.saveData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}