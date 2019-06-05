package com.example.androidsocialnetwork.Model;

public class NewMessage {
    private String createdDate;
    private String message;
    private String picture;
    private BlockProfile recipient;
    private String url;

    public NewMessage () {

    }
    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
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

    public BlockProfile getRecipient() {
        return recipient;
    }

    public void setRecipient(BlockProfile recipient) {
        this.recipient = recipient;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
