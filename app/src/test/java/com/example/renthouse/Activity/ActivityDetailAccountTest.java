package com.example.renthouse.Activity;

import static org.junit.Assert.*;

import android.net.Uri;
import android.text.Editable;

import com.example.renthouse.OOP.AccountClass;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ActivityDetailAccountTest {
    private ActivityDetailAccount activityDetailAccount;
    @Mock
    private TextInputEditText fullName, email, phoneNumber, password;
    @Mock
    private FirebaseAuth mAuth;
    @Mock
    private FirebaseUser currentUser;
    @Mock
    private AccountClass accountClass;
    @Mock
    private Uri fileUri;

    @Before
    public void setup(){
        activityDetailAccount = new ActivityDetailAccount();
        activityDetailAccount.TIETfullname = fullName;
        activityDetailAccount.TIETemail = email;
        activityDetailAccount.TIETsodienthoai = phoneNumber;
        activityDetailAccount.TIETMatKhau = password;
    }
    @Test
    public void onCreate() {

        assertNotNull(activityDetailAccount);
    }

    @Test
    public void uploadToFireBase() {
        mAuth = FirebaseAuth.getInstance();
        assertNotNull(mAuth);
    }

    @Test
    public void getFileExtension() {

    }

    @Test
    public void testGetFullName() {
        when(fullName.getText()).thenReturn(mock(Editable.class));
        when(fullName.getText().toString()).thenReturn("quocdanh");

        String result = activityDetailAccount.getFullName();

        assertEquals("quocdanh", result);
    }


    @Test
    public void getEmail() {
        when(email.getText()).thenReturn(mock(Editable.class));
        when(email.getText().toString()).thenReturn("qd@gmail.com");
        String result = activityDetailAccount.getEmail();
        assertEquals("qd@gmail.com", result);

    }

    @Test
    public void getPhoneNumber() {
        when(phoneNumber.getText()).thenReturn(mock(Editable.class));
        when(phoneNumber.getText().toString()).thenReturn("012345678");
        String result = activityDetailAccount.getPhoneNumber();
        assertEquals("012345678", result);
    }

    @Test
    public void getPassword() {
        when(password.getText()).thenReturn(mock(Editable.class));
        when(password.getText().toString()).thenReturn("test@12346");
        String result = activityDetailAccount.getPassword();
        assertEquals("test@12346", result);
    }

    @Test
    public void isValidPassword() {
        when(password.getText()).thenReturn(mock(Editable.class));
        when(password.getText().toString()).thenReturn("test@123456");
        boolean result = activityDetailAccount.isValidPassword("test@123456");
        assertEquals(true, result);
    }
}