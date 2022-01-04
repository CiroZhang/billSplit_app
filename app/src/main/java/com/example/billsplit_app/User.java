package com.example.billsplit_app;

public class User {
    private String name;

    public User(String name){
        this.name = name;
    }

    public String getUsername(){
        return name;
    }

    public void setUsername(String name){
        this.name = name;
    }
}
