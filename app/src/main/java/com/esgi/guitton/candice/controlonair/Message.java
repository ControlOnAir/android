package com.esgi.guitton.candice.controlonair;

/**
 * Created by candiceguitton on 07/04/2018.
 */

public class Message {
    private Contact author;
    private String body;

    public Message(Contact author, String body) {
        this.author = author;
        this.body = body;
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
                "author=" + author +
                ", body='" + body + '\'' +
                '}';
    }
}


