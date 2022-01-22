package com.example.billsplit_app;

import org.json.JSONException;

import java.util.ArrayList;

public class Dish {
    private String name;
    private String price = "0.0";
    private String priceWithLiquor = "0.0";
    private boolean collapsed = false;
    private boolean alcoholic = false;
    private ArrayList<User> sharedUsers = new ArrayList<>();

    public Dish (String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    public boolean isAlcoholic() {
        return alcoholic;
    }

    public void setAlcoholic(boolean alcoholic) {
        this.alcoholic = alcoholic;
    }

    public void addUser(User u) {
        if (!this.sharedUsers.contains(u)) {
            this.sharedUsers.add(u);
        }
    }

    public void removeUser(User u) {
        this.sharedUsers.remove(u);
    }

    public int getNOfSharedUsers() {
        return this.sharedUsers.size();
    }

    public void clearSharedUsers() {
        this.sharedUsers.clear();
    }

    public void addLiquorTax() throws JSONException {
        this.priceWithLiquor = String.valueOf(InternalFiles.getLiquorTax(Double.parseDouble(this.price)));
    }

    public void removeLiquorTax() {
        this.priceWithLiquor = this.price;
    }

    public double getLiquorTax() {
        return Double.parseDouble(this.priceWithLiquor);
    }
}
