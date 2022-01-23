package com.example.billsplit_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {
    public static ArrayList<User> usersList = new ArrayList<>();
    public static ArrayList<Dish> dishList = new ArrayList<>();
    public static ArrayList<Integer> colorList;
    public static int nOfUsers = 1;
    public static boolean allTipsSelected = false;

    public static double finalTaxTotal = 0.0;
    public static double finalTipTotal = 0.0;
    private static boolean even = false;
    public static boolean tipsChanged = false;
    public static boolean pricesChanged = false;

    public static ArrayList<Integer> generatedUsersTotal = new ArrayList<>();

    public MainActivity() throws JSONException {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public static void setColorList(){
        colorList = new ArrayList<>(Arrays.asList(-16731781,-2706168,-15503959,-7533027));
        System.out.println(colorList);
    }


    public static int get_color(){
        int current = colorList.get(0);
        colorList.remove(0);
        colorList.add(current);
        return current;
    }
    public static double get_default_total(){
        double sum = 0;
        for (User i: usersList) {
            sum = sum + i.getDefault_total();
        }
        return sum;
    }


    public static double get_even_tax_total(){
        double sum = 0;
        for (User i: usersList) {
            sum = sum + i.getTax_total();
        }
        return sum;
    }

    public static double get_even_tip_total(){
        double sum = 0;
        for (User i: usersList) {
            sum = sum + i.getTip_total();
        }
        return sum;
    }

    public static int get_user_count(){
        return usersList.size()+1;
    }

    public static double get_user_sum(){
        double sum = 0;
        for (User i: usersList) {
            sum = sum + i.getTotal();
        }
        return sum;
    }

    public static void is_Even(){
        even = true;
    }
    public static void is_ind(){
        even = false;
    }
    public static boolean check(){
        return even;
    }

    public static double acoholTaxCalculator(String location, double cost){
        if (location.equals("alberta")){return 4.51;}
        if (location.equals("british columbia")){return cost * 0.1;}
        if (location.equals("manitoba")){return 0;}
        if (location.equals("new brunswick")){return cost* 0.05;}
        if (location.equals("newfoundland and labrador")){return 0;}
        if (location.equals("northwest territories")){return cost * 0.7;}
        if (location.equals("nova scotia")){return 0;}
        if (location.equals("nunavut")){return 0;}
        if (location.equals("ontario")){return cost * 0.061;}
        if (location.equals("prince edward island")){return cost * 0.25;}
        if (location.equals("quebec")){return 0.72;}
        if (location.equals("saskatchewan")){return cost * 0.1;}
        if (location.equals("yukon")){return cost * 0.12;}
        System.out.println("no province selected");
        return 0;

    }
}