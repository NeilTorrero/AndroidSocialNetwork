package com.example.androidsocialnetwork.Model;

import java.util.List;

public class Ethnicity {

    private String ethnicity;
    private Integer id;
    private List<User> users = null;

    public Ethnicity() {}

    public Ethnicity(String ethnicity, Integer id, List<User> users) {
        this.ethnicity = ethnicity;
        this.id = id;
        this.users = users;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
