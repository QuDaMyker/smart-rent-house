package com.example.renthouse;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.renthouse.Activity.ActivityLogIn;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testing_activityLogin_validEmail_1() {
        // test case for empty input
        assertEquals(false, ActivityLogIn.isValidEmail(""));
    }

    @Test
    public void testing_activityLogin_validEmail_2() {
        // test case for input without '@.com'
        assertEquals(false, ActivityLogIn.isValidEmail("aaa"));
    }

    @Test
    public void testing_activityLogin_validEmail_3() {
        // test case for input without '@'
        assertEquals(false, ActivityLogIn.isValidEmail("aaa.com"));
    }

    @Test
    public void testing_activityLogin_validEmail_4() {
        // test case for input only '@'
        assertEquals(false, ActivityLogIn.isValidEmail("@"));
    }

    @Test
    public void testing_activityLogin_validPassword_1() {
        // empty password
        assertEquals(false, ActivityLogIn.isValidPassword(""));
    }

    @Test
    public void testing_activityLogin_validPassword_2() {
        // invalid length of password min 8 max 15
        assertEquals(false, ActivityLogIn.isValidPassword("aaaaaaa"));
        assertEquals(false, ActivityLogIn.isValidPassword("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
    }

    @Test
    public void testing_activityLogin_validPassword_3() {

    }

    @Test
    public void testing_activityLogin_validPassword_4() {

    }

    @Test
    public void testing_activityLogin_validPassword_5() {

    }
}