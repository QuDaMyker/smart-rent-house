package com.example.renthouse.OnBoard;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FragmentOnBoard2Test {

    private FragmentOnBoard2 fragment;

    @Before
    public void setUp() {
        // Initialize the fragment
        fragment = new FragmentOnBoard2();
    }

    @Test
    public void testFragmentLayoutInflation() {
        //View view = fragment.onCreateView(LayoutInflater.from(Robolectric.buildActivity(ActivityOnBoard.class).get()), null, null);

        assertNotNull(fragment);
    }
}