package com.example.renthouse.FragmentPost;

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

import com.example.renthouse.Activity.ActivityPost;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class FragmentInformation extends Fragment {

    public RadioGroup radioBtnType;
    public TextInputEditText edtCapacity;
    RadioGroup radioBtnGender;
    public TextInputEditText edtArea;
    public TextInputEditText edtPrice;
    public TextInputEditText edtDeposit;
    public MaterialSwitch switchFreeElectricity;
    public TextInputEditText edtElectricityCost;
    public MaterialSwitch switchFreeWater;
    public TextInputEditText edtWaterCost;
    public MaterialSwitch switchFreeInternet;
    public TextInputEditText edtInternetCost;
    public CheckBox cbParking;
    LinearLayout parkingInfo;
    public MaterialSwitch switchFreeParking;
    public TextInputEditText edtParkingFee;

    public TextInputLayout edtLayoutCapacity;
    public TextInputLayout edtLayoutArea;
    public TextInputLayout edtLayoutPrice;
    public TextInputLayout edtLayoutDeposit;
    TextInputLayout edtLayoutElectricityCost;
    TextInputLayout edtLayoutWaterCost;
    TextInputLayout edtLayoutInternetCost;
    TextInputLayout edtLayoutParkingFee;
    private boolean isDataSet = false;

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

//        edtCapacity.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                edtLayoutCapacity.setError(null);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//        edtArea.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                edtLayoutArea.setError(null);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//        edtPrice.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                edtLayoutPrice.setError(null);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                String processed = currencyFormatter(editable);
//                edtPrice.removeTextChangedListener(this);
//                edtPrice.setText(processed);
//                try {
//                    edtPrice.setSelection(processed.length());
//                } catch (Exception e) {
//
//                }
//                edtPrice.addTextChangedListener(this);
//            }
//        });
//        edtDeposit.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                edtLayoutDeposit.setError(null);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                String processed = currencyFormatter(editable);
//                edtDeposit.removeTextChangedListener(this);
//                edtDeposit.setText(processed);
//                try {
//                    edtDeposit.setSelection(processed.length());
//                } catch (Exception e) {
//
//                }
//                edtDeposit.addTextChangedListener(this);
//            }
//        });
//        edtElectricityCost.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                edtLayoutElectricityCost.setError(null);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                String processed = currencyFormatter(editable);
//                edtElectricityCost.removeTextChangedListener(this);
//                edtElectricityCost.setText(processed);
//                try {
//                    edtElectricityCost.setSelection(processed.length());
//                } catch (Exception e) {
//
//                }
//                edtElectricityCost.addTextChangedListener(this);
//            }
//        });
//        edtWaterCost.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                edtLayoutWaterCost.setError(null);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                String processed = currencyFormatter(editable);
//                edtWaterCost.removeTextChangedListener(this);
//                edtWaterCost.setText(processed);
//                try {
//                    edtWaterCost.setSelection(processed.length());
//                } catch (Exception e) {
//
//                }
//                edtWaterCost.addTextChangedListener(this);
//            }
//        });
//        edtInternetCost.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                edtLayoutInternetCost.setError(null);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                String processed = currencyFormatter(editable);
//                edtInternetCost.removeTextChangedListener(this);
//                edtInternetCost.setText(processed);
//                try {
//                    edtInternetCost.setSelection(processed.length());
//                } catch (Exception e) {
//
//                }
//                edtInternetCost.addTextChangedListener(this);
//            }
//        });
//        edtParkingFee.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                edtLayoutParkingFee.setError(null);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                String processed = currencyFormatter(editable);
//                edtParkingFee.removeTextChangedListener(this);
//                edtParkingFee.setText(processed);
//                try {
//                    edtParkingFee.setSelection(processed.length());
//                } catch (Exception e) {
//
//                }
//                edtParkingFee.addTextChangedListener(this);
//            }
//        });

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
        ActivityPost activityPost = (ActivityPost) getActivity();
        if(!isDataSet && activityPost.roomToEdit != null){
            setData(activityPost.roomToEdit);
            isDataSet = true;
        }
        return v;
    }
    public String currencyFormatter(Editable editable) {
        String initial = editable.toString();
        if (initial.isEmpty()) return "";
        String cleanString;
        if (initial.contains(".")){
            cleanString = initial.replace(".", "");
        }
        else{
            cleanString = initial.replace(",", "");
        }
        NumberFormat formatter = new DecimalFormat("#,###");
        double myNumber = new Double(cleanString);
        return formatter.format(myNumber);
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
        return Integer.parseInt(edtArea.getText().toString().replaceAll("[^\\d]", ""));
    }

    public int getPrice(){
        return Integer.parseInt(edtPrice.getText().toString().replaceAll("[^\\d]", ""));
    }

    public int getDeposit(){
        return Integer.parseInt(edtDeposit.getText().toString().replaceAll("[^\\d]", ""));
    }

    public int getElectricityCost(){
        return Integer.parseInt(edtElectricityCost.getText().toString().replaceAll("[^\\d]", ""));
    }

    public int getWaterCost(){
        return Integer.parseInt(edtWaterCost.getText().toString().replaceAll("[^\\d]", ""));
    }

    public int getInternetCost(){
        return Integer.parseInt(edtInternetCost.getText().toString().replaceAll("[^\\d]", ""));
    }

    public int getParkingFee(){
        return Integer.parseInt(edtParkingFee.getText().toString().replaceAll("[^\\d]", ""));
    }

    public boolean hasParking(){
        return cbParking.isChecked();
    }

    public void setData(Room room){
        for (int i = 0; i < radioBtnType.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) radioBtnType.getChildAt(i);
            if (radioButton.getText().toString().equals(room.getRoomType())) {
                radioBtnType.check(radioButton.getId());
                break;
            }
        }

        for (int i = 0; i < radioBtnGender.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) radioBtnGender.getChildAt(i);
            if (radioButton.getText().toString().equals(room.getGender())) {
                radioBtnGender.check(radioButton.getId());
                break;
            }
        }

        edtCapacity.setText(String.valueOf(room.getCapacity()));
        edtArea.setText(String.valueOf(Math.round(room.getArea())));
        edtPrice.setText(String.valueOf(room.getPrice()));
        edtDeposit.setText(String.valueOf(room.getDeposit()));

        if(room.getElectricityCost() == 0){
            switchFreeElectricity.setChecked(true);
            edtElectricityCost.setEnabled(false);
            edtElectricityCost.setText("0");
        }
        else{
            switchFreeElectricity.setChecked(false);
            edtElectricityCost.setEnabled(true);
            edtElectricityCost.setText(String.valueOf(room.getElectricityCost()));
        }

        if(room.getWaterCost() == 0){
            switchFreeWater.setChecked(true);
            edtWaterCost.setEnabled(false);
            edtWaterCost.setText("0");
        }
        else{
            switchFreeWater.setChecked(false);
            edtWaterCost.setEnabled(true);
            edtWaterCost.setText(String.valueOf(room.getWaterCost()));
        }

        if(room.getInternetCost() == 0){
            switchFreeInternet.setChecked(true);
            edtInternetCost.setEnabled(false);
            edtInternetCost.setText("0");
        }
        else{
            switchFreeInternet.setChecked(false);
            edtInternetCost.setEnabled(true);
            edtInternetCost.setText(String.valueOf(room.getInternetCost()));
        }

        if (room.isParking()){
            parkingInfo.setVisibility(View.VISIBLE);
            cbParking.setChecked(true);
            if(room.getParkingFee() == 0){
                switchFreeParking.setChecked(true);
                edtParkingFee.setEnabled(false);
                edtParkingFee.setText("0");
            }
            else{
                switchFreeParking.setChecked(false);
                edtParkingFee.setEnabled(true);
                edtParkingFee.setText(String.valueOf(room.getParkingFee()));
            }
        }
        else {
            cbParking.setChecked(false);
            parkingInfo.setVisibility(View.GONE);
        }
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
