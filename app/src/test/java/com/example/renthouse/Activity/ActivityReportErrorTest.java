//package com.example.renthouse.Activity;
//
//import static org.junit.Assert.*;
//
//import android.text.Editable;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageButton;
//
//import com.example.renthouse.R;
//import com.google.android.material.textfield.TextInputEditText;
//import com.google.android.material.textfield.TextInputLayout;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.JUnit4;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.robolectric.RobolectricTestRunner;
//
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;
//@RunWith(RobolectricTestRunner.class)
//public class ActivityReportErrorTest {
//    private ActivityReportError activityReportError;
//    @Mock
//    private TextInputLayout txtInTieuDe, txtInMoTa;
//    @Mock
//    private TextInputEditText txtEdtTieuDe, txtEdtMoTa;
//    @Mock
//    private ImageButton btnBack;
//    @Mock
//    private Button btnSend;
//    @Mock
//    private LayoutInflater layoutInflater;
//    @Mock
//    private ViewGroup viewGroup;
//    @Mock
//    private View view;
//
//    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        activityReportError = new ActivityReportError();
//        activityReportError.txtInTieuDe = txtInTieuDe;
//        activityReportError.txtInMoTa = txtInMoTa;
//        activityReportError.txtEdtTieuDe = txtEdtTieuDe;
//        activityReportError.txtEdtMoTa = txtEdtMoTa;
//        activityReportError.btnBack = btnBack;
//        activityReportError.btnSend = btnSend;
//    }
//
//    @Test
//    public void testGetTieuDe() {
//        when(txtEdtTieuDe.getText()).thenReturn(mock(Editable.class));
//        when(txtEdtTieuDe.getText().toString()).thenReturn("HoTro");
//        assertEquals("HoTro", activityReportError.getTieuDe());
//    }
//
//    @Test
//    public void testGetMoTa() {
//        when(txtEdtMoTa.getText()).thenReturn(mock(Editable.class));
//        when(txtEdtMoTa.getText().toString()).thenReturn("HoTro");
//        assertEquals("HoTro", activityReportError.getMoTa());
//    }
//
//    @Test
//    public void onCreate() {
//
//    }
//
//    @Test
//    public void onActivityResult() {
//    }
//}