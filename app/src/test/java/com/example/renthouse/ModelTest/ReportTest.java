package com.example.renthouse.ModelTest;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.renthouse.OOP.AccountClass;
import com.example.renthouse.OOP.City;
import com.example.renthouse.OOP.Reports;

public class ReportTest {

    private Reports reports;

    @Before
    public void setUp() {
        AccountClass account = new AccountClass("John Doe", "john@example.com", "123456789", "password123", "profile.jpg", "2023-10-30", false, "");
        reports = new Reports(account, "user123", "Report Title", "Report Content", "2023-10-30");
    }

    @Test
    public void testConstructor() {
        Reports reports = new Reports();
        assertNotNull(reports);
    }

    @Test
    public void testGetAccount() {
        assertNotNull(reports.getAccount());
        assertEquals("John Doe", reports.getAccount().getFullname());
    }

    @Test
    public void testSetAccount() {
        AccountClass newAccount = new AccountClass("Jane Doe", "jane@example.com", "987654321", "newpassword123", "newprofile.jpg", "2023-10-31", true, "2023-11-01");
        reports.setAccount(newAccount);

        assertNotNull(reports.getAccount());
        assertEquals("Jane Doe", reports.getAccount().getFullname());
    }

    @Test
    public void testGetIdUser() {
        assertEquals("user123", reports.getIdUser());
    }

    @Test
    public void testSetIdUser() {
        reports.setIdUser("newUser456");
        assertEquals("newUser456", reports.getIdUser());
    }

    @Test
    public void testGetTitle() {
        assertEquals("Report Title", reports.getTitle());
    }

    @Test
    public void testSetTitle() {
        reports.setTitle("New Report Title");
        assertEquals("New Report Title", reports.getTitle());
    }

    @Test
    public void testGetContent() {
        assertEquals("Report Content", reports.getContent());
    }

    @Test
    public void testSetContent() {
        reports.setContent("New Report Content");
        assertEquals("New Report Content", reports.getContent());
    }

    @Test
    public void testGetNgayBaoCao() {
        assertEquals("2023-10-30", reports.getNgayBaoCao());
    }

    @Test
    public void testSetNgayBaoCao() {
        reports.setNgayBaoCao("2023-10-31");
        assertEquals("2023-10-31", reports.getNgayBaoCao());
    }
}
