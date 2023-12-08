package com.example.renthouse.Fragment;

import static org.junit.Assert.*;

import org.junit.Test;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;
import com.example.renthouse.Adapter.TabLayoutAccountAdapter;
import com.example.renthouse.R;
import com.google.android.material.tabs.TabLayout;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertNotNull;

public class FragmentAccountTest {

    @Mock
    private View mockView;
    @Mock
    private LayoutInflater mockInflater;
    @Mock
    private ViewGroup mockContainer;
    @Mock
    private Bundle mockSavedInstanceState;
    @Mock
    private TabLayout mockTabLayout;
    @Mock
    private ViewPager2 mockViewPager;
    @Mock
    private TabLayoutAccountAdapter mockAdapter;
    @Mock
    private TabLayout.Tab mockTab;
    @Mock
    private FragmentManager mockFragmentManager;

    private FragmentAccount fragment;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        fragment = new FragmentAccount() {
            @Override
            public View getView() {
                return mockView;
            }
        };
        fragment.fragHome_tab_layout = mockTabLayout;
        fragment.fragHome_viewPager2 = mockViewPager;
        fragment.tabLayoutAccountAdapter = mockAdapter;


        when(mockInflater.inflate(R.layout.fragment_account, mockContainer, false)).thenReturn(mockView);
        when(mockView.findViewById(R.id.fragHome_tab_layout)).thenReturn(mockTabLayout);
        when(mockView.findViewById(R.id.fragHome_viewPager2)).thenReturn(mockViewPager);
        when(mockTabLayout.newTab()).thenReturn(mockTab);


    }

//    @Test
//    public void testOnCreateView() {
//        View result = fragment.onCreateView(mockInflater, mockContainer, mockSavedInstanceState);
//
//        assertNotNull(result);
//        verify(mockTabLayout).addTab(mockTab);
//        verify(mockViewPager).setAdapter(mockAdapter);
//    }
}