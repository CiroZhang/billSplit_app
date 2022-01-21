package com.example.billsplit_app;

import org.json.JSONException;

import java.util.ArrayList;

public class User {
    private String name;
    private int color;
    private boolean lock_tips = false;
    private InternalFiles data;
    private int tipsPercentage = 0;
    private double default_total;
    private double dishesRawPriceTotal = 0.0;
    private double tax_total = 0.0;
    private double tip_total = 0.0;
    private double tips_times_total = 0.0;
    private double total = 0.0;
    private int sharedNum = 1;
    private ArrayList<Dish> sharedDishes = new ArrayList<>();

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

    public void setLock_tips(boolean lock){
        this.lock_tips = lock;
    }

    public int getColor() {
        return color;
    }

    public int getTipsPercentage(){return tipsPercentage;}

    public void setTipsPercentage(int tips){ this.tipsPercentage = tips; }

    public void setColor(int color) {
        this.color = color;
    }

    public void setIndividualTotal() {
        double user = (double)  MainActivity.get_user_count() -1;
        double tip = (double) tipsPercentage /100;
    }

    public void setEvenTips()
    {
        double tip = (double) tipsPercentage /100;
        this.tip_total = this.default_total * tip;
    }

    public void setTax_total(double n) {
        this.tax_total = n;
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
        double tip = (double) tipsPercentage /100;
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

    public ArrayList<Dish> getSharedDishes() {
        return sharedDishes;
    }

    public void addDish(Dish dish) {
        this.sharedDishes.add(dish);
    }

    public void removeDish(Dish dish) {
        this.sharedDishes.remove(dish);
    }

    public double getTips_times_total() {
        return tips_times_total;
    }

    public void setTips_times_total(double tips_times_total) {
        this.tips_times_total = tips_times_total;
    }

    public void setSharedNum(int sharedNum) {
        this.sharedNum = sharedNum;
    }

    public void setDishesRawPriceTotal(Double n) {
        this.dishesRawPriceTotal = n;
    }

    public void addToDishesRawPrice(double n) {
        this.dishesRawPriceTotal += n;
    }

    public void refreshTotal() {
        this.tips_times_total = ((this.tipsPercentage * this.dishesRawPriceTotal)/100.0) / this.sharedNum;
        this.total = (this.tax_total + this.tips_times_total + (this.dishesRawPriceTotal/(double)this.sharedNum));
    }

    public void refreshTotalEven() throws JSONException {
        this.tips_times_total = (this.tipsPercentage * InternalFiles.getSavedCost())/100.0 / MainActivity.nOfUsers;
        this.total = (this.tax_total + this.tips_times_total + InternalFiles.getSavedCost()/MainActivity.nOfUsers);
    }

    public double getDishesRawPriceTotal() {
        return this.dishesRawPriceTotal;
    }
}
