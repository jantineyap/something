package com.teamanything.goonsquad;

import android.test.AndroidTestCase;

import com.teamanything.goonsquad.database.SaleItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jantine on 4/3/2015.
 */
public class SaleItemAdapterTest extends AndroidTestCase {

    SaleItemAdapter adapter;
    List<SaleItem> data;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        data = new ArrayList<>();
        data.add(new SaleItem("1", 1));
        data.add(new SaleItem("2", 2));
        data.add(new SaleItem("3", 3));

        adapter = new SaleItemAdapter(getContext(), data);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        data.clear();
    }

    public void testGetItem() {
        assertEquals(new SaleItem("1", 1) , adapter.getItem(0));
        assertEquals(new SaleItem("2", 2) , adapter.getItem(1));
        assertEquals(new SaleItem("3", 3) , adapter.getItem(2));
    }

}