package com.example.androidsocialnetwork.Model;

public class Chat {
    private String groupName;
    private String lastMessage;

    public Chat (String groupName, String lastMessage) {
        this.groupName = groupName;
        this.lastMessage = lastMessage;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
