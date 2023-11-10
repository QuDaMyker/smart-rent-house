package com.example.renthouse.utilities;

import android.content.Context;

import com.example.renthouse.utilities.CacheUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

import java.io.File;

public class CacheUtilsTest {

    @Mock
    private Context mockContext;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void clearCache_Success() {
        // Mocking the cache directory
        File mockCacheDir = mock(File.class);
        when(mockContext.getApplicationContext()).thenReturn(mockContext);
        when(mockContext.getApplicationContext().getCacheDir()).thenReturn(mockCacheDir);

        // Mocking the deletion of the cache directory
        when(mockCacheDir.delete()).thenReturn(true);

        // Call the method to be tested
        CacheUtils.clearCache(mockContext);

        // Verify that getCacheDir() and delete() were called
        verify(mockContext.getApplicationContext()).getCacheDir();
        verify(mockCacheDir).delete();
    }

    @Test
    public void clearCache_Exception() {
        // Mocking the cache directory
        File mockCacheDir = mock(File.class);
        when(mockContext.getApplicationContext()).thenReturn(mockContext);
        when(mockContext.getApplicationContext().getCacheDir()).thenReturn(mockCacheDir);

        // Mocking an exception when deleting the cache directory
        when(mockCacheDir.delete()).thenThrow(new RuntimeException("Unable to delete cache"));

        // Call the method to be tested
        CacheUtils.clearCache(mockContext);

        // Verify that getCacheDir() and delete() were called
        verify(mockContext.getApplicationContext()).getCacheDir();
        verify(mockCacheDir).delete();
    }
}
