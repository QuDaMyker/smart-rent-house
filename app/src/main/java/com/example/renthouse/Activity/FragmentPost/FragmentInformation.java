package com.example.renthouse.Activity.FragmentPost;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.renthouse.R;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

public class FragmentInformation extends Fragment {

    RadioGroup radioBtnType;
    TextInputEditText edtCapacity;
    RadioGroup radioBtnGender;
    TextInputEditText edtArea;
    TextInputEditText edtPrice;
    TextInputEditText edtDeposit;
    MaterialSwitch switchFreeElectricity;
    TextInputEditText edtElectricityCost;
    MaterialSwitch switchFreeWater;
    TextInputEditText edtWaterCost;
    MaterialSwitch switchFreeInternet;
    TextInputEditText edtInternetCost;
    CheckBox cbParking;
    LinearLayout parkingInfo;
    MaterialSwitch switchFreeParking;
    TextInputEditText edtParkingFee;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_post_information, container, false);
        radioBtnType = v.findViewById(R.id.radioBtnType);
        edtCapacity = v.findViewById(R.id.edtCapacity);
        radioBtnGender = v.findViewById(R.id.radioBtnGender);
        edtArea = v.findViewById(R.id.edtArea);
        edtPrice = v.findViewById(R.id.edtPrice);
        edtDeposit = v.findViewById(R.id.edtDeposit);
        switchFreeElectricity = v.findViewById(R.id.switchFreeElectricity);
        edtElectricityCost = v.findViewById(R.id.edtElectricityCost);
        switchFreeWater = v.findViewById(R.id.switchFreeWater);
        edtWaterCost = v.findViewById(R.id.edtWaterCost);
        switchFreeInternet = v.findViewById(R.id.switchFreeInternet);
        edtInternetCost = v.findViewById(R.id.edtInternetCost);
        cbParking = v.findViewById(R.id.cbParking);
        parkingInfo = v.findViewById(R.id.parkingInfo);
        switchFreeParking = v.findViewById(R.id.switchFreeParking);
        edtParkingFee = v.findViewById(R.id.edtParkingFee);

        cbParking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true){
                    parkingInfo.setVisibility(View.VISIBLE);
                }
                else{
                    parkingInfo.setVisibility(View.GONE);
                }
            }
        });
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public String getRoomType(){
        RadioButton typeBtnRadio = getRadioItemChecked(radioBtnType);
        return typeBtnRadio.getText().toString();
    }

    public int getCapacity(){
        return Integer.parseInt(edtCapacity.getText().toString());
    }

    public String getGender(){
        RadioButton genderBtnRadio = getRadioItemChecked(radioBtnGender);
        return genderBtnRadio.getText().toString();
    }

    public int getArea(){
        return Integer.parseInt(edtArea.getText().toString());
    }

    public int getPrice(){
        return Integer.parseInt(edtPrice.getText().toString());
    }

    public int getDeposit(){
        return Integer.parseInt(edtDeposit.getText().toString());
    }

    public int getElectricityCost(){
        return Integer.parseInt(edtElectricityCost.getText().toString());
    }

    public int getWaterCost(){
        return Integer.parseInt(edtWaterCost.getText().toString());
    }

    public int getInternetCost(){
        return Integer.parseInt(edtInternetCost.getText().toString());
    }

    public int getParkingFee(){
        return Integer.parseInt(edtParkingFee.getText().toString());
    }

    public boolean isFreeElectricity(){
        return switchFreeElectricity.isChecked();
    }
    public boolean isFreeWater(){
        return switchFreeWater.isChecked();
    }

    public boolean isFreeInternet(){
        return switchFreeInternet.isChecked();
    }

    public boolean isFreeParking(){
        return switchFreeParking.isChecked();
    }

    public boolean hasParking(){
        return cbParking.isChecked();
    }


    public RadioButton getRadioItemChecked(RadioGroup radioButtonGroup){
        int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
        View radioButton = radioButtonGroup.findViewById(radioButtonID);
        int idx = radioButtonGroup.indexOfChild(radioButton);
        RadioButton r = (RadioButton) radioButtonGroup.getChildAt(idx);
        return r;
    }
}
