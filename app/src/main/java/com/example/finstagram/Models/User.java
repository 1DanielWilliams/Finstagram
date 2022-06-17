package com.example.finstagram.Models;


import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("User")
public class User extends ParseObject {
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PROFILE_IMAGE = "profileImage";
    public static final String KEY_PASSWORD = "password";

    public User() {}

    public String getUsername() { return getString(KEY_USERNAME); }

    public void setUsername(String username) { put(KEY_USERNAME, username);}


    public String getPassword() { return getString(KEY_PASSWORD); }

    public void setPassword(String password) { put(KEY_PASSWORD, password); }

}
