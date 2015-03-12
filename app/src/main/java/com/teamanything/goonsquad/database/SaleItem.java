package com.teamanything.goonsquad.database;

/**
 * Created by Adam on 3/11/2015.
 */
public class SaleItem {

    private String item;
    private String location; //TODO Change to actual location
    private double price;


    public SaleItem() {
        item = "item";
        location = "loc";
        price = 0.0;
    }

    public SaleItem(String item, double price, String location) {
        this.item = item;
        this.location = location;
        this.price = price;
    }

    public String getItem() {
        return item;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

}