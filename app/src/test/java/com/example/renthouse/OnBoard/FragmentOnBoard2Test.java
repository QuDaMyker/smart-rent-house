package com.example.renthouse.OnBoard;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.renthouse.R;

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
        LayoutInflater mockInflater = mock(LayoutInflater.class);
        ViewGroup mockContainer = mock(ViewGroup.class);
        View mockView = mock(View.class);
        when(mockInflater.inflate(eq(R.layout.fragment_on_board2), eq(mockContainer), eq(false)))
                .thenReturn(mockView);
        fragment.onCreateView(mockInflater, mockContainer, null);
    }
}