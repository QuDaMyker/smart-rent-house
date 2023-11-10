package com.example.renthouse.FragmentPost;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.Adapter.PhotoAdapter;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;
import com.google.android.material.button.MaterialButton;

public class FragmentUtilitiesTest {

    @Mock
    private LinearLayout addImg;

    @Mock
    private LinearLayout imgLayout;

    @Mock
    private MaterialButton addImgBtn;

    @Mock
    private RecyclerView rcvPhoto;

    @Mock
    private TextView warningTxt;

    @Mock
    private GridLayout gridLayout;

    @Mock
    private PhotoAdapter photoAdapter;

    // Initialize the FragmentUtilities
    private FragmentUtilities fragmentUtilities;

    @Mock
    private Context mockContext;
    @Mock
    private Resources mockResources;

    @Before
    public void setUp() {
        // Initialize mocks
        MockitoAnnotations.initMocks(this);

        // Create a new instance of FragmentUtilities with mocked objects
        fragmentUtilities = new FragmentUtilities();

        // Set the mocked objects
        fragmentUtilities.addImg = addImg;
        fragmentUtilities.imgLayout = imgLayout;
        fragmentUtilities.addImgBtn = addImgBtn;
        fragmentUtilities.rcvPhoto = rcvPhoto;
        fragmentUtilities.warningTxt = warningTxt;
        fragmentUtilities.gridLayout = gridLayout;
        fragmentUtilities.photoAdapter = photoAdapter;

        when(mockContext.getResources()).thenReturn(mockResources);

    }

//    @Test
//    public void testAddImg() {
//        FragmentUtilities fragmentUtilities = new FragmentUtilities();
//
//        // Mock dependencies
//        List<Uri> mockUriList = Arrays.asList(mock(Uri.class), mock(Uri.class));
//        PhotoAdapter mockPhotoAdapter = mock(PhotoAdapter.class);
//        MaterialButton mockAddImgBtn = mock(MaterialButton.class);
//        LinearLayout mockImgLayout = mock(LinearLayout.class);
//        TextView mockWarningTxt = mock(TextView.class);
//
//        // Set mocked dependencies
//        fragmentUtilities.photoAdapter = mockPhotoAdapter;
//        fragmentUtilities.addImgBtn = mockAddImgBtn;
//        fragmentUtilities.imgLayout = mockImgLayout;
//        fragmentUtilities.warningTxt = mockWarningTxt;
//
//        // Provide a mocked Context with a color resource
//        when(mockContext.getColor(R.color.Primary_60)).thenReturn(Color.BLUE);
//
//        // Call the addImg method with the mocked Context
//        fragmentUtilities.addImg(mockUriList);
//
//        // Verify interactions
//        verify(mockPhotoAdapter).setData(mockUriList);
//        verify(mockAddImgBtn).setVisibility(View.GONE);
//        verify(mockImgLayout).setVisibility(View.VISIBLE);
//        verify(mockAddImgBtn).setText("Bấm vào đây để đăng hình ảnh từ thư viện nhé!");
//        verify(mockAddImgBtn).setTextColor(Color.BLUE); // Use the mocked color
//        verify(mockWarningTxt).setText("(Tối thiểu 4 ảnh, tối đa 20 ảnh)");
//        verify(mockWarningTxt).setTextColor(anyInt()); // Use anyInt() for other color
//    }

    @Test
    public void testGetUtilities() {
        // Mocking gridLayout
        GridLayout gridLayout = mock(GridLayout.class);
        fragmentUtilities.gridLayout = gridLayout;

        // Mocking child views
        MaterialButton button1 = mock(MaterialButton.class);
        MaterialButton button2 = mock(MaterialButton.class);

        Mockito.when(gridLayout.getChildCount()).thenReturn(2);
        Mockito.when(gridLayout.getChildAt(0)).thenReturn(button1);
        Mockito.when(gridLayout.getChildAt(1)).thenReturn(button2);

        // Mocking isChecked() method
        Mockito.when(button1.isChecked()).thenReturn(true);
        Mockito.when(button2.isChecked()).thenReturn(false);
        Mockito.when(button1.getText()).thenReturn("Button 1");
        Mockito.when(button2.getText()).thenReturn("");

        List<String> expectedUtilities = new ArrayList<>();
        expectedUtilities.add("Button 1");

        assertEquals(expectedUtilities, fragmentUtilities.getUtilities());
    }

    @Test
    public void testGetUtilitiesIdx() {
        // Mocking gridLayout
        GridLayout gridLayout = mock(GridLayout.class);
        fragmentUtilities.gridLayout = gridLayout;

        // Mocking child views
        MaterialButton button1 = mock(MaterialButton.class);
        MaterialButton button2 = mock(MaterialButton.class);

        Mockito.when(gridLayout.getChildCount()).thenReturn(2);
        Mockito.when(gridLayout.getChildAt(0)).thenReturn(button1);
        Mockito.when(gridLayout.getChildAt(1)).thenReturn(button2);

        // Mocking isChecked() method
        Mockito.when(button1.isChecked()).thenReturn(true);
        Mockito.when(button2.isChecked()).thenReturn(false);

        List<Integer> expectedIndexes = new ArrayList<>();
        expectedIndexes.add(0);

        assertEquals(expectedIndexes, fragmentUtilities.getUtilitiesIdx());
    }

    @Test
    public void testGetUriListImg() {
        List<Uri> uriList = new ArrayList<>();
        Uri uri1 = Uri.parse("uri1");
        Uri uri2 = Uri.parse("uri2");
        uriList.add(uri1);
        uriList.add(uri2);
        fragmentUtilities.uriListImg = uriList;

        List<Uri> result = fragmentUtilities.getUriListImg();

        assertEquals(uriList, result);
    }

