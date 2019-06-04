package com.example.androidsocialnetwork.Model;

public class Block {

    private String createdDate;
    private BlockProfile received;
    private BlockProfile sent;

    public Block() {}

    public Block(String createdDate, Integer id, BlockProfile received, BlockProfile sent) {
        this.createdDate = createdDate;
        this.received = received;
        this.sent = sent;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }


    public BlockProfile getReceived() {
        return received;
    }

    public void setReceived(Profile received) {
        this.received = new BlockProfile(received.getId());
    }

    public BlockProfile getSent() {
        return sent;
    }

    public void setSent(Profile sent) {
        this.sent = new BlockProfile(sent.getId());
    }
}
