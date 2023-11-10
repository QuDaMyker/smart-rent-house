package com.example.renthouse.Activity;

import static org.mockito.Mockito.when;

import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.Fragment.FragmentFilter;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.Mock;

public class ActivitySearchTest extends TestCase {

    @Mock
    private FragmentManager fragmentManager;
    @Mock
    private FragmentTransaction fragmentTransaction;
    @Mock
    private TextView textViewHistorySearch;
    @Mock
    private RecyclerView recyclerView;
    @Test
    public void onCreate() {
    }

    @Test
    public void showFragmentFilter() {
        // Chuẩn bị một đối tượng FragmentFilter và địa chỉ giả lập
        String address = "TP Hồ Chí Minh";
        FragmentFilter fragmentFilter = new FragmentFilter(address);

        ActivitySearch activity = new ActivitySearch();
        when(activity.getSupportFragmentManager()).thenReturn(fragmentManager);
        when(fragmentManager.beginTransaction()).thenReturn(fragmentTransaction);

    }

    @Test
    public void showFragmentFilterPhoBien() {
    }

    @Test
    public void hideFragmentFilter() {
    }

    @Test
    public void readHistoryLocation() {
    }
}