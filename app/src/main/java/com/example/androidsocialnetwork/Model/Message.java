package com.example.androidsocialnetwork.Model;

public class Message {

    private Chatroom chatroom;
    private String createdDate;
    private Integer id;
    private Location location;
    private String message;
    private String picture;
    private String pictureContentType;
    private Profile sender;
    private String url;

    public Message() {}

    public Message(Chatroom chatroom, String createdDate, Integer id, Location location, String message, String picture, String pictureContentType, Profile sender, String url) {
        this.chatroom = chatroom;
        this.createdDate = createdDate;
        this.id = id;
        this.location = location;
        this.message = message;
        this.picture = picture;
        this.pictureContentType = pictureContentType;
        this.sender = sender;
        this.url = url;
    }

    public Chatroom getChatroom() {
        return chatroom;
    }

    public void setChatroom(Chatroom chatroom) {
        this.chatroom = chatroom;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPictureContentType() {
        return pictureContentType;
    }

    public void setPictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
    }

    public Profile getSender() {
        return sender;
    }

    public void setSender(Profile sender) {
        this.sender = sender;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
