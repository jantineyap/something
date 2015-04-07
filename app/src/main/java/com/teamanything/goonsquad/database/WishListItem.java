package com.teamanything.goonsquad.database;

/**
 * Created by Adam on 3/11/2015.
 *
 * A Wish List item which has a name and max price
 */
public class WishListItem {

    private final String item;
    private double maxPrice;
    private boolean isMatched;

// --Commented out by Inspection START (4/3/15, 1:40 PM):
//    public WishListItem() {
//        item = "";
//        maxPrice = 0.0;
//        isMatched = false;
//    }
// --Commented out by Inspection STOP (4/3/15, 1:40 PM)

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

// --Commented out by Inspection START (4/3/15, 1:40 PM):
//    public void setMaxPrice(double maxPrice) {
//        this.maxPrice = maxPrice;
//    }
// --Commented out by Inspection STOP (4/3/15, 1:40 PM)

    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched() {
        isMatched = true;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof WishListItem)) { return false; }
        if (this == o) { return true; }
        WishListItem that = (WishListItem) o;
        return this.item.equals(that.item) && this.maxPrice == that.maxPrice;
    }

}