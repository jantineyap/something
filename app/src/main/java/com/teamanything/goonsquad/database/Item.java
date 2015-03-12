package com.teamanything.goonsquad.database;

/**
 * Created by Jantine on 3/5/2015.
 */
public class Item {

    private String item;
    private double price;

    public Item() {

    }

    public Item(String item, double price) {
        this.item = item;
        this.price = price;
    }

    public String getItem() {
        return this.item;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }


}
