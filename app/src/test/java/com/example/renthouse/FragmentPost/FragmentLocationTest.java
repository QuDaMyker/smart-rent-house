package com.example.renthouse.FragmentPost;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.renthouse.Activity.ActivityPost;
import com.example.renthouse.FragmentPost.FragmentLocation;
import com.example.renthouse.OOP.AccountClass;
import com.example.renthouse.OOP.City;
import com.example.renthouse.OOP.District;
import com.example.renthouse.OOP.LocationTemp;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.OOP.Ward;
import com.example.renthouse.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class FragmentLocationTest {

    @Mock
    TextInputEditText mockCityEdt;
    @Mock
    TextInputEditText mockDistrictEdt;
    @Mock
    TextInputEditText mockWardEdt;
    @Mock
    TextInputEditText mockEdtStreet;
    @Mock
    TextInputEditText mockEdtAddress;
    @Mock
    TextInputLayout mockCityLayoutEdt;
    @Mock
    TextInputLayout mockDistrictLayoutEdt;
    @Mock
    TextInputLayout mockWardLayoutEdt;
    @Mock
    TextInputLayout mockEdtLayoutStreet;
    @Mock
    TextInputLayout mockEdtLayoutAddress;

    FragmentLocation fragmentLocation;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        fragmentLocation = new FragmentLocation();

        // Set the mock views in the fragment
        fragmentLocation.cityEdt = mockCityEdt;
        fragmentLocation.districtEdt = mockDistrictEdt;
        fragmentLocation.wardEdt = mockWardEdt;
        fragmentLocation.edtStreet = mockEdtStreet;
        fragmentLocation.edtAddress = mockEdtAddress;
        fragmentLocation.cityLayoutEdt = mockCityLayoutEdt;
        fragmentLocation.districtLayoutEdt = mockDistrictLayoutEdt;
        fragmentLocation.wardLayoutEdt = mockWardLayoutEdt;
        fragmentLocation.edtLayoutStreet = mockEdtLayoutStreet;
        fragmentLocation.edtLayoutAddress = mockEdtLayoutAddress;
    }

    @Test
    public void testLoadJSONFromAsset() throws IOException {
        Context context = mock(Context.class);
        AssetManager  assetManager = mock(AssetManager.class);
        InputStream is = mock(InputStream.class);
        when(context.getAssets()).thenReturn(assetManager);
        when(assetManager.open("test.json")).thenReturn(is);
        String json = fragmentLocation.loadJSONFromAsset("test.json", context);

        assertNotNull(json);
    }


    @Test
    public void testValidateData() {
        when(fragmentLocation.cityEdt.getText()).thenReturn(mock(Editable.class));
        when(fragmentLocation.districtEdt.getText()).thenReturn(mock(Editable.class));
        when(fragmentLocation.wardEdt.getText()).thenReturn(mock(Editable.class));
        when(fragmentLocation.edtAddress.getText()).thenReturn(mock(Editable.class));
        when(fragmentLocation.edtStreet.getText()).thenReturn(mock(Editable.class));

        // Assuming all TextInputEditText fields are filled
        fragmentLocation.cityEdt.setText("");
        fragmentLocation.districtEdt.setText("");
        fragmentLocation.wardEdt.setText("");
        fragmentLocation.edtStreet.setText("");
        fragmentLocation.edtAddress.setText("");

        boolean result = fragmentLocation.validateData();
        assertTrue(result);
    }

    @Test
    public void testOnCreateView() throws IOException {
        LayoutInflater inflater = mock(LayoutInflater.class);
        ViewGroup container = mock(ViewGroup.class);
        Bundle savedInstanceState = mock(Bundle.class);

        View mockView = mock(View.class);
        when(inflater.inflate(anyInt(), any(ViewGroup.class), anyBoolean())).thenReturn(mockView);
        Context context = mock(Context.class);
        AssetManager  assetManager = mock(AssetManager.class);
        InputStream is = mock(InputStream.class);
        when(inflater.getContext()).thenReturn(context);
        when(context.getAssets()).thenReturn(assetManager);
        when(assetManager.open(anyString())).thenReturn(is);

        View resultView = fragmentLocation.onCreateView(inflater, container, savedInstanceState);

        verify(inflater).inflate(ArgumentMatchers.eq(R.layout.fragment_post_location), eq(container), eq(false));


        assertNotNull(resultView);
    }

    @Test
    public void testGetLocation() {
        City city = new City("Ho Chi Minh", "ho-chi-minh", "thanh-pho", "Thành phố Hồ Chí Minh", "79");
        District district = new District("Long Xuyên","thanh-pho","long-xuyen","Thành phố Long Xuyên","Long Xuyên, An Giang","Thành phố Long Xuyên, Tỉnh An Giang","883","89");
        Ward ward = new Ward("Phu Nhuan", "Phuong", "phu-nhuan", "Phường Phu Nhuan", "1", "phu-nhuan", "001", "Q1");

// Assuming edtStreet, edtAddress, selectedCity, selectedDistrict, selectedWard are properties of YourClassName
        fragmentLocation.edtStreet.setText("Street");
        fragmentLocation.edtAddress.setText("Address");
        fragmentLocation.selectedCity = city;
        fragmentLocation.selectedDistrict = district;
        fragmentLocation.selectedWard = ward;

        when(fragmentLocation.edtAddress.getText()).thenReturn(mock(Editable.class));
        when(fragmentLocation.edtStreet.getText()).thenReturn(mock(Editable.class));

        LocationTemp locationTemp = fragmentLocation.getLocation();


        assertEquals(city, locationTemp.getCity());
        assertEquals(district, locationTemp.getDistrict());
        assertEquals(ward, locationTemp.getWard());
    }

    @Test
    public void testSetData() {
        ArrayList<String> utilities = new ArrayList<>();
        utilities.add("Internet");
        utilities.add("Electricity");

        City city = new City("Ho Chi Minh", "ho-chi-minh", "thanh-pho", "Thành phố Hồ Chí Minh", "79");
        District district = new District("Quan 1", "Quan", "quan-1", "Quận 1", "1", "quan-1", "001", "TPHCM");
        Ward ward = new Ward("Phu Nhuan", "Phuong", "phu-nhuan", "Phường Phu Nhuan", "1", "phu-nhuan", "001", "Q1");
        LocationTemp location = new LocationTemp("Nguyen Van B", "123", city, district, ward);
        AccountClass account = new AccountClass("Le Bao Nhu", "nhu@example.com", "0987654321", "password123", "profile.jpg", "2023-10-29", true, "2023-11-01");
        Room room = new Room("1", "Nhà trọ đẹp", "Thuê đi", "homestay", 2, "All", 25, 1000000, 1000000, 0,0,0,false, 0, location, utilities, account, "0987654321", "2023-10-29", false, "pending");

        fragmentLocation.setData(room);
    }

    @Test
    public void testOnCreate() {
        fragmentLocation.onCreate(null);

        // Add assertions to check if any initialization logic in onCreateView is working as expected
        // For example, check if certain views are initialized, data is loaded, etc.
        assertNotNull(fragmentLocation.getView());  // Assuming onCreateView initializes the view
    }

}
