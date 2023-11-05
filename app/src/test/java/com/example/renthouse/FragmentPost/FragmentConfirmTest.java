package com.example.renthouse.FragmentPost;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.renthouse.FragmentPost.FragmentConfirm;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FragmentConfirmTest {

    private FragmentConfirm fragmentConfirm;

    @Mock
    private TextInputEditText mockPhoneNumberEditText;
    @Mock
    private TextInputEditText mockTitleEditText;
    @Mock
    private TextInputEditText mockDescriptionEditText;
    @Mock
    private TextInputLayout mockPhoneNumberLayout;
    @Mock
    private TextInputLayout mockTitleLayout;
    @Mock
    private TextInputLayout mockDescriptionLayout;

    @Mock
    private LayoutInflater mockInflater;
    @Mock
    private ViewGroup mockContainer;
    @Mock
    private View mockView;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        fragmentConfirm = new FragmentConfirm();

        fragmentConfirm.edtPhoneNumber = mockPhoneNumberEditText;
        fragmentConfirm.edtTitle = mockTitleEditText;
        fragmentConfirm.edtDescription = mockDescriptionEditText;
        fragmentConfirm.edtLayoutPhoneNumber = mockPhoneNumberLayout;
        fragmentConfirm.edtLayoutTitle = mockTitleLayout;
        fragmentConfirm.edtLayoutDescription = mockDescriptionLayout;
    }

    @Test
    public void testGetPhoneNumber() {
        when(mockPhoneNumberEditText.getText()).thenReturn(mock(Editable.class));
        when(mockPhoneNumberEditText.getText().toString()).thenReturn("123456789");

        assertEquals("123456789", fragmentConfirm.getPhoneNumber());
    }

    @Test
    public void testGetTitle() {
        when(mockTitleEditText.getText()).thenReturn(mock(Editable.class));
        when(mockTitleEditText.getText().toString()).thenReturn("Sample Title");

        assertEquals("Sample Title", fragmentConfirm.getTitle());
    }

    @Test
    public void testGetDescription() {
        when(mockDescriptionEditText.getText()).thenReturn(mock(Editable.class));
        when(mockDescriptionEditText.getText().toString()).thenReturn("Sample Description");

        assertEquals("Sample Description", fragmentConfirm.getDescription());
    }

    @Test
    public void testValidateDataValidInput() {
        when(mockTitleEditText.getText()).thenReturn(mock(Editable.class));
        when(mockTitleEditText.getText().toString()).thenReturn("Sample Title");
        when(mockDescriptionEditText.getText()).thenReturn(mock(Editable.class));
        when(mockDescriptionEditText.getText().toString()).thenReturn("Sample Description");
        when(mockPhoneNumberEditText.getText()).thenReturn(mock(Editable.class));
        when(mockPhoneNumberEditText.getText().toString()).thenReturn("123456789");

        assertTrue(fragmentConfirm.validateData());
    }

    @Test
    public void testValidateDataEmptyTitle() {
        when(mockTitleEditText.getText()).thenReturn(mock(Editable.class));
        when(mockTitleEditText.getText().toString()).thenReturn("");
        when(mockDescriptionEditText.getText()).thenReturn(mock(Editable.class));
        when(mockDescriptionEditText.getText().toString()).thenReturn("");
        when(mockPhoneNumberEditText.getText()).thenReturn(mock(Editable.class));
        when(mockPhoneNumberEditText.getText().toString()).thenReturn("");

        assertFalse(fragmentConfirm.validateData());
        verify(mockTitleLayout).setError("Vui lòng nhập tiêu đề bài đăng");
    }

    @Test
    public void testSetData() {
        Room mockRoom = mock(Room.class);
        when(mockRoom.getPhoneNumber()).thenReturn("123456789");
        when(mockRoom.getTitle()).thenReturn("Sample Title");
        when(mockRoom.getDescription()).thenReturn("Sample Description");

        fragmentConfirm.setData(mockRoom);

        verify(mockPhoneNumberEditText).setText("123456789");
        verify(mockTitleEditText).setText("Sample Title");
        verify(mockDescriptionEditText).setText("Sample Description");
    }

    @Test
    public void testOnCreate() {
        fragmentConfirm.onCreate(null);

        // Add assertions to check if any initialization logic in onCreateView is working as expected
        // For example, check if certain views are initialized, data is loaded, etc.
        assertNotNull(fragmentConfirm.getView());  // Assuming onCreateView initializes the view
    }

    @Test
    public void testOnCreateView() {
        when(mockInflater.inflate(eq(R.layout.fragment_post_confirm), eq(mockContainer), eq(false)))
                .thenReturn(mockView);


        fragmentConfirm.onCreateView(mockInflater, mockContainer, null);

//        // Add assertions or verifications as needed
//        // For example, check if the views are correctly initialized and listeners are set up
//        assertNotNull(fragmentConfirm.edtPhoneNumber);
//        assertNotNull(fragmentConfirm.edtTitle);
//        assertNotNull(fragmentConfirm.edtDescription);
//
//        assertNotNull(fragmentConfirm.edtLayoutPhoneNumber);
//        assertNotNull(fragmentConfirm.edtLayoutTitle);
//        assertNotNull(fragmentConfirm.edtLayoutDescription);
//
//        verify(fragmentConfirm.edtTitle).addTextChangedListener(any(TextWatcher.class));
//        verify(fragmentConfirm.edtDescription).addTextChangedListener(any(TextWatcher.class));
//        verify(fragmentConfirm.edtPhoneNumber).addTextChangedListener(any(TextWatcher.class));

        // You can also mock ActivityPost and test the isDataSet and setData parts if needed
    }






    // Add similar tests for other validation scenarios

    // Add tests for setData() if needed
}
