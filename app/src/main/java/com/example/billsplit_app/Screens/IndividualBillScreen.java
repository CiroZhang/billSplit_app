package com.example.billsplit_app.Screens;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import com.example.billsplit_app.Adapters.ItemAdapter;
import com.example.billsplit_app.Adapters.ProfileAdapter;
import com.example.billsplit_app.Dish;
import com.example.billsplit_app.MainActivity;
import com.example.billsplit_app.R;
import com.example.billsplit_app.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IndividualBillScreen extends AppCompatActivity {

    ProfileAdapter ProfileViewAdapter;
    RecyclerView ProfileRecyclerView;
    static ItemAdapter ItemViewAdapter;
    RecyclerView ItemRecyclerView;
    TextView currentTotal;
    double indivTotalNum = 0.0;
    double alcoholTax = 0.0;
    Activity activity = IndividualBillScreen.this;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.is_ind();
        MainActivity.setColorList();
        setContentView(R.layout.individual_bill_screen);

        ImageButton ocrButton = findViewById(R.id.ocr_button);

        ImageButton backButton = findViewById(R.id.back_button);
        ImageButton addUserButton = findViewById(R.id.add_user_button);
        ImageButton addDishButton = findViewById(R.id.add_dish_button);
        TextView addDishButton2 = findViewById(R.id.add_dish_button2);
        Button submitBillButton = findViewById(R.id.submit_bill_button);
        currentTotal = findViewById(R.id.current_total_price);

        MainActivity.usersList.clear();
        MainActivity.usersList.add(new User("Me"));
        if (MainActivity.nOfUsers > 1) {
            for (int i = 1; i < MainActivity.nOfUsers; i++) {
                MainActivity.usersList.add(new User("Person " + i));
            }
        }
        MainActivity.dishList.clear();
        MainActivity.dishList.add(new Dish("New Dish " + (MainActivity.dishList.size()+1),""));

        ocrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_ocr_screen();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
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
            Dish newDish = new Dish("New Dish " + (MainActivity.dishList.size()+1), "");
            newDish.clearSharedUsers();
            MainActivity.dishList.add(newDish);
            ItemViewAdapter.notifyDataSetChanged();
        });

        addDishButton2.setOnClickListener(v -> {
            Dish newDish = new Dish("New Dish " + (MainActivity.dishList.size()+1), "");
            newDish.clearSharedUsers();
            MainActivity.dishList.add(newDish);
            ItemViewAdapter.notifyDataSetChanged();
        });

        submitBillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.tipsChanged = false;
                if (MainActivity.pricesChanged) {
                    open_individual_tip_screen();
                }
                else {
                    alertPopup(activity);
                }
            }
        });

        setupRecyclerView();
    }

    void setupRecyclerView() {
        ProfileRecyclerView = findViewById(R.id.profile_list_view);
        ProfileViewAdapter = new ProfileAdapter(this);
        ProfileRecyclerView.setAdapter(ProfileViewAdapter);
        ProfileRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        ItemRecyclerView = findViewById(R.id.dish_list_view);
        ItemViewAdapter = new ItemAdapter(this,currentTotal,indivTotalNum);
        ItemRecyclerView.setAdapter(ItemViewAdapter);
        ItemRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    private void alertPopup(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("No prices entered!")
                .setMessage("You haven't entered in any prices yet!")
                .setNegativeButton("OK", null)
                .show();
    }

    public void ShowPopup(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.add_profile_popup, null,false);
        CheckPopup();

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                CheckPopup();
                return true;
            }
        });

        ImageView addProfileBackground = popupView.findViewById(R.id.popup_background);
        ImageButton addProfileCloseButton = popupView.findViewById(R.id.popup_close_button);
        EditText addProfileNameEditText = popupView.findViewById(R.id.new_name);
        Button addProfileNameSubmitButton = popupView.findViewById(R.id.delete);

        addProfileBackground.setOnClickListener(v -> {
            // This is just here to prevent popup from closing when clicking the background
        });

        addProfileCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        addProfileNameSubmitButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                MainActivity.usersList.add(new User(addProfileNameEditText.getText().toString()));
                ProfileViewAdapter.notifyDataSetChanged();
                popupWindow.dismiss();
                CheckPopup();
                ItemViewAdapter.SharedAdapter.notifyDataSetChanged();
            }
        });
    }

    private void open_ocr_screen() {
        Intent open_ocr_screen = new Intent(this, OCR.class);
        startActivity(open_ocr_screen);
    }

    private void open_individual_tip_screen() {
        Intent open_individual_tip_screen = new Intent(this, IndividualTipScreen.class);
        startActivity(open_individual_tip_screen);
    }

    public void CheckPopup() {
    }

    public String readJson(String fileName) {
        try {
            InputStream is = getAssets().open(fileName);
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

            return responseStrBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void refreshAdapters() {
        ProfileViewAdapter.notifyDataSetChanged();
        ItemViewAdapter.notifyDataSetChanged();
        ItemViewAdapter.SharedAdapter.notifyDataSetChanged();
    }

    public void OCRPopup(View view) {
        View popupView = LayoutInflater.from(view.getContext()).inflate(R.layout.ocr_popup, null, false);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });

        ImageView popupBackground = popupView.findViewById(R.id.popup_background);
        ImageView scanBillButton = popupView.findViewById(R.id.scan_bill_button);
        TextView manualExitButton = popupView.findViewById(R.id.manual_exit);

        popupBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This is just here to prevent the popup from closing when clicking the popup
            }
        });

        scanBillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_ocr_screen();
                popupWindow.dismiss();
            }
        });

        manualExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }
}