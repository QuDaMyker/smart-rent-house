package com.example.renthouse.OnBoard;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class OnBoardAdapterTest {

    @Test
    public void testGetItem() {
        FragmentManager mockFragmentManager = Mockito.mock(FragmentManager.class);

        OnboardAdapter onboardAdapter = new OnboardAdapter(mockFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        Fragment fragment0 = onboardAdapter.getItem(0);
        Fragment fragment1 = onboardAdapter.getItem(1);
        Fragment fragment2 = onboardAdapter.getItem(2);

        assertEquals(FragmentOnBoard1.class, fragment0.getClass());
        assertEquals(FragmentOnBoard2.class, fragment1.getClass());
        assertEquals(FragmentOnBoard3.class, fragment2.getClass());
    }

    @Test
    public void testGetCount() {
        FragmentManager mockFragmentManager = Mockito.mock(FragmentManager.class);

        OnboardAdapter onboardAdapter = new OnboardAdapter(mockFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        int count = onboardAdapter.getCount();

        assertEquals(3, count);
    }
}
