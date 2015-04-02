package com.teamanything.goonsquad;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Adam on 4/2/2015.
 *
 * Test cases for MainActivity
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    MainActivity activity;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
