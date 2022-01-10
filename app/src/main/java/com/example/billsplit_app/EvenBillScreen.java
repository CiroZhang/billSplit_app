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

import java.util.ArrayList;
import java.util.Arrays;

public class EvenBillScreen extends AppCompatActivity {

    ProfileAdapter ProfileViewAdapter;
    TipAdapter TipViewAdapter;
    RecyclerView ProfileRecyclerView;
    RecyclerView EvenTipRecyclerView;
    Boolean popupShown = false;

    ArrayList<Integer> color_list = new ArrayList<>(Arrays.asList(-16731781,-2706168,-15503959,-7533027));
    int empty_count = 1;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.even_bill_screen);

        ImageButton backButton = findViewById(R.id.back_button);
        ImageButton addUserButton = findViewById(R.id.add_user_button);

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
    }

    void setupRecyclerView() {
        ProfileViewAdapter = new ProfileAdapter(this,MainActivity.usersList);
        TipViewAdapter = new TipAdapter(this,MainActivity.usersList);

        ProfileRecyclerView = findViewById(R.id.profile_list_view);
        ProfileRecyclerView.setAdapter(ProfileViewAdapter);
        ProfileRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        EvenTipRecyclerView = findViewById(R.id.even_tip_profile_list);
        EvenTipRecyclerView.setAdapter(ProfileViewAdapter);
//        EvenTipRecyclerView.setAdapter(TipViewAdapter);
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
                int color = get_color();
                String name = addProfileNameEditText.getText().toString();
                if (name.isEmpty()){
                    name = "Person" + empty_count;
                    empty_count++;
                }

                MainActivity.usersList.add(new User(name,color));
                ProfileViewAdapter.notifyDataSetChanged();
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
    public int get_color(){
        int current = color_list.get(0);
        color_list.remove(0);
        color_list.add(current);
        return current;
    }

}
