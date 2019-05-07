package com.example.androidsocialnetwork.Model;

import java.util.List;

public class Gender {
    private Integer id;
    private String type;
    private List<User> users = null;

    public Gender() {}

    public Gender(Integer id, String type, List<User> users) {
        this.id = id;
        this.type = type;
        this.users = users;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
