package com.example.billsplit_app;

import org.json.JSONException;

import java.util.ArrayList;

public class User {
    private String name;
    private int color;
    private int tips;
    private boolean lock_tips = false;
    private ArrayList<Dish> dishes = new ArrayList<>();
    private InternalFiles data;

    public User(String name) {
        if (name.isEmpty()){
            name = "Person" + MainActivity.get_count();

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

    public ArrayList<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(ArrayList<Dish> dishes) {
        this.dishes = dishes;
    }

    public void setTips(int tips){ this.tips = tips; }

    public void setLock_tips(boolean lock){
        this.lock_tips = lock;
    }

    public void addDish(Dish dish) {
        this.dishes.add(dish);
    }

    public void removeDish(Dish dish) {
        this.dishes.remove(dish);
    }

    public int getColor() {
        return color;
    }

    public int getTips(){return tips;}

    public void setColor(int color) {
        this.color = color;
    }

    public double getEvenTotal(){
        double user = (double)  MainActivity.get_user_count();
        double tip = (double) tips/100;
        try {
            double totalCost = (double)  InternalFiles.getSavedCost();
            double tax =(double) InternalFiles.getSavedTax();
            double total = ((totalCost * (1 + tax + tip))/user);
            int cal = (int) (total * 100);
            total = (double) cal/100;
            return total;
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("error");
        return 0;

    }

}
