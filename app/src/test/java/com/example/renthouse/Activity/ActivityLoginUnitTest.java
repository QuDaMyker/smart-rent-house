package com.example.renthouse.Activity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ActivityLoginUnitTest {
    @Test
    public void testValidEmail() {
        // Test với một địa chỉ email hợp lệ
        String validEmail = "test@example.com";
        boolean isValid = ActivityLogIn.isValidEmail(validEmail);
        assertTrue(isValid);
    }

    @Test
    public void testInvalidEmail() {
        // Test với một địa chỉ email không hợp lệ
        String invalidEmail = "invalid_email";
        boolean isValid = ActivityLogIn.isValidEmail(invalidEmail);
        assertFalse(isValid);
    }

    @Test
    public void testNullEmail() {
        // Test với địa chỉ email là null
        String nullEmail = null;
        boolean isValid = ActivityLogIn.isValidEmail(nullEmail);
        assertFalse(isValid);
    }

    @Test
    public void testNullPassword() {
        // test voi mat khau null
        String nullPassword = null;
        boolean isValid = ActivityLogIn.isValidPassword(nullPassword);
        assertFalse(isValid);
    }
    @Test
    public void testValidPassword() {
        // Test với mật khẩu hợp lệ
        String validPassword = "Abc123!@";
        boolean isValid = ActivityLogIn.isValidPassword(validPassword);
        assertTrue(isValid);
    }

    @Test
    public void testShortPassword() {
        // Test với mật khẩu quá ngắn
        String shortPassword = "Abc123";
        boolean isValid = ActivityLogIn.isValidPassword(shortPassword);
        assertFalse(isValid);
    }

    @Test
    public void testLongPassword() {
        // Test với mật khẩu quá dài
        String longPassword = "Abc1234567Abc1234";
        boolean isValid = ActivityLogIn.isValidPassword(longPassword);
        assertFalse(isValid);
    }

    @Test
    public void testNoDigit() {
        // Test với mật khẩu không có chữ số
        String noDigitPassword = "Abcdef!@";
        boolean isValid = ActivityLogIn.isValidPassword(noDigitPassword);
        assertFalse(isValid);
    }

    @Test
    public void testNoLowercase() {
        // Test với mật khẩu không có chữ thường
        String noLowercasePassword = "ABC123!@";
        boolean isValid = ActivityLogIn.isValidPassword(noLowercasePassword);
        assertFalse(isValid);
    }

    @Test
    public void testNoUppercase() {
        // Test với mật khẩu không có chữ hoa
        String noUppercasePassword = "abc123!@";
        boolean isValid = ActivityLogIn.isValidPassword(noUppercasePassword);
        assertFalse(isValid);
    }

    @Test
    public void testNoSpecialCharacter() {
        // Test với mật khẩu không có ký tự đặc biệt
        String noSpecialCharPassword = "Abc123456";
        boolean isValid = ActivityLogIn.isValidPassword(noSpecialCharPassword);
        assertFalse(isValid);
    }
}
