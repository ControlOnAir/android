package com.esgi.guitton.candice.controlonair.models;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by candiceguitton on 07/04/2018.
 */

public class Contact implements Serializable, Comparable<Contact> {
    private String id;
    private String name;
    private String number;


    public Contact(String id, String name, String number) {
        this.id = id;
        this.name = name;
        this.number = number;
    }

    public Contact() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                '}';
    }


    @Override
    public int compareTo(@NonNull Contact o) {
        return this.getName().compareTo(o.getName());
    }
}
