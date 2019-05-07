package com.example.androidsocialnetwork.Model;

public class Block {

    private String createdDate;
    private Integer id;
    private Profile received;
    private Profile sent;

    public Block() {}

    public Block(String createdDate, Integer id, Profile received, Profile sent) {
        this.createdDate = createdDate;
        this.id = id;
        this.received = received;
        this.sent = sent;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Profile getReceived() {
        return received;
    }

    public void setReceived(Profile received) {
        this.received = received;
    }

    public Profile getSent() {
        return sent;
    }

    public void setSent(Profile sent) {
        this.sent = sent;
    }
}
