package com.esgi.guitton.candice.controlonair.models;

import java.io.Serializable;
import java.util.List;

public class Conversation implements Serializable {
    private int id;
    private Contact contact;
    private long timestamp;
    private List<Message> messages;

    public Conversation(int id, Contact contact, long timestamp, List<Message> messages) {
        this.id = id;
        this.contact = contact;
        this.timestamp = timestamp;
        this.messages = messages;
    }

    public Conversation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "id=" + id +
                ", contact=" + contact +
                ", timestamp=" + timestamp +
                ", messages=" + messages +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conversation conversation = (Conversation) o;

        return conversation.id == id && conversation.timestamp == timestamp;
    }

    @Override
    public int hashCode() {

        return id + messages.size();
    }
}
