package com.example.renthouse.FragmentPost;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import android.text.Editable;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputEditText;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

// Import các thư viện cần thiết

public class FragmentInformationTest {

    private FragmentInformation fragmentInformation;

    @Mock
    private RadioGroup mockRadioGroup;
    @Mock
    private TextInputEditText mockCapacityEditText;
    @Mock
    private RadioGroup mockGenderRadioGroup;
    // ... (Mock các đối tượng khác cần thiết)

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        fragmentInformation = new FragmentInformation();

        fragmentInformation.radioBtnType = mockRadioGroup;
        fragmentInformation.edtCapacity = mockCapacityEditText;
        fragmentInformation.radioBtnGender = mockGenderRadioGroup;
        // ... (Inject các mock object khác)
    }

    @Test
    public void testCurrencyFormatter() {
        Editable editable = mock(Editable.class);
        when(editable.toString()).thenReturn("1000");

        String result = fragmentInformation.currencyFormatter(editable);

        assertEquals("1,000", result);
    }

    @Test
    public void testGetRoomType() {
        RadioButton mockRadioButton = mock(RadioButton.class);
        when(mockRadioButton.getText()).thenReturn("Single");
        when(mockRadioGroup.getCheckedRadioButtonId()).thenReturn(mockRadioButton.getId());

        String result = fragmentInformation.getRoomType();

        assertEquals("Single", result);
    }

    // Viết các test case cho các phương thức khác tương tự như trên
}
