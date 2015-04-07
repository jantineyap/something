package com.teamanything.goonsquad;

import android.test.AndroidTestCase;

import com.teamanything.goonsquad.database.SaleItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jantine on 4/3/2015.
 *
 * Tests functionality of SaleItem and SaleItemAdapter
 */
public class SaleItemAdapterTest extends AndroidTestCase {

    private SaleItemAdapter mAdapter;
    private List<SaleItem> mData;
    private final int mCount = 5;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mData = new ArrayList<>();
        for (int i = 0; i < mCount; i++) {
            String sI = Integer.toString(i);
            mData.add(new SaleItem(sI, i, sI, sI));
        }

        mAdapter = new SaleItemAdapter(getContext(), mData);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        mData.clear();
    }

    /**
     * Tests if SaleItemAdapter stores correct count
     */
    public void testCount() {
        assertEquals(mCount, mAdapter.getCount());
    }

    /**
     * Tests if SaleItem are stored in SaleItemAdapter with correct order
     */
    public void testGetItem() {
        for (int i = 0; i < mAdapter.getCount(); i++) {
            assertEquals(new SaleItem(Integer.toString(i), i), mAdapter.getItem(i));
        }
    }

    /**
     * Tests if WishListItem have correct price
     */
    public void testPrice() {
        for (int i = 0; i < mAdapter.getCount(); i++) {
            assertEquals((double) i, mAdapter.getItem(i).getPrice());
        }
    }

    /**
     * Tests if SaleItem have correct x coordinate
     */
    public void testX() {
        for (int i = 0; i < mAdapter.getCount(); i++) {
            assertEquals(Integer.toString(i), mAdapter.getItem(i).getX());
        }
    }

    /**
     * Tests if SaleItem have correct y coordinate
     */
    public void testY() {
        for (int i = 0; i < mAdapter.getCount(); i++) {
            assertEquals(Integer.toString(i), mAdapter.getItem(i).getY());
        }
    }

}