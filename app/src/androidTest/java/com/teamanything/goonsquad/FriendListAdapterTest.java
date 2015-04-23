package com.teamanything.goonsquad;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 4/2/2015.
 *
 * JUnit Test class for Adapter
 */
public class FriendListAdapterTest extends AndroidTestCase {

    FriendListAdapter adapter;
    List<String> data;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        data = new ArrayList<>();
        data.add("Friend 1");
        data.add("Friend 2");
        data.add("Friend 3");

        adapter = new FriendListAdapter(getContext(), data);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        data.clear();
    }

    /**
     * Tests that Friends are added properly
     */
    public void testAdd() {
        adapter.add("Friend 4");
        assertTrue(adapter.contains("Friend 4"));
    }

    /**
     * Tests that items are present and ordered correctly
     */
    public void testGetItem() {
        assertEquals("Friend 3", adapter.getItem(2));
    }

    /**
     * Tests that Friends are removed properly
     */
    public void testRemove() {
        adapter.remove("Friend 2");
        assertFalse(adapter.contains("Friend 2"));
    }

    /**
     * Tests for proper count
     */
    public void testCount() {
        assertEquals(3, adapter.getCount());
    }
}