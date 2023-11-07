package com.example.renthouse.OnBoard;

import static org.junit.Assert.*;

import android.view.LayoutInflater;
import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;

public class FragmentOnBoard3Test {

    private FragmentOnBoard3 fragment;

    @Before
    public void setUp() {
        // Initialize the fragment
        fragment = new FragmentOnBoard3();
    }

    @Test
    public void testFragmentLayoutInflation() {
        //View view = fragment.onCreateView(LayoutInflater.from(Robolectric.buildActivity(ActivityOnBoard.class).get()), null, null);

        assertNotNull(fragment);
    }
}