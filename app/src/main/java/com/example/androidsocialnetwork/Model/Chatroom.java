package com.example.androidsocialnetwork.Model;

import java.util.List;

public class Chatroom {

    private Profile admin;
    private String createdDate;
    private Integer id;
    private List<Message> messages = null;
    private List<Profile> participants = null;
    private String topic;

    public Chatroom() {}

    public Chatroom(Profile admin, String createdDate, Integer id, List<Message> messages, List<Profile> participants, String topic) {

        this.admin = admin;
        this.createdDate = createdDate;
        this.id = id;
        this.messages = messages;
        this.participants = participants;
        this.topic = topic;
    }

    public Profile getAdmin() {
        return admin;
    }

    public void setAdmin(Profile admin) {
        this.admin = admin;
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

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<Profile> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Profile> participants) {
        this.participants = participants;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}


