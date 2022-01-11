package com.example.billsplit_app;
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

import java.util.ArrayList;
import java.util.Arrays;

public class EvenBillScreen extends AppCompatActivity {

    ProfileAdapter ProfileViewAdapter;
    TipAdapter TipViewAdapter;
    RecyclerView ProfileRecyclerView;
    RecyclerView EvenTipRecyclerView;
    Boolean popupShown = false;
    Boolean same_tip = false;


    ArrayList<Integer> color_list = new ArrayList<>(Arrays.asList(-16731781,-2706168,-15503959,-7533027));
    int empty_count = 1;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.even_bill_screen);

        ImageButton backButton = findViewById(R.id.back_button);
        ImageButton addUserButton = findViewById(R.id.add_user_button);

        CheckBox sameTipButton = findViewById(R.id.same_tip_button);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_welcome_screen();
            }
        });

        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopup(v);
                CheckPopup();
            }
        });
        setupRecyclerView();

        sameTipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                same_tip = sameTipButton.isChecked();

            }
        });
    }

    void setupRecyclerView() {
        ProfileViewAdapter = new ProfileAdapter(this,MainActivity.usersList);
        TipViewAdapter = new TipAdapter();

        ProfileRecyclerView = findViewById(R.id.profile_list_view);
        ProfileRecyclerView.setAdapter(ProfileViewAdapter);
        ProfileRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        EvenTipRecyclerView = findViewById(R.id.even_tip_profile_list);
        EvenTipRecyclerView.setAdapter(TipViewAdapter);
        EvenTipRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    public void ShowPopup(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.add_profile_popup, null,false);
        popupShown = true;
        CheckPopup();

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                popupShown = false;
                CheckPopup();
                return true;
            }
        });

        EditText addProfileNameEditText = popupView.findViewById(R.id.add_profile_name_edit_text);
        Button addProfileNameSubmitButton = popupView.findViewById(R.id.add_profile_name_submit_button);

        addProfileNameSubmitButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                MainActivity.usersList.add(new User( addProfileNameEditText.getText().toString()));
                ProfileViewAdapter.notifyDataSetChanged();
                TipViewAdapter.notifyDataSetChanged();
                popupWindow.dismiss();
                popupShown = false;
                CheckPopup();
            }
        });
    }

    private void open_welcome_screen() {
        Intent open_welcome_screen = new Intent(this, WelcomeScreen.class);
        startActivity(open_welcome_screen);
    }

    public void CheckPopup() {
    }
}
