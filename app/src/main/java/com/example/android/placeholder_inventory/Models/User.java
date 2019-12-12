package com.example.android.placeholder_inventory.Models;

public class User {
    //public String username;
    public String userId;
    public Boolean isAnon;
    public User() {}
    public User(String userId, Boolean isAnon) {

        this.userId = userId;
        this.isAnon = isAnon;
    }
}
