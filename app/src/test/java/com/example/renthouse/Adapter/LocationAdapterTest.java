package com.example.renthouse.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import com.example.renthouse.Interface.IClickItemAddressListener;
import com.example.renthouse.R;
import com.example.renthouse.Test.Location;
import com.example.renthouse.Test.LocationAdapter;
import com.example.renthouse.utilities.Constants;
import com.example.renthouse.utilities.PreferenceManager;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LocationAdapterTest {

    @Mock
    private Context mockContext;

    @Mock
    private IClickItemAddressListener mockListener;

    @Mock
    private PreferenceManager mockPreferenceManager;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testViewHolderInitialization() {
        LocationAdapter adapter = new LocationAdapter(new ArrayList<>(), mockContext, "currentLocation", mockListener);
        ViewGroup mockParent = mock(ViewGroup.class);
        LayoutInflater mockInflater = mock(LayoutInflater.class);
        View mockView = mock(View.class);

        when(mockContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).thenReturn(mockInflater);
        when(mockInflater.inflate(any(Integer.class), any(ViewGroup.class), any(Boolean.class))).thenReturn(mockView);

        LocationAdapter.LocationViewHolder viewHolder = adapter.onCreateViewHolder(mockParent, 0);

        assertEquals(mockView.findViewById(R.id.diachi), viewHolder.diachi);
        assertEquals(mockView.findViewById(R.id.xoaBtn), viewHolder.xoa);
    }

    @Test
    public void testSaveHistory() {
        LocationAdapter adapter = new LocationAdapter(new ArrayList<>(), mockContext, "currentLocation", mockListener);
        adapter.listHistoryLocation = mock(List.class);
        adapter.saveHistory("Test Location");

        verify(mockListener).onItemClick("Test Location");
    }

    @Test
    public void testSaveHistoryLocation() {
        LocationAdapter adapter = new LocationAdapter(new ArrayList<>(), mockContext, "currentLocation", mockListener);
        adapter.listHistoryLocation = mock(List.class);

        adapter.saveHistoryLocation("Test Location");

        verify(mockPreferenceManager).putString(any(String.class), any(String.class));
    }

    @Test
    public void testRemoveHistoryItem() {
        LocationAdapter adapter = new LocationAdapter(new ArrayList<>(), mockContext, "currentLocation", mockListener);
        adapter.listHistoryLocation = new ArrayList<>();
        adapter.listHistoryLocation.add(new Location("Test Location"));

        adapter.saveHistoryLocation("Test Location");

        verify(mockPreferenceManager).putString(any(String.class), any(String.class));
        assertEquals(1, adapter.listHistoryLocation.size());

        verify(mockPreferenceManager).putString(any(String.class), any(String.class));
        assertEquals(0, adapter.listHistoryLocation.size());
    }

    @Test
    public void testFilterEmptyConstraint() {
        LocationAdapter adapter = new LocationAdapter(new ArrayList<>(), mockContext, "currentLocation", mockListener);
        adapter.listLocationDatabase = new ArrayList<>();
        adapter.listLocationDatabase.add(new Location("Test Location"));

        adapter.getFilter().filter("");

        assertEquals(adapter.listLocation.size(), adapter.listHistoryLocation.size());
        verify(mockListener).onFilterCount(1);
    }

    @Test
    public void testFilterNonEmptyConstraint() {
        LocationAdapter adapter = new LocationAdapter(new ArrayList<>(), mockContext, "currentLocation", mockListener);
        adapter.listLocationDatabase = new ArrayList<>();
        adapter.listLocationDatabase.add(new Location("Test Location"));

        adapter.getFilter().filter("Test");

        assertEquals(1, adapter.listLocation.size());
        verify(mockListener).onFilterCount(1);
    }
}
