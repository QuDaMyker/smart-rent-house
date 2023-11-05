package com.example.renthouse.Activity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ActivitySignUpUnitTest {
    @Test
    public void testValidEmail() {
        // Test với một địa chỉ email hợp lệ
        String validEmail = "test@example.com";
        boolean isValid = ActivitySignUp.isValidEmail(validEmail);
        assertTrue(isValid);
    }

    @Test
    public void testInvalidEmail() {
        // Test với một địa chỉ email không hợp lệ
        String invalidEmail = "invalid_email";
        boolean isValid = ActivitySignUp.isValidEmail(invalidEmail);
        assertFalse(isValid);
    }

    @Test
    public void testNullEmail() {
        // Test với địa chỉ email là null
        String nullEmail = null;
        boolean isValid = ActivitySignUp.isValidEmail(nullEmail);
        assertFalse(isValid);
    }

    @Test
    public void testNullPassword() {
        // test voi mat khau null
        String nullPassword = null;
        boolean isValid = ActivitySignUp.isValidPassword(nullPassword);
        assertFalse(isValid);
    }
    @Test
    public void testValidPassword() {
        // Test với mật khẩu hợp lệ
        String validPassword = "Abc123!@";
        boolean isValid = ActivitySignUp.isValidPassword(validPassword);
        assertTrue(isValid);
    }

    @Test
    public void testShortPassword() {
        // Test với mật khẩu quá ngắn
        String shortPassword = "Abc123";
        boolean isValid = ActivitySignUp.isValidPassword(shortPassword);
        assertFalse(isValid);
    }

    @Test
    public void testLongPassword() {
        // Test với mật khẩu quá dài
        String longPassword = "Abc1234567Abc1234";
        boolean isValid = ActivitySignUp.isValidPassword(longPassword);
        assertFalse(isValid);
    }

    @Test
    public void testNoDigit() {
        // Test với mật khẩu không có chữ số
        String noDigitPassword = "Abcdef!@";
        boolean isValid = ActivitySignUp.isValidPassword(noDigitPassword);
        assertFalse(isValid);
    }

    @Test
    public void testNoLowercase() {
        // Test với mật khẩu không có chữ thường
        String noLowercasePassword = "ABC123!@";
        boolean isValid = ActivitySignUp.isValidPassword(noLowercasePassword);
        assertFalse(isValid);
    }

    @Test
    public void testNoUppercase() {
        // Test với mật khẩu không có chữ hoa
        String noUppercasePassword = "abc123!@";
        boolean isValid = ActivitySignUp.isValidPassword(noUppercasePassword);
        assertFalse(isValid);
    }

    @Test
    public void testNoSpecialCharacter() {
        // Test với mật khẩu không có ký tự đặc biệt
        String noSpecialCharPassword = "Abc123456";
        boolean isValid = ActivitySignUp.isValidPassword(noSpecialCharPassword);
        assertFalse(isValid);
    }

    @Test
    public void testIsVietnameseStringWithValidInput() {
        // Test with valid Vietnamese strings
        assertTrue(ActivitySignUp.isVietnameseString("Xin chào"));
        assertTrue(ActivitySignUp.isVietnameseString("Cái nắng"));
        assertTrue(ActivitySignUp.isVietnameseString("Sông Hồng"));
        assertTrue(ActivitySignUp.isVietnameseString("Bánh mì"));

        // Test with valid Vietnamese strings containing spaces
        assertTrue(ActivitySignUp.isVietnameseString("Cầu Rồng"));
    }

    @Test
    public void testIsVietnameseStringWithInvalidInput() {
        // Test with non-Vietnamese strings
        assertTrue(ActivitySignUp.isVietnameseString("Hello"));
        assertFalse(ActivitySignUp.isVietnameseString("12345"));
        assertFalse(ActivitySignUp.isVietnameseString("!@#$%"));

        // Test with mixed characters
        assertFalse(ActivitySignUp.isVietnameseString("Xin chào, World!"));
    }

    @Test
    public void testIsVietnameseStringWithEmptyInput() {
        // Test with an empty string
        assertFalse(ActivitySignUp.isVietnameseString(""));
    }

    @Test
    public void testIsVietnameseStringWithNullInput() {
        // Test with null input
        assertFalse(ActivitySignUp.isVietnameseString(null));
    }

    @Test
    public void testIsVietnamesePhoneNumberWithValidInput() {
        // Test with valid Vietnamese phone numbers
        assertFalse(ActivitySignUp.isVietnameseString("0123456789"));
        assertFalse(ActivitySignUp.isVietnameseString("0356789123"));
        assertFalse(ActivitySignUp.isVietnameseString("0987654321"));
    }

    @Test
    public void testIsVietnamesePhoneNumberWithInvalidInput() {
        // Test with non-Vietnamese phone numbers
        assertFalse(ActivitySignUp.isVietnameseString("1234567890")); // Does not start with 0[35789]
        assertFalse(ActivitySignUp.isVietnameseString("09876543210")); // Too long
        assertFalse(ActivitySignUp.isVietnameseString("0356789abc"));  // Contains non-digit characters
        assertFalse(ActivitySignUp.isVietnameseString("0123456"));     // Too short
    }

    @Test
    public void testIsVietnamesePhoneNumberWithEmptyInput() {
        // Test with an empty string
        assertFalse(ActivitySignUp.isVietnameseString(""));
    }

    @Test
    public void testIsVietnamesePhoneNumberWithNullInput() {
        // Test with null input
        assertFalse(ActivitySignUp.isVietnameseString(null));
    }
}
