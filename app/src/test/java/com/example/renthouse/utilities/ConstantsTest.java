package com.example.renthouse.utilities;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ConstantsTest {

    @Test
    public void testConstants() {
        assertEquals("firstinstall", Constants.KEY_FIRST_INSTALL);
        assertEquals("rentHousePreference", Constants.KEY_PREFERENCE_NAME);
        assertEquals("user_key", Constants.KEY_USER_KEY);
        assertEquals("email", Constants.KEY_EMAIL);
        assertEquals("fullname", Constants.KEY_FULLNAME);
        assertEquals("image", Constants.KEY_IMAGE);
        assertEquals("password", Constants.KEY_PASSWORD);
        assertEquals("ngayTaoTaiKhoan", Constants.KEY_DATECREATEDACCOUNT);
        assertEquals("phoneNumber", Constants.KEY_PHONENUMBER);
        assertEquals("isSignedIn", Constants.KEY_IS_SIGNED_IN);
        assertEquals("isBlocked", Constants.KEY_IS_BLOCKED);
        assertEquals("nguoidung", Constants.KEY_NGUOIDUNG);
        assertEquals("historySearch", Constants.KEY_HISTORY_SEARCH);
        assertEquals("pending", Constants.STATUS_PENDING);
        assertEquals("approved", Constants.STATUS_APPROVED);
        assertEquals("expired", Constants.STATUS_EXPIRED);
        assertEquals("deleted", Constants.STATUS_DELETED);
    }
}
