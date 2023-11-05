package com.example.renthouse;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.renthouse.Activity.ActivityMain;
import com.example.renthouse.Fragment.FragmentHome;
import com.example.renthouse.databinding.ActivityMainBinding;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.*;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ActivityMainTest {


    @Test
    public void testOnCreate() {
        ActivityMain activity = mock(ActivityMain.class);

        doCallRealMethod().when(activity).onCreate(null);
        doNothing().when(activity).setDefaultFragment();

        Bundle savedInstanceState = mock(Bundle.class);
        activity.onCreate(savedInstanceState);
        verify(activity, times(1)).setDefaultFragment();


        // Add more verifications as needed...
    }

    @Test
    public void testSetDefaultFragment() {
        ActivityMain activity = mock(ActivityMain.class);
        activity.fragmentManager = mock(FragmentManager.class);
        activity.fragmentTransaction = mock(FragmentTransaction.class);

        doCallRealMethod().when(activity).setDefaultFragment();

        activity.setDefaultFragment();

        // Add assertions to verify expected behavior
        // For example, check if the default fragment is set correctly
    }

    @Test
    public void testReplaceFragment() {
        ActivityMain activity = mock(ActivityMain.class);
        activity.fragmentManager = mock(FragmentManager.class);
        activity.fragmentTransaction = mock(FragmentTransaction.class);

        doCallRealMethod().when(activity).replaceFragment(any(Fragment.class));


        FragmentHome mockFragment = mock(FragmentHome.class);
        activity.replaceFragment(mockFragment);

        // Add assertions to verify expected behavior
        // For example, check if the fragment is replaced correctly
    }

    @Test
    public void testShowDialog() {
        ActivityMain activity = mock(ActivityMain.class);
        activity.progressDialog = mock(ProgressDialog.class);

        doCallRealMethod().when(activity).showDialog();

        activity.showDialog();
    }

    @Test
    public void testDismissDialog() {
        ActivityMain activity = mock(ActivityMain.class);
        activity.progressDialog = mock(ProgressDialog.class);

        doCallRealMethod().when(activity).dismissDialog();

        activity.dismissDialog();
    }

    @Test
    public void testOnOptionsItemSelected() {
        ActivityMain activity = mock(ActivityMain.class);
        doCallRealMethod().when(activity).onOptionsItemSelected(any(MenuItem.class));

        MenuItem mockMenuItem = mock(MenuItem.class);
        when(mockMenuItem.getItemId()).thenReturn(R.id.btnAccount); // Replace with actual menu item ID

        activity.onOptionsItemSelected(mockMenuItem);
    }
}
