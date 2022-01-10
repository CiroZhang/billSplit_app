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
    public static ArrayList<Dish> dishList = new ArrayList<>();
    public static int numOfUsers = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}