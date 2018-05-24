package com.esgi.guitton.candice.controlonair.models;

import java.io.Serializable;

/**
 * Created by candiceguitton on 07/04/2018.
 */

public class Message implements Serializable{
    private int id;
    private Contact author;
    private String body;
    private boolean sent;
    private long timestamp;

    public Message(int id, Contact author, String body, boolean sent, long timestamp) {
        this.id = id;
        this.author = author;
        this.body = body;
        this.sent = sent;
        this.timestamp = timestamp;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Contact getAuthor() {
        return author;
    }

    public void setAuthor(Contact author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", author=" + author +
                ", body='" + body + '\'' +
                ", sent=" + sent +
                ", timestamp=" + timestamp +
                '}';
    }
}


