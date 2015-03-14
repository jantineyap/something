package com.teamanything.goonsquad.database;

/**
 * Created by Adam on 3/11/2015.
 */
public class WishListItem {

    private String item;
    private double price;
    private boolean isMatched;

    public WishListItem() {
        item = "";
        price = 0.0;
        isMatched = false;
    }

    public WishListItem(String item, double price) {
        this.item = item;
        this.price = price;
        isMatched = false;
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

    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof WishListItem)) { return false; }
        if (this == o) { return true; }
        WishListItem that = (WishListItem) o;
        return this.item.equals(that.item) && this.price == that.price;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}