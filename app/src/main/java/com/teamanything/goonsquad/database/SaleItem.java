package com.teamanything.goonsquad.database;

/**
 * Created by Adam on 3/11/2015.
 *
 * Represents a SaleItem with a name, price, location
 */
public class SaleItem {

    private String item;
    private String x;
    private String y;
    private double price;


    public SaleItem() {
        item = "item";
        x = "0";
        y = "0";
        price = 0.0;
    }

    public SaleItem(String item, double price) {
        this.item = item;
        this.price = price;
    }

    public SaleItem(String item, double price, String x, String y) {
        this.item = item;
        this.price = price;
        this.x = x;
        this.y = y;
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

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    public String getLocation () {
        String location = x + ", " + y;
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SaleItem)) { return false; }
        if (this == o) { return true; }
        SaleItem that = (SaleItem) o;
        return this.item.equals(that.item) && this.price == that.price;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}