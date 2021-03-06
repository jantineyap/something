package com.teamanything.goonsquad;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.teamanything.goonsquad.database.DatabaseHandler;
import com.teamanything.goonsquad.database.SaleItem;

/**
 * Created by Carroll on 4/7/2015.
 *
 * JUnits for DatabaseHandler and SaleItem
 */
public class SaleItemTest extends AndroidTestCase {

    DatabaseHandler db;
    SaleItem a = new SaleItem("xbox", 400);
    SaleItem b = new SaleItem("ps4", 300);

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test");
        db = DatabaseHandler.getInstance(context);
        db.addItem(a);
        db.addItem(b);
    }

    public void testSaleItem() throws Exception {
        assertTrue(db.isSaleItem(a));
        assertTrue(db.isSaleItem(b));

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}

