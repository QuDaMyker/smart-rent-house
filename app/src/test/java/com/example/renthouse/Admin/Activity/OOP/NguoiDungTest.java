package com.example.renthouse.Admin.Activity.OOP;

import com.example.renthouse.Admin.OOP.NguoiDung;
import com.example.renthouse.OOP.AccountClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class NguoiDungTest {

    @Test
    public void testConstructorAndGettersSetters() {
        String key = "testKey";
        AccountClass account = new AccountClass("Le Bao Nhu", "nhu@example.com", "0987654321", "password123", "profile.jpg", "2023-10-29", true, "2023-11-01");
        int soLuongPhong = 5;

        NguoiDung nguoiDung = new NguoiDung(key, account, soLuongPhong);

        assertEquals(key, nguoiDung.getKey());
        assertEquals(account, nguoiDung.getAccountClass());
        assertEquals(soLuongPhong, nguoiDung.getSoLuongPhong());

        String newKey = "newTestKey";
        nguoiDung.setKey(newKey);
        assertEquals(newKey, nguoiDung.getKey());

        AccountClass newAccountClass = new AccountClass("Le Bao Nhu", "nhu@example.com", "0987654321", "password123", "profile.jpg", "2023-10-29", true, "2023-11-01");
        nguoiDung.setAccountClass(newAccountClass);
        assertEquals(newAccountClass, nguoiDung.getAccountClass());

        int newSoLuongPhong = 10;
        nguoiDung.setSoLuongPhong(newSoLuongPhong);
        assertEquals(newSoLuongPhong, nguoiDung.getSoLuongPhong());
    }

    @Test
    public void testEmptyConstructor() {
        NguoiDung nguoiDung = new NguoiDung();

        assertNotNull(nguoiDung);
    }
}
