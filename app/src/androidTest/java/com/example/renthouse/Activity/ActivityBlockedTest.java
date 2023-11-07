package com.example.renthouse.Activity;

import static org.junit.Assert.*;

import android.view.View;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ActivityBlockedTest {

    @Rule
    public ActivityTestRule<ActivityBlocked> activityBlockedActivityTestRule =
            new ActivityTestRule<ActivityBlocked>(ActivityBlocked.class);
    private ActivityBlocked activityBlocked = null;
    @Before
    public void setup() throws Exception{
        activityBlocked = activityBlockedActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch() {
        assertNotNull(activityBlocked);
    }

    @After
    public void tearDown() {
        activityBlocked = null;
    }

    @Test
    public void onCreate() {
        assertNotNull(activityBlocked);
    }

    @Test
    public void sendReport() {
        //assertNotNull(activityBlocked.sendReport());
    }
}