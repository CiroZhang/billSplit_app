package com.example.billsplit_app.Screens;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.billsplit_app.Adapters.FinalAdapter;
import com.example.billsplit_app.Adapters.ProfileAdapter;
import com.example.billsplit_app.Adapters.TipAdapter;
import com.example.billsplit_app.MainActivity;
import com.example.billsplit_app.R;
import com.example.billsplit_app.User;

public class EvenFinalScreen extends AppCompatActivity {

    FinalAdapter FinalViewAdapter;
    RecyclerView FinalRecyclerView;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_bill_screen);
        for (User i: MainActivity.usersList){
            i.setEvenTotal();
        }

        ImageButton backButton = findViewById(R.id.back_button);
        Button submitButton = findViewById(R.id.submit_button);



        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_welcome_screen();
            }
        });


//        submitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                open_welcome_screen();
//
//
//            }
//        });

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
