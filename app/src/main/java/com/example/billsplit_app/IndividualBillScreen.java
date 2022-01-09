package com.example.billsplit_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class IndividualBillScreen extends AppCompatActivity {

    ProfileAdapter ProfileViewAdapter;
    RecyclerView ProfileRecyclerView;
    ItemAdapter ItemViewAdapter;
    RecyclerView ItemRecyclerView;
    Boolean popupShown = false;

    ArrayList<Integer> color_list = new ArrayList<>(Arrays.asList(-16731781,-2706168,-15503959,-7533027));
    Drawable user_circle;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_bill_screen);

//        MainActivity.dishList.add(new Dish("Dish 1","00.00"));

        ImageButton addUserButton = findViewById(R.id.add_user_button);
        TextView addDishButton = findViewById(R.id.add_dish_button);
        user_circle = getDrawable(R.drawable.circle1);
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopup(v);
                CheckPopup();
            }
        });
        addDishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.dishList.add(new Dish("rice","00.00"));
                ItemViewAdapter.notifyDataSetChanged();
                System.out.println(MainActivity.dishList.size());
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
        ItemViewAdapter = new ItemAdapter(MainActivity.dishList);
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
                int color = get_color();

                MainActivity.usersList.add(new User(addProfileNameEditText.getText().toString(),color));
                ProfileViewAdapter.notifyDataSetChanged();
                popupWindow.dismiss();
                popupShown = false;
                CheckPopup();
            }
        });
    }

    public void CheckPopup() {

    }
    public int get_color(){
        int current = color_list.get(0);
        color_list.remove(0);
        color_list.add(current);
        return current;
    }

    // for opening screens later on
//    private void open_org_screen() {
//        Intent open_org_screen = new Intent(this, OrganizationScreen.class);
//        startActivity(open_org_screen);
//    }
}