package com.example.androidsocialnetwork.Model;

import java.io.Serializable;

public class UserLogin implements Serializable {
    private String password;
    private boolean rememberMe;
    private String username;

    public UserLogin(String username, boolean rememberMe, String password) {
        this.password = password;
        this.rememberMe = rememberMe;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
