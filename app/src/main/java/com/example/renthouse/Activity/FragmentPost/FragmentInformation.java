package com.example.renthouse.Activity.FragmentPost;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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
import com.google.android.material.textfield.TextInputLayout;

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

    TextInputLayout edtLayoutCapacity;
    TextInputLayout edtLayoutArea;
    TextInputLayout edtLayoutPrice;
    TextInputLayout edtLayoutDeposit;
    TextInputLayout edtLayoutElectricityCost;
    TextInputLayout edtLayoutWaterCost;
    TextInputLayout edtLayoutInternetCost;
    TextInputLayout edtLayoutParkingFee;


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

        edtLayoutCapacity = v.findViewById(R.id.edtLayoutCapacity);
        edtLayoutArea = v.findViewById(R.id.edtLayoutArea);
        edtLayoutPrice = v.findViewById(R.id.edtLayoutPrice);
        edtLayoutDeposit = v.findViewById(R.id.edtLayoutDeposit);
        edtLayoutElectricityCost = v.findViewById(R.id.edtLayoutElectricityCost);
        edtLayoutWaterCost = v.findViewById(R.id.edtLayoutWaterCost);
        edtLayoutInternetCost = v.findViewById(R.id.edtLayoutInternetCost);
        edtLayoutParkingFee = v.findViewById(R.id.edtLayoutParkingFee);

        edtCapacity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                edtLayoutCapacity.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                edtLayoutArea.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                edtLayoutPrice.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtDeposit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                edtLayoutDeposit.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtElectricityCost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                edtLayoutElectricityCost.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtWaterCost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                edtLayoutWaterCost.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtInternetCost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                edtLayoutInternetCost.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtParkingFee.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                edtLayoutParkingFee.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        cbParking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true){
                    parkingInfo.setVisibility(View.VISIBLE);
                    edtParkingFee.setText("");
                }
                else{
                    parkingInfo.setVisibility(View.GONE);
                }
            }
        });

        switchFreeElectricity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    edtElectricityCost.setEnabled(false);
                    edtElectricityCost.setText("0");
                }
                else{
                    edtElectricityCost.setEnabled(true);
                    edtElectricityCost.setText("");
                }
            }
        });
        switchFreeWater.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    edtWaterCost.setEnabled(false);
                    edtWaterCost.setText("0");
                }
                else{
                    edtWaterCost.setEnabled(true);
                    edtWaterCost.setText("");
                }
            }
        });
        switchFreeInternet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    edtInternetCost.setEnabled(false);
                    edtInternetCost.setText("0");
                }
                else{
                    edtInternetCost.setEnabled(true);
                    edtInternetCost.setText("");
                }
            }
        });
        switchFreeParking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    edtParkingFee.setEnabled(false);
                    edtParkingFee.setText("0");
                }
                else{
                    edtParkingFee.setEnabled(true);
                    edtParkingFee.setText("");
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


    public boolean validateData(){
        boolean flag = true;
        if(TextUtils.isEmpty(edtCapacity.getText().toString().trim())){
            edtLayoutCapacity.setError("Vui lòng nhập số người/phòng");
            flag = false;
        }
        if(TextUtils.isEmpty(edtArea.getText().toString().trim())){
            edtLayoutArea.setError("Vui lòng nhập diện tích phòng");
            flag = false;
        }
        if(TextUtils.isEmpty(edtPrice.getText().toString().trim())){
            edtLayoutPrice.setError("Vui lòng nhập giá phòng");
            flag = false;
        }
        if(TextUtils.isEmpty(edtDeposit.getText().toString().trim())){
            edtLayoutDeposit.setError("Vui lòng nhập đặt cọc");
            flag = false;
        }
        if(TextUtils.isEmpty(edtElectricityCost.getText().toString().trim())){
            edtLayoutElectricityCost.setError("Vui lòng nhập tiền điện");
            flag = false;
        }
        if(TextUtils.isEmpty(edtWaterCost.getText().toString().trim())){
            edtLayoutWaterCost.setError("Vui lòng nhập số tiền nước");
            flag = false;
        }
        if(TextUtils.isEmpty(edtInternetCost.getText().toString().trim())){
            edtLayoutInternetCost.setError("Vui lòng nhập tiền internet");
            flag = false;
        }
        if(hasParking()){
            if(TextUtils.isEmpty(edtParkingFee.getText().toString().trim())){
                edtLayoutParkingFee.setError("Vui lòng nhập phí gửi xe");
                flag = false;
            }
        }
        else {
            edtParkingFee.setText("0");
            edtLayoutParkingFee.setError(null);
        }

        return flag;
    }
}