    @Test
    public void testSetData() {
        Room mockRoom = Mockito.mock(Room.class);
        List<String> images = new ArrayList<>();
        images.add("uri1");
        images.add("uri2");
        Mockito.when(mockRoom.getImages()).thenReturn(images);

        GridLayout gridLayout = mock(GridLayout.class);
        fragmentUtilities.gridLayout = gridLayout;

        // Mocking child views
        MaterialButton button1 = mock(MaterialButton.class);
        MaterialButton button2 = mock(MaterialButton.class);

        ArrayList<String> utilities = new ArrayList<>();
        utilities.add("Internet");
        utilities.add("Electricity");

        Mockito.when(gridLayout.getChildCount()).thenReturn(2);
        Mockito.when(gridLayout.getChildAt(0)).thenReturn(button1);
        Mockito.when(gridLayout.getChildAt(1)).thenReturn(button2);
        Mockito.when(mockRoom.getUtilities()).thenReturn(utilities);
        Mockito.when(button1.getText()).thenReturn("Internet");
        Mockito.when(button2.getText()).thenReturn("");

        // Mocking isChecked() method
        Mockito.when(button1.isChecked()).thenReturn(true);
        Mockito.when(button2.isChecked()).thenReturn(false);


        fragmentUtilities.setData(mockRoom);


    }

//    @Test
//    public void testValidateData() {
//        // Assuming mockAddImgBtn.setTextColor(), mockWarningTxt.setText(), and
//        // mockWarningTxt.setTextColor() methods do not throw exceptions.
//
//        // Test when uriListImg size is less than 4
//        fragmentUtilities.uriListImg = new ArrayList<>();
//        boolean result1 = fragmentUtilities.validateData();
//        assertEquals(false, result1);
//
//        // Test when uriListImg size is greater than 20
//        List<Uri> uriList = new ArrayList<>();
//        for (int i = 0; i < 21; i++) {
//            uriList.add(Uri.parse("uri" + i));
//        }
//        fragmentUtilities.uriListImg = uriList;
//        boolean result2 = fragmentUtilities.validateData();
//        assertEquals(false, result2);
//
//        // Test when uriListImg size is between 4 and 20
//        List<Uri> uriList3 = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            uriList3.add(Uri.parse("uri" + i));
//        }
//        fragmentUtilities.uriListImg = uriList3;
//        boolean result3 = fragmentUtilities.validateData();
//        assertEquals(true, result3);
//    }

//    @Test
//    public void testOnCreate() {
//        FragmentUtilities fragmentUtilities = new FragmentUtilities();
//
//        Bundle savedInstanceState = mock(Bundle.class);
//
//        fragmentUtilities.onCreate(savedInstanceState);
//
//    }

    @Test
    public void testOnPause() {
        GridLayout gridLayout = mock(GridLayout.class);
        fragmentUtilities.gridLayout = gridLayout;

        // Mocking child views
        MaterialButton button1 = mock(MaterialButton.class);
        MaterialButton button2 = mock(MaterialButton.class);

        Mockito.when(gridLayout.getChildCount()).thenReturn(2);
        Mockito.when(gridLayout.getChildAt(0)).thenReturn(button1);
        Mockito.when(gridLayout.getChildAt(1)).thenReturn(button2);

        // Mocking isChecked() method
        Mockito.when(button1.isChecked()).thenReturn(true);
        Mockito.when(button2.isChecked()).thenReturn(false);

        List<Integer> expectedIndexes = new ArrayList<>();
        expectedIndexes.add(0);

        ArrayList<Integer> utilIdx = new ArrayList<>();
        utilIdx.add(1); // Add some values for testing

        Bundle bundle = mock(Bundle.class);
        when(bundle.getIntegerArrayList("utilIdx")).thenReturn(utilIdx);
        fragmentUtilities.setArguments(bundle);

        fragmentUtilities.onPause();

        // Add assertions as needed to verify the behavior of onPause
    }

    @Test
    public void testOnStart() {

        Bundle mockBundle = mock(Bundle.class);
        when(mockBundle.getIntegerArrayList("utilIdx")).thenReturn(new ArrayList<>(Arrays.asList(1, 2))); // Set up a mock bundle

        GridLayout gridLayout = mock(GridLayout.class);
        fragmentUtilities.gridLayout = gridLayout;

        // Mocking child views
        MaterialButton button1 = mock(MaterialButton.class);
        MaterialButton button2 = mock(MaterialButton.class);

        Mockito.when(gridLayout.getChildCount()).thenReturn(2);
        Mockito.when(gridLayout.getChildAt(0)).thenReturn(button1);
        Mockito.when(gridLayout.getChildAt(1)).thenReturn(button2);

        // Mocking isChecked() method
        Mockito.when(button1.isChecked()).thenReturn(true);
        Mockito.when(button2.isChecked()).thenReturn(false);
        fragmentUtilities.addImgBtn = mock(MaterialButton.class); // Mock the MaterialButton
        fragmentUtilities.imgLayout = mock(LinearLayout.class); // Mock the LinearLayout
        fragmentUtilities.photoAdapter = mock(PhotoAdapter.class); // Mock the PhotoAdapter

        fragmentUtilities.setArguments(mockBundle); // Set the mock bundle

        fragmentUtilities.onStart();

        // Add assertions as needed to verify the behavior of onStart
    }





}
