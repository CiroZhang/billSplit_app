package com.example.billsplit_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class IndividualBillScreen extends AppCompatActivity {

    ProfileAdapter ProfileViewAdapter;
    RecyclerView ProfileRecyclerView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_bill_screen);

        ImageButton addUserButton = findViewById(R.id.add_user_button);

        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopup(v);
            }
        });

        System.out.println("before setting up");
        setupRecyclerView();
        System.out.println("after setting up");
    }

    void setupRecyclerView() {
        ProfileRecyclerView = findViewById(R.id.profile_list_view);
        ProfileViewAdapter = new ProfileAdapter(this,MainActivity.usersList);
        ProfileRecyclerView.setAdapter(ProfileViewAdapter);
        ProfileRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
    }

    public void ShowPopup(View view) {
        setContentView(R.layout.add_profile_popup);

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.add_profile_popup, null,false);

        //set up stuff

        // create the popup window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);


        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });


        EditText ProfileEditText = popupView.findViewById(R.id.profile_edit_text);
        Button SubmitEditText = popupView.findViewById(R.id.profile_submit_button);

        SubmitEditText.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View popupView) {
                String text = ProfileEditText.getText().toString();
                User newUser = new User(text);
                System.out.println(text);
                MainActivity.usersList.add(newUser);
                ProfileViewAdapter.notifyDataSetChanged();
            }
        });


    }

    // for opening screens later on
//    private void open_org_screen() {
//        Intent open_org_screen = new Intent(this, OrganizationScreen.class);
//        startActivity(open_org_screen);
//    }
}