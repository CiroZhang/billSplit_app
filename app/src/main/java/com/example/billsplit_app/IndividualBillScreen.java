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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class IndividualBillScreen extends AppCompatActivity {

    ProfileAdapter ProfileViewAdapter;
    RecyclerView ProfileRecyclerView;
    ItemAdapter ItemViewAdapter;
    RecyclerView ItemRecyclerView;
    Boolean popupShown = false;
    int empty_count = 1;

    ArrayList<Integer> color_list = new ArrayList<>(Arrays.asList(-16731781,-2706168,-15503959,-7533027));

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_bill_screen);

        ImageButton backButton = findViewById(R.id.back_button);
        ImageButton addUserButton = findViewById(R.id.add_user_button);
        ImageButton addDishButton = findViewById(R.id.add_dish_button);
        TextView addDishButton2 = findViewById(R.id.add_dish_button2);
        Button submitBillButton = findViewById(R.id.submit_bill_button);


        System.out.println(MainActivity.usersList);
        System.out.println(MainActivity.dishList);

        if (MainActivity.usersList.isEmpty()) {
            MainActivity.usersList.add(new User("Me"));
        }

        if (MainActivity.dishList.isEmpty()) {
            MainActivity.dishList.add(new Dish("New Dish " + (MainActivity.dishList.size()+1),""));
        }


        System.out.println("added user and dish");
        System.out.println(MainActivity.usersList);
        System.out.println(MainActivity.dishList);

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

        addDishButton.setOnClickListener(v -> {
            MainActivity.dishList.add(new Dish("New Dish " + (MainActivity.dishList.size()+1), ""));
            ItemViewAdapter.notifyDataSetChanged();
            System.out.println(MainActivity.dishList.size());
        });
        addDishButton2.setOnClickListener(v -> {
            MainActivity.dishList.add(new Dish("New Dish " + (MainActivity.dishList.size()+1),""));
            ItemViewAdapter.notifyDataSetChanged();
            System.out.println(MainActivity.dishList.size());
        });

        submitBillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_individual_tip_screen();
            }
        });

        setupRecyclerView();
    }

    void setupRecyclerView() {
        ProfileRecyclerView = findViewById(R.id.profile_list_view);
        ProfileViewAdapter = new ProfileAdapter(this,MainActivity.usersList);
        ProfileRecyclerView.setAdapter(ProfileViewAdapter);
        ProfileRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        ItemRecyclerView = findViewById(R.id.dish_list_view);
        ItemViewAdapter = new ItemAdapter();
        ItemRecyclerView.setAdapter(ItemViewAdapter);
        ItemRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
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
                MainActivity.usersList.add(new User(addProfileNameEditText.getText().toString()));
                ProfileViewAdapter.notifyDataSetChanged();
                ItemViewAdapter.UpdateSharedAdapter();
                popupWindow.dismiss();
                popupShown = false;
                CheckPopup();
                ItemViewAdapter.SharedAdapter.notifyDataSetChanged();
            }
        });
    }

    private void open_welcome_screen() {
        Intent open_welcome_screen = new Intent(this, WelcomeScreen.class);
        startActivity(open_welcome_screen);
    }

    private void open_individual_tip_screen() {
        Intent open_individual_tip_screen = new Intent(this, IndividualBillTip.class);
        startActivity(open_individual_tip_screen);
    }

    public void CheckPopup() {
    }

}