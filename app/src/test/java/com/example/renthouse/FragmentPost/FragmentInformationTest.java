package com.example.renthouse.FragmentPost;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import android.text.Editable;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;

import com.example.renthouse.MockEditable;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.textfield.TextInputEditText;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.zip.Inflater;


public class FragmentInformationTest {

    private FragmentInformation fragmentInformation;

    @Mock
    private RadioGroup mockRadioGroup;
    @Mock
    private TextInputEditText mockCapacityEditText;

    @Mock
    private TextInputEditText mockAreaEditText;

    @Mock
    private TextInputEditText mockPriceEditText;

    @Mock
    private TextInputEditText mockDepositEditText;
    @Mock
    private TextInputEditText mockElectricityEditText;
    @Mock
    private TextInputEditText mockWaterEditText;
    @Mock
    private TextInputEditText mockInternetEditText;
    @Mock
    private TextInputEditText mockParkingEditText;
    @Mock
    private RadioGroup mockGenderRadioGroup;
    @Mock
    private CheckBox mockCbParking;

    @Mock
    private MaterialSwitch mockSwitchFreeElectricity;

    @Mock
    private MaterialSwitch mockSwitchFreeWater;

    @Mock
    private MaterialSwitch mockSwitchFreeInternet;

    @Mock
    private MaterialSwitch mockSwitchFreeParking;

    @Mock
    private LinearLayout mockParkingInfo;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        fragmentInformation = new FragmentInformation();

        fragmentInformation.radioBtnType = mockRadioGroup;
        fragmentInformation.radioBtnGender = mockGenderRadioGroup;
        fragmentInformation.edtCapacity = mockCapacityEditText;
        fragmentInformation.edtArea = mockAreaEditText;
        fragmentInformation.edtPrice = mockPriceEditText;
        fragmentInformation.edtDeposit = mockDepositEditText;
        fragmentInformation.edtElectricityCost = mockElectricityEditText;
        fragmentInformation.edtWaterCost = mockWaterEditText;
        fragmentInformation.edtInternetCost = mockInternetEditText;
        fragmentInformation.edtParkingFee = mockParkingEditText;
        fragmentInformation.cbParking = mockCbParking;
        fragmentInformation.switchFreeElectricity = mockSwitchFreeElectricity;
        fragmentInformation.switchFreeWater = mockSwitchFreeWater;
        fragmentInformation.switchFreeInternet = mockSwitchFreeInternet;
        fragmentInformation.switchFreeParking = mockSwitchFreeParking;
        fragmentInformation.parkingInfo = mockParkingInfo;
    }

    @Test
    public void testCurrencyFormatter() {
        Editable editable = mock(Editable.class);
        when(editable.toString()).thenReturn("1000");

        String result = fragmentInformation.currencyFormatter(editable);

        assertEquals("1,000", result);
    }

    @Test
    public void testCurrencyFormatter2() {
        Editable editable = mock(Editable.class);
        when(editable.toString()).thenReturn("1.000");

        String result = fragmentInformation.currencyFormatter(editable);

        assertEquals("1,000", result);
    }

