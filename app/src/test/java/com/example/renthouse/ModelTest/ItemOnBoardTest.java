package com.example.renthouse.ModelTest;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.renthouse.OOP.ItemOnboard;
import com.example.renthouse.R;

public class ItemOnBoardTest {

    private ItemOnboard itemOnboard;

    @Before
    public void setUp() {
        itemOnboard = new ItemOnboard(R.drawable.ic_onboard_1);
    }

    @Test
    public void testGetResourceId() {
        assertEquals(R.drawable.ic_onboard_1, itemOnboard.getResourceId());
    }

    @Test
    public void testSetResourceId() {
        itemOnboard.setResourceId(R.drawable.ic_onboard_2);
        assertEquals(R.drawable.ic_onboard_2, itemOnboard.getResourceId());
    }
}
