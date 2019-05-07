package com.example.androidsocialnetwork.Model;

import java.util.List;

public class Relationship {

    private Integer id;
    private String status;
    private List<User> users = null;

    public Relationship() {}

    public Relationship(Integer id, String status, List<User> users) {
        this.id = id;
        this.status = status;
        this.users = users;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
