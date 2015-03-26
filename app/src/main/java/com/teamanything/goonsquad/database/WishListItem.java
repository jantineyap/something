package com.teamanything.goonsquad.database;

/**
 * Created by Adam on 3/11/2015.
 *
 * A Wish List item which has a name and max price
 */
public class WishListItem {

    private String item;
    private double maxPrice;
    private boolean isMatched;

    public WishListItem() {
        item = "";
        maxPrice = 0.0;
        isMatched = false;
    }

    public WishListItem(String item, double maxPrice) {
        this.item = item;
        this.maxPrice = maxPrice;
        isMatched = false;
    }

    public String getItem() {
        return item;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
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
        return this.item.equals(that.item) && this.maxPrice == that.maxPrice;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}