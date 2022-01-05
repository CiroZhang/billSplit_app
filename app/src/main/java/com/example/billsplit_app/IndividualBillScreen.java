package com.example.billsplit_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class IndividualBillScreen extends AppCompatActivity {

    ProfileAdapter ProfileViewAdapter;
    RecyclerView ProfileRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_bill_screen);

        ImageButton addUserButton = findViewById(R.id.add_user_button);

        addUserButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                User newUser = new User("Bob");
                MainActivity.usersList.add(newUser);
                System.out.println(newUser.getUsername());
                ProfileViewAdapter.notifyDataSetChanged();
                openAlertScreen(IndividualBillScreen.this);
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

    private void openAlertScreen(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Cannot Add To Cart!")
                .setMessage("Please select a dish size before adding to cart!")
                .setPositiveButton("ok", null)
//                .setIcon(R.drawable.close_icon)
                .show();
    }

    // for opening screens later on
//    private void open_org_screen() {
//        Intent open_org_screen = new Intent(this, OrganizationScreen.class);
//        startActivity(open_org_screen);
//    }
}