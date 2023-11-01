package com.example.renthouse.ModelTest;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.renthouse.OOP.AccountClass;

public class AccountTest {

    private AccountClass account;

    @Before
    public void setUp() {
        account = new AccountClass("Le Bao Nhu", "nhu@example.com", "0987654321", "password123", "profile.jpg", "2023-10-29", true, "2023-11-01");
    }

    @Test
    public void testConstructor() {
        AccountClass account = new AccountClass();
        assertNotNull(account);
    }

    @Test
    public void testGetFullname() {
        assertEquals("Le Bao Nhu", account.getFullname());
    }

    @Test
    public void testSetFullname() {
        account.setFullname("Le Van A");
        assertEquals("Le Van A", account.getFullname());
    }

    @Test
    public void testGetEmail() {
        assertEquals("nhu@example.com", account.getEmail());
    }

    @Test
    public void testSetEmail() {
        account.setEmail("vana@example.com");
        assertEquals("vana@example.com", account.getEmail());
    }

    @Test
    public void testGetPhoneNumber() {
        assertEquals("0987654321", account.getPhoneNumber());
    }

    @Test
    public void testSetPhoneNumber() {
        account.setPhoneNumber("0823306992");
        assertEquals("0823306992", account.getPhoneNumber());
    }

    @Test
    public void testGetPassword() {
        assertEquals("password123", account.getPassword());
    }

    @Test
    public void testSetPassword() {
        account.setPassword("newpassword456");
        assertEquals("newpassword456", account.getPassword());
    }

    @Test
    public void testGetImage() {
        assertEquals("profile.jpg", account.getImage());
    }

    @Test
    public void testSetImage() {
        account.setImage("newprofile.jpg");
        assertEquals("newprofile.jpg", account.getImage());
    }

    @Test
    public void testGetNgayTaoTaiKhoan() {
        assertEquals("2023-10-29", account.getNgayTaoTaiKhoan());
    }

    @Test
    public void testSetNgayTaoTaiKhoan() {
        account.setNgayTaoTaiKhoan("2023-11-01");
        assertEquals("2023-11-01", account.getNgayTaoTaiKhoan());
    }

    @Test
    public void testGetBlocked() {
        assertTrue(account.getBlocked());
    }

    @Test
    public void testSetBlocked() {
        account.setBlocked(false);
        assertFalse(account.getBlocked());
    }

    @Test
    public void testGetThoiGianKhoa() {
        assertEquals("2023-11-01", account.getThoiGianKhoa());
    }

    @Test
    public void testSetThoiGianKhoa() {
        account.setThoiGianKhoa("2023-12-01");
        assertEquals("2023-12-01", account.getThoiGianKhoa());
    }
}
