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

    // various constructors for the saleItem
    public SaleItem() {
        item = "item";
        x = "33.75";
        y = "-84.39";
        price = 0.0;
    }

    public SaleItem(String item, double price) {
        this.item = item;
        this.price = price;
        this.x = "33.75";
        this.y = "-84.39";
    }

    public SaleItem(String item, double price, String x, String y) {
        this.item = item;
        this.price = price;
        this.x = x;
        this.y = y;
    }

    // getters and setters
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
        return x + "," + y;
    }

    // overridden .equals used for the getSaleItem(item, price) method
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