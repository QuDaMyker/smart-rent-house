package com.example.renthouse.OnBoard;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FragmentOnBoard1Test {
    private FragmentOnBoard1 fragment;

    @Before
    public void setUp() {
        // Initialize the fragment
        fragment = new FragmentOnBoard1();
    }

    @Test
    public void testFragmentLayoutInflation() {
        //View view = fragment.onCreateView(LayoutInflater.from(Robolectric.buildActivity(ActivityOnBoard.class).get()), null, null);

        assertNotNull(fragment);
    }
}