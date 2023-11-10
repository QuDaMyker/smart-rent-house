package com.example.renthouse.utilities;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.content.SharedPreferences;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PreferenceManagerTest {
    @Mock
    private SharedPreferences mockSharedPreferences;
    @Mock
    private SharedPreferences.Editor mockEditor;
    @Mock
    private Context mockContext;

    private PreferenceManager preferenceManager;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        // Sử dụng MockSharedPreferences để kiểm tra PreferenceManager
        when(mockContext.getSharedPreferences(anyString(), anyInt())).thenReturn(mockSharedPreferences);
        when(mockSharedPreferences.edit()).thenReturn(mockEditor);

        preferenceManager = new PreferenceManager(mockContext);
    }

    @Test
    public void putBooleanTest() {
        // Giả lập việc lưu giá trị boolean vào SharedPreferences
        preferenceManager.putBoolean("testKey", true);

        // Kiểm tra xem putBoolean đã được gọi với đúng key và giá trị
        verify(mockEditor).putBoolean("testKey", true);
        // Kiểm tra xem apply đã được gọi
        verify(mockEditor).apply();
    }

    @Test
    public void getBooleanTest() {
        // Giả lập việc trả về giá trị boolean từ SharedPreferences
        when(mockSharedPreferences.getBoolean("testKey", false)).thenReturn(true);

        // Kiểm tra xem phương thức getBoolean trả về giá trị đúng
        boolean result = preferenceManager.getBoolean("testKey");
        assert(result);
    }

    @Test
    public void putStringTest() {
        // Giả lập việc lưu giá trị string vào SharedPreferences
        preferenceManager.putString("testKey", "testValue");

        // Kiểm tra xem putString đã được gọi với đúng key và giá trị
        verify(mockEditor).putString("testKey", "testValue");
        // Kiểm tra xem apply đã được gọi
        verify(mockEditor).apply();
    }

    @Test
    public void getStringTest() {
        // Giả lập việc trả về giá trị string từ SharedPreferences
        when(mockSharedPreferences.getString("testKey", null)).thenReturn("testValue");

        // Kiểm tra xem phương thức getString trả về giá trị đúng
        String result = preferenceManager.getString("testKey");
        Assert.assertEquals("testValue", result);
    }

    @Test
    public void clearTest() {
        // Gọi phương thức clear
        preferenceManager.clear();

        // Kiểm tra xem clear đã được gọi
        verify(mockEditor).clear();
        // Kiểm tra xem apply đã được gọi
        verify(mockEditor).apply();
    }
}