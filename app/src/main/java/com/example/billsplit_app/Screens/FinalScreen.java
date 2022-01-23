package com.example.billsplit_app.Screens;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.billsplit_app.Adapters.FinalAdapter;
import com.example.billsplit_app.Dish;
import com.example.billsplit_app.InternalFiles;
import com.example.billsplit_app.MainActivity;
import com.example.billsplit_app.R;

import org.json.JSONException;

public class FinalScreen extends AppCompatActivity {

    FinalAdapter FinalViewAdapter;
    RecyclerView FinalRecyclerView;
    Activity activity = FinalScreen.this;

    @SuppressLint({"ResourceType", "SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_bill_screen);
        for(Dish i : MainActivity.dishList){
            System.out.println("-----------------");
            System.out.println(i.getName());
            System.out.println(i.stuff2());

            System.out.println(i.stuff3());
        }
        System.out.println("-----------------");
        for (String i : MainActivity.stuff){
            System.out.println(i);
        }

        ImageButton backButton = findViewById(R.id.back_button);
        Button submitButton = findViewById(R.id.return_button);
        TextView total = findViewById(R.id.total);
        TextView subtotal = findViewById(R.id.subtotal);
        TextView tax = findViewById(R.id.taxes_total);
        TextView tip = findViewById(R.id.tips_total);

        // even screen
        if (MainActivity.check()){
            // setting subtotal text
            try {
                subtotal.setText("$ " + String.format("%.2f", InternalFiles.getSavedCost()));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // setting tax text
            try {
                MainActivity.finalTaxTotal = InternalFiles.getSavedCost() * InternalFiles.getSavedTax();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            tax.setText("$ " + String.format("%.2f", MainActivity.finalTaxTotal));

            // setting tip text
            tip.setText("$ " + String.format("%.2f", MainActivity.finalTipTotal));

            // setting final total text
            try {
                total.setText("$ " + String.format("%.2f", (InternalFiles.getSavedCost() + MainActivity.finalTipTotal + MainActivity.finalTaxTotal)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // indiv screen
        else {
            // getting total price of all dishes
            double totalDishesPriceRaw = 0.0;
            for (Dish d : MainActivity.dishList) {
                if (!d.getPrice().isEmpty()) {
                    totalDishesPriceRaw += Double.parseDouble(d.getPrice());
                }
            }

            // setting subtotal text
            subtotal.setText("$ " + String.format("%.2f", totalDishesPriceRaw));

            // setting tax text
            tax.setText("$ " + String.format("%.2f", MainActivity.finalTaxTotal));

            // setting tip text
            tip.setText("$ " + String.format("%.2f", MainActivity.finalTipTotal));

            // setting final total text
            total.setText("$ " + String.format("%.2f", (totalDishesPriceRaw + MainActivity.finalTaxTotal + MainActivity.finalTipTotal)));
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
//                open_welcome_screen();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_welcome_screen();
            }
        });

        setupRecyclerView();
    }

    void setupRecyclerView() {
        FinalViewAdapter = new FinalAdapter(this, MainActivity.usersList);

        FinalRecyclerView = findViewById(R.id.even_final_list_view);
        FinalRecyclerView.setAdapter(FinalViewAdapter);
        FinalRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

    }

    private void open_welcome_screen() {
        Intent open_welcome_screen = new Intent(this, WelcomeScreen.class);
        startActivity(open_welcome_screen);
    }

}
