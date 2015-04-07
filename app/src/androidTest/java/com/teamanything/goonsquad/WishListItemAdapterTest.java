package com.teamanything.goonsquad;

import android.test.AndroidTestCase;

import com.teamanything.goonsquad.database.WishListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 4/2/2015.
 *
 * Tests functionality of WishListItem and WishListItemAdapter
 */
public class WishListItemAdapterTest extends AndroidTestCase {

    private WishListItemAdapter mAdapter;
    private List<WishListItem> mData;
    private final int mCount = 5;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mData = new ArrayList<>();
        for (int i = 0; i < mCount; i++) {
            mData.add(new WishListItem(Integer.toString(i), i));
        }

        mAdapter = new WishListItemAdapter(getContext(), mData);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        mData.clear();
    }

    /**
     * Tests for proper count
     */
    public void testCount() {
        assertEquals(mCount, mAdapter.getCount());
    }

    /**
     * Tests if WishListItem are stored in WishListItemAdapter with correct order
     */
    public void testGetItem() {
        for (int i = 0; i < mAdapter.getCount(); i++) {
            assertEquals(new WishListItem(Integer.toString(i), i), mAdapter.getItem(i));
        }
    }

    /**
     * Tests if WishListItem have correct price
     */
    public void testMaxPrice() {
        for (int i = 0; i < mAdapter.getCount(); i++) {
            assertEquals((double) i, mAdapter.getItem(i).getMaxPrice());
        }
    }

}
