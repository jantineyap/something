package com.teamanything.goonsquad;

import android.test.AndroidTestCase;

import com.teamanything.goonsquad.database.WishListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 4/2/2015.
 *
 * JUnit Test class for WishListItemAdapter
 */
public class WishListItemAdapterTest extends AndroidTestCase {

    WishListItemAdapter adapter;
    List<WishListItem> data;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        data = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            data.add(new WishListItem(Integer.toString(i), i));
        }
        adapter = new WishListItemAdapter(getContext(), data);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        data.clear();
    }

    /**
     * Tests for proper count
     */
    public void testCount() {
        assertEquals(5, adapter.getCount());
    }

    /**
     * Tests if WishListItem are stored in correct order with correct price
     */
    public void testMaxPrice() {
        for (int i = 0; i < adapter.getCount(); i++) {
            assertEquals((double) (i + 1), adapter.getItem(i).getMaxPrice());
        }
    }

}
