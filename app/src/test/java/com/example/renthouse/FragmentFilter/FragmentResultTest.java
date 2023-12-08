//package com.example.renthouse.FragmentFilter;
//
//import com.example.renthouse.OOP.City;
//import com.example.renthouse.OOP.District;
//import com.example.renthouse.OOP.LocationTemp;
//import com.example.renthouse.OOP.ObjectSearch;
//import com.example.renthouse.OOP.Room;
//import com.example.renthouse.OOP.Ward;
//
//import junit.framework.TestCase;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mock;
//
//import java.util.ArrayList;
//
//public class FragmentResultTest extends TestCase {
//    @Mock
//    private ObjectSearch objectSearch;
//    private Room roomTest;
//    private FragmentResult fragmentResult;
//    @Before
//    public void setUp() {
//        objectSearch = new ObjectSearch();
//        objectSearch.setPath("duong");
//        fragmentResult = new FragmentResult();
//        Room room = new Room();
//        City city = new City("Ho Chi Minh", "ho-chi-minh", "thanh-pho", "Thành phố Hồ Chí Minh", "79");
//        District district = new District("Quan 1", "Quan", "quan-1", "Quận 1", "1", "quan-1", "001", "TPHCM");
//        Ward ward = new Ward("Phu Nhuan", "Phuong", "phu-nhuan", "Phường Phu Nhuan", "1", "phu-nhuan", "001", "Q1");
//
//        room.setLocation(new LocationTemp("Duong Vanh Dai", "1", city, district, ward));
//
//    }
//    @Test
//    public void newInstance() {
//    }
//
//    @Test
//    public void onCreate() {
//    }
//
//    @Test
//    public void onCreateView() {
//        assertNotNull(fragmentResult);
//    }
//
//    @Test
//    public void shimmerRun() {
//    }
//
//    @Test
//    public void loadData() {
//    }
//
//    @Test
//    public void isContainsAddress() {
//        boolean result = fragmentResult.isContainsAddress(roomTest);
//        assertTrue(result);
//    }
//
//    @Test
//    public void removeDiacritics() {
//        String inputString = "TP Hồ Chí Minh";
//        String expectedOutputString = "TP Ho Chi Minh";
//        String result = fragmentResult.removeDiacritics(inputString);
//
//        assertEquals(expectedOutputString, result);
//
//        inputString = "New York";
//        expectedOutputString = "New York";
//        result = fragmentResult.removeDiacritics(inputString);
//
//        assertEquals(expectedOutputString, result);
//    }
//
//    @Test
//    public void isPriceValid() {
//        // Lúc chưa có gì trong objectSearch
//        boolean result = fragmentResult.isPriceValid(roomTest);
//        assertFalse(result);
//
//        // Lúc
//
//        roomTest.setPrice(1000);
//        ArrayList<Long> arrayList = new ArrayList<>();
//        arrayList.add(100L);
//        arrayList.add(2000L);
//
//    }
//
//    @Test
//    public void isContainsUtilities() {
//    }
//
//    @Test
//    public void isContainTypeRoom() {
//    }
//
//    @Test
//    public void isContainAmount() {
//    }
//
//    @Test
//    public void sortItem() {
//    }
//
//    @Test
//    public void sortMaxtoMin() {
//    }
//
//    @Test
//    public void sortMintoMax() {
//    }
//
//    @Test
//    public void sortDate() {
//    }
//
//    @Test
//    public void setObjectSearch() {
//    }
//}