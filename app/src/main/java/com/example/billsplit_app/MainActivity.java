package com.example.billsplit_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {
    public static ArrayList<User> usersList = new ArrayList<>();
    public static ArrayList<Dish> dishList = new ArrayList<>();
    public static ArrayList<Integer> colorList = new ArrayList<>(Arrays.asList(-16731781,-2706168,-15503959,-7533027));
    public static int nOfUsers = 1;

    public MainActivity() throws JSONException {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public static int get_color(){
        int current = colorList.get(0);
        colorList.remove(0);
        colorList.add(current);
        return current;
    }

    public static int get_user_count(){
//        return (int) usersList.stream().count();
        return usersList.size()+1;
    }

    public static double get_user_sum(){
        double sum = 0;
        for (User i: usersList) {
            sum = sum + i.getTotal();
        }
        return sum;
    }


}