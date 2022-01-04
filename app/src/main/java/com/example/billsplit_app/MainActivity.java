package com.example.billsplit_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    public static ArrayList<User> usersList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        usersList.add(new User("Me"));

        setContentView(R.layout.individual_bill_screen);
        open_bill_screen();
    }

    private void open_bill_screen() {
        Intent open_form1_screen = new Intent(this, IndividualBillScreen.class);
        startActivity(open_form1_screen);
    }
}