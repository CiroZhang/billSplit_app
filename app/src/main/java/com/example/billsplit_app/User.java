package com.example.billsplit_app;

import org.json.JSONException;

import java.util.ArrayList;

public class User {
    private String name;
    private int color;
    private int tips;
    private boolean lock_tips = false;
    private InternalFiles data;
    private double default_total;
    private double tax_total;
    private double tip_total;
    private double total;


    public User(String name) {
        if (name.isEmpty()){
            name = "Person " + MainActivity.usersList.size();

        }
        this.name = name;
        this.color = MainActivity.get_color();

        try {
            data = new InternalFiles();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getUsername(){
        return name;
    }

    public void setUsername(String name){
        this.name = name;
    }

    public void setTips(int tips){ this.tips = tips; }

    public void setLock_tips(boolean lock){
        this.lock_tips = lock;
    }

    public int getColor() {
        return color;
    }

    public int getTips(){return tips;}

    public void setColor(int color) {
        this.color = color;
    }

    public void setIndividualTotal() {
        double user = (double)  MainActivity.get_user_count() -1;
        double tip = (double) tips/100;
    }

    public void setEvenTips(){
        double tip = (double) tips/100;
        this.tip_total = this.default_total * tip;
    }

    public double getTax_total(){
        return tax_total;
    }
    public void setEvenTax() throws JSONException {
        double tax =(double) InternalFiles.getSavedTax();
        this.tax_total = default_total * tax;



    }
    public double getTip_total(){
        return tip_total;
    }
    public void setEvenTotal(){
        double user = (double)  MainActivity.get_user_count() -1;
        double tip = (double) tips/100;
        try {
            double totalCost = (double)  InternalFiles.getSavedCost();
            double tax =(double) InternalFiles.getSavedTax();
            double total = ((totalCost * (1 + tax + tip))/user);
            int cal = (int) (total * 100);
            total = (double) cal/100;
            this.total = total;
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public double getDefault_total(){
        return default_total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    public void set_default_total() throws JSONException {
        default_total = InternalFiles.getSavedCost()/ (MainActivity.get_user_count()-1);
    }

    public double getTotal(){
        return total;
    }

}
