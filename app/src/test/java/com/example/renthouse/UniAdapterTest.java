package com.example.renthouse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.renthouse.Adapter.UniAdapter;
import com.example.renthouse.OOP.University;

public class UniAdapterTest {

    private UniAdapter uniAdapter;

    @Mock
    private UniAdapter.OnItemClickListener mockListener;

    private University mockUniversity;

    @Mock
    LayoutInflater mockInflater;

    @Mock
    View mockView;

    @Mock
    ViewGroup mockParent;

    private int dummyTestId;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        List<University> mockItemList = new ArrayList<>();
        mockUniversity = new University("UIT", "Thủ Đức");
        mockItemList.add(mockUniversity);

        uniAdapter = new UniAdapter(mockItemList);
        uniAdapter.setOnItemClickListener(mockListener);
        uniAdapter.introView = mock(LinearLayout.class);
        uniAdapter.helpTv = mock(TextView.class);
    }

    @Test
    public void testGetItemCount() {
        assertEquals(1, uniAdapter.getItemCount());
    }

    @Test
    public void testFilter() {

        uniAdapter.filter("UIT");
        // Kiểm tra rằng filteredList đã được cập nhật đúng
        assertEquals(1, uniAdapter.filteredList.size());

    }

    @Test
    public void testFilterWithEmptyKeyword() {
        uniAdapter.filter("");
        assertEquals(1, uniAdapter.filteredList.size());
    }

    @Test
    public void testFilterWithEmptyList() {
        List<University> emptyList = new ArrayList<>();
        uniAdapter = new UniAdapter(emptyList);

        uniAdapter.filter("keyword");
        assertEquals(0, uniAdapter.filteredList.size());
    }

    @Test
    public void testSetIntroView() {
        LinearLayout mockIntroView = mock(LinearLayout.class);
        uniAdapter.setIntroView(mockIntroView);

        // Kiểm tra rằng introView đã được set đúng
        assertEquals(mockIntroView, uniAdapter.introView);
    }

    @Test
    public void testSetHelpTv() {
        TextView mockHelpTv = mock(TextView.class);
        uniAdapter.setHelpTv(mockHelpTv);

        // Kiểm tra rằng helpTv đã được set đúng
        assertEquals(mockHelpTv, uniAdapter.helpTv);
    }


}