//    @Test
//    public void testGetRoomType() {
//        RadioGroup mockRadioGroup = mock(RadioGroup.class);
//        RadioButton mockRadioButton = mock(RadioButton.class);
//
//        when(mockRadioGroup.getCheckedRadioButtonId()).thenReturn(0);
//
//        when(mockRadioGroup.indexOfChild(mockRadioButton)).thenReturn(0); // Assuming index 0
//
//        when(mockRadioGroup.getChildAt(0)).thenReturn(mockRadioButton);
//        when(mockRadioButton.getText()).thenReturn("Single");
//
//        String result = fragmentInformation.getRoomType();
//
//        assertEquals("Single", result);
//    }

    @Test
    public void testGetCapacity() {
        fragmentInformation.edtCapacity.setText("10");
        when(fragmentInformation.edtCapacity.getText()).thenReturn(new MockEditable("10"));
        assertEquals(10, fragmentInformation.getCapacity());
    }

    @Test
    public void testGetArea() {
        fragmentInformation.edtArea.setText("10");
        when(fragmentInformation.edtArea.getText()).thenReturn(new MockEditable("10"));
        assertEquals(10, fragmentInformation.getArea());
    }

    @Test
    public void testGetPrice() {
        fragmentInformation.edtPrice.setText("10");
        when(fragmentInformation.edtPrice.getText()).thenReturn(new MockEditable("10"));
        assertEquals(10, fragmentInformation.getPrice());
    }

    @Test
    public void testGetDeposit() {
        fragmentInformation.edtDeposit.setText("10");
        when(fragmentInformation.edtDeposit.getText()).thenReturn(new MockEditable("10"));
        assertEquals(10, fragmentInformation.getDeposit());
    }

    @Test
    public void testGetElectricityCost() {
        fragmentInformation.edtElectricityCost.setText("10");
        when(fragmentInformation.edtElectricityCost.getText()).thenReturn(new MockEditable("10"));
        assertEquals(10, fragmentInformation.getElectricityCost());
    }

    @Test
    public void testGetWaterCost() {
        fragmentInformation.edtWaterCost.setText("10");
        when(fragmentInformation.edtWaterCost.getText()).thenReturn(new MockEditable("10"));
        assertEquals(10, fragmentInformation.getWaterCost());
    }

    @Test
    public void testGetInternetCost() {
        fragmentInformation.edtInternetCost.setText("10");
        when(fragmentInformation.edtInternetCost.getText()).thenReturn(new MockEditable("10"));
        assertEquals(10, fragmentInformation.getInternetCost());
    }

    @Test
    public void testGetParkingFee() {
        fragmentInformation.edtParkingFee.setText("10");
        when(fragmentInformation.edtParkingFee.getText()).thenReturn(new MockEditable("10"));
        assertEquals(10, fragmentInformation.getParkingFee());
    }

    @Test
    public void testHasParking_Checked() {
        CheckBox mockCheckBox = mock(CheckBox.class);
        fragmentInformation.cbParking = mockCheckBox;

        when(mockCheckBox.isChecked()).thenReturn(true);

        boolean result = fragmentInformation.hasParking();

        assertTrue(result);
    }

    @Test
    public void testGetRadioItemChecked() {
        RadioGroup mockRadioGroup = mock(RadioGroup.class);
        RadioButton mockRadioButton = mock(RadioButton.class);

        when(mockRadioGroup.getCheckedRadioButtonId()).thenReturn(0);

        when(mockRadioGroup.indexOfChild(mockRadioButton)).thenReturn(0); // Assuming index 0

        when(mockRadioGroup.getChildAt(0)).thenReturn(mockRadioButton);

        RadioButton result = fragmentInformation.getRadioItemChecked(mockRadioGroup);

        assertEquals(mockRadioButton, result);
    }

    @Test
    public void testValidateData() {
        when(fragmentInformation.edtCapacity.getText()).thenReturn(new MockEditable("1"));
        when(fragmentInformation.edtArea.getText()).thenReturn(new MockEditable("1"));
        when(fragmentInformation.edtPrice.getText()).thenReturn(new MockEditable("1"));
        when(fragmentInformation.edtDeposit.getText()).thenReturn(new MockEditable("1"));
        when(fragmentInformation.edtElectricityCost.getText()).thenReturn(new MockEditable("1"));
        when(fragmentInformation.edtWaterCost.getText()).thenReturn(new MockEditable("1"));
        when(fragmentInformation.edtInternetCost.getText()).thenReturn(new MockEditable("1"));
        when(fragmentInformation.edtParkingFee.getText()).thenReturn(new MockEditable("1"));
        when(fragmentInformation.cbParking.isChecked()).thenReturn(true);
        boolean result = fragmentInformation.validateData();

        assertTrue(result);
    }

    @Test
    public void testSetData() {
        Room mockRoom = mock(Room.class);
        when(mockRoom.getRoomType()).thenReturn("Single");
        when(mockRoom.getGender()).thenReturn("Male");
        when(mockRoom.getCapacity()).thenReturn(2);
        when(mockRoom.getArea()).thenReturn(25.0F);
        when(mockRoom.getPrice()).thenReturn(500);
        when(mockRoom.getDeposit()).thenReturn(100);
        when(mockRoom.getElectricityCost()).thenReturn(50);
        when(mockRoom.getWaterCost()).thenReturn(30);
        when(mockRoom.getInternetCost()).thenReturn(20);
        when(mockRoom.isParking()).thenReturn(true);
        when(mockRoom.getParkingFee()).thenReturn(50);

        when(fragmentInformation.edtCapacity.getText()).thenReturn(new MockEditable("2"));
        when(fragmentInformation.edtArea.getText()).thenReturn(new MockEditable("25"));
        when(fragmentInformation.edtPrice.getText()).thenReturn(new MockEditable("500"));
        when(fragmentInformation.edtDeposit.getText()).thenReturn(new MockEditable("100"));
        when(fragmentInformation.edtElectricityCost.getText()).thenReturn(new MockEditable("50"));
        when(fragmentInformation.edtWaterCost.getText()).thenReturn(new MockEditable("30"));
        when(fragmentInformation.edtInternetCost.getText()).thenReturn(new MockEditable("20"));
        when(fragmentInformation.edtParkingFee.getText()).thenReturn(new MockEditable("50"));
        when(fragmentInformation.cbParking.isChecked()).thenReturn(true);

        RadioButton mockRadioButton = mock(RadioButton.class);

        when(fragmentInformation.radioBtnType.getCheckedRadioButtonId()).thenReturn(0);
        when(fragmentInformation.radioBtnType.getChildCount()).thenReturn(1);

        when(fragmentInformation.radioBtnType.indexOfChild(mockRadioButton)).thenReturn(0); // Assuming index 0

        when(fragmentInformation.radioBtnType.getChildAt(0)).thenReturn(mockRadioButton);
        when(mockRadioButton.getText()).thenReturn("Single");


        RadioButton mockRadioButton2 = mock(RadioButton.class);

        when(fragmentInformation.radioBtnGender.getCheckedRadioButtonId()).thenReturn(0);
        when(fragmentInformation.radioBtnGender.getChildCount()).thenReturn(1);

        when(fragmentInformation.radioBtnGender.indexOfChild(mockRadioButton2)).thenReturn(0); // Assuming index 0

        when(fragmentInformation.radioBtnGender.getChildAt(0)).thenReturn(mockRadioButton2);

        when(mockRadioButton2.getText()).thenReturn("Male");

        // Gọi phương thức cần kiểm thử
        fragmentInformation.setData(mockRoom);

        // Kiểm tra các thành phần đã được set đúng giá trị từ Room hay không
        assertEquals("Single", ((RadioButton) fragmentInformation.radioBtnType.getChildAt(0)).getText().toString());
        assertEquals("Male", ((RadioButton) fragmentInformation.radioBtnGender.getChildAt(0)).getText().toString());
        assertEquals("2", fragmentInformation.edtCapacity.getText().toString());
        assertEquals("25", fragmentInformation.edtArea.getText().toString());
        assertEquals("500", fragmentInformation.edtPrice.getText().toString());
        assertEquals("100", fragmentInformation.edtDeposit.getText().toString());

        // Tiếp tục kiểm tra các thành phần khác tương tự

        // Kiểm tra switch và các EditText tương ứng
        assertFalse(fragmentInformation.switchFreeElectricity.isChecked());
        assertFalse(fragmentInformation.edtElectricityCost.isEnabled());

        assertFalse(fragmentInformation.switchFreeWater.isChecked());
        assertFalse(fragmentInformation.edtWaterCost.isEnabled());

        assertFalse(fragmentInformation.switchFreeInternet.isChecked());
        assertFalse(fragmentInformation.edtInternetCost.isEnabled());

        assertTrue(fragmentInformation.cbParking.isChecked());
        assertEquals(View.VISIBLE, fragmentInformation.parkingInfo.getVisibility());
        assertFalse(fragmentInformation.switchFreeParking.isChecked());
        assertFalse(fragmentInformation.edtParkingFee.isEnabled());
    }

//    @Test
//    public void testOnCreateView() {
//        LayoutInflater mockInflater = mock(LayoutInflater.class);
//        ViewGroup mockContainer = mock(ViewGroup.class);
//        View mockView = mock(View.class);
//        when(mockInflater.inflate(eq(R.layout.fragment_post_information), eq(mockContainer), eq(false)))
//                .thenReturn(mockView);
//        fragmentInformation.onCreateView(mockInflater, mockContainer, null);
//    }
}