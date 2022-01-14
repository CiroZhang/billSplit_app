package com.example.billsplit_app.Screens;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.TextView;

import com.example.billsplit_app.Adapters.ProfileAdapter;
import com.example.billsplit_app.Adapters.TipAdapter;
import com.example.billsplit_app.InternalFiles;
import com.example.billsplit_app.MainActivity;
import com.example.billsplit_app.R;
import com.example.billsplit_app.User;

import org.json.JSONException;

public class EvenBillScreen extends AppCompatActivity {

    ProfileAdapter ProfileViewAdapter;
    TipAdapter TipViewAdapter;
    RecyclerView ProfileRecyclerView;
    RecyclerView EvenTipRecyclerView;
    Boolean popupShown = false;
    Boolean same_tip = false;
    int allTip = 0;

    @SuppressLint({"ResourceType", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.even_bill_screen);

        MainActivity.allTipsSelected = false;
        MainActivity.usersList.add(new User("Me"));
        if (MainActivity.nOfUsers > 1) {
            for (int i = 1; i < MainActivity.nOfUsers; i++) {
                MainActivity.usersList.add(new User("Person " + i));
            }
        }

        ImageButton backButton = findViewById(R.id.back_button);
        ImageButton addUserButton = findViewById(R.id.add_user_button);
        Button submitButton = findViewById(R.id.submit_button);
        CheckBox sameTipButton = findViewById(R.id.same_tip_button);

        View same_tip_selection = findViewById(R.id.same_tip_selection);
        TextView current_total = findViewById(R.id.current_total);
        EditText sameTipEditText = findViewById(R.id.same_tip_edit_text);

        try { current_total.setText("$ " + InternalFiles.getSavedCost());
        } catch (JSONException e) { e.printStackTrace(); }


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

        sameTipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sameTipEditText.getText().clear();
                if (sameTipButton.isChecked()) {
                    same_tip_selection.setVisibility(View.VISIBLE);

                    MainActivity.allTipsSelected = true;
                }
                else{
                    same_tip_selection.setVisibility(View.INVISIBLE);
                    MainActivity.allTipsSelected = false;
                }
                TipViewAdapter.notifyDataSetChanged();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sameTipButton.isChecked()) {
                    for (User user : MainActivity.usersList) {
                        user.setTips(allTip);
                    }
                }
                for (User user : MainActivity.usersList) {
                    System.out.println(user.getTips());
                }

                open_final_screen();
            }
        });

        sameTipEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    allTip = Integer.parseInt(s.toString());
                }
                else {
                    allTip = 0;
                }
            }
        });

        setupRecyclerView();
    }

    void setupRecyclerView() {
        ProfileViewAdapter = new ProfileAdapter(this, MainActivity.usersList);
        TipViewAdapter = new TipAdapter();

        ProfileRecyclerView = findViewById(R.id.profile_list_view);
        ProfileRecyclerView.setAdapter(ProfileViewAdapter);
        ProfileRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        EvenTipRecyclerView = findViewById(R.id.even_tip_list_view);
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

    private void open_final_screen() {
        for (User i: MainActivity.usersList){
            i.setEvenTotal();
        }
        Intent open_even_final_screen = new Intent(this, FinalScreen.class);
        startActivity(open_even_final_screen);
    }

    public void CheckPopup() {
    }
}
