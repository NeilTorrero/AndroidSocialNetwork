package com.example.androidsocialnetwork.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TokenUser  implements Serializable {
    @SerializedName("id_token")
    private String idToken;

    public TokenUser(String idToken) {
        this.idToken = idToken;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
}
