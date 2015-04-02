package com.teamanything.goonsquad;

import android.test.AndroidTestCase;

import com.teamanything.goonsquad.database.WishListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 4/2/2015.
 *
 * JUnit Test class for Adapter
 */
public class WishListItemAdapterTest extends AndroidTestCase {

    WishListItemAdapter adapter;
    List<WishListItem> data;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        data = new ArrayList<>();
        data.add(new WishListItem("1", 1));
        data.add(new WishListItem("2", 2));
        data.add(new WishListItem("3", 3));

        adapter = new WishListItemAdapter(getContext(), data);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        data.clear();
    }

    public void testCount() {
        assertEquals(3, adapter.getCount());
    }

}
