package com.example.billsplit_app;

import java.util.ArrayList;

public class User {
    private String name;
    private int color;
    private ArrayList<Dish> dishes = new ArrayList<>();

    public User(String name, int color){
        this.name = name;
        this.color = color;
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

    public void addDish(Dish dish) {
        this.dishes.add(dish);
    }

    public void removeDish(Dish dish) {
        this.dishes.remove(dish);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
