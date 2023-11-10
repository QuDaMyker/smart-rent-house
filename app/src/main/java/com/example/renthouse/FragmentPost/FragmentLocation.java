package com.example.renthouse.FragmentPost;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.renthouse.Activity.ActivityPost;
import com.example.renthouse.OOP.City;
import com.example.renthouse.OOP.District;
import com.example.renthouse.OOP.LocationTemp;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.OOP.Ward;
import com.example.renthouse.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FragmentLocation extends Fragment {

    public TextInputEditText cityEdt;
    public TextInputEditText districtEdt;
    public TextInputEditText wardEdt;
    public TextInputEditText edtStreet;
    public TextInputEditText edtAddress;

    public TextInputLayout cityLayoutEdt;
    public TextInputLayout districtLayoutEdt;
    public TextInputLayout wardLayoutEdt;
    public TextInputLayout edtLayoutStreet;
    public TextInputLayout edtLayoutAddress;
    public City selectedCity;
    public District selectedDistrict;
    public Ward selectedWard;
    private boolean isDataSet = false;
    List<City> cityList;
    List<District> districtList;
    List<Ward> wardList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_post_location, container, false);
        cityEdt = (TextInputEditText) v.findViewById(R.id.cityEdt);
        districtEdt = (TextInputEditText) v.findViewById(R.id.districtEdt);
        wardEdt = (TextInputEditText) v.findViewById(R.id.wardEdt);
        edtStreet = (TextInputEditText) v.findViewById(R.id.edtStreet);
        edtAddress = (TextInputEditText) v.findViewById(R.id.edtAddress);
        cityLayoutEdt = v.findViewById(R.id.cityLayoutEdt);
        districtLayoutEdt =  v.findViewById(R.id.districtLayoutEdt);
        wardLayoutEdt = v.findViewById(R.id.wardLayoutEdt);
        edtLayoutStreet = v.findViewById(R.id.edtLayoutStreet);
        edtLayoutAddress =  v.findViewById(R.id.edtLayoutAddress);
        String cityJson = loadJSONFromAsset("cities.json", inflater.getContext());
        String districtJson = loadJSONFromAsset("districts.json", inflater.getContext());
        String wardJson = loadJSONFromAsset("wards.json", inflater.getContext());
        Gson gson = new Gson();
        Type listCityType = new TypeToken<List<City>>(){}.getType();
        Type listDistrictType = new TypeToken<List<District>>(){}.getType();
        Type listWardType = new TypeToken<List<Ward>>(){}.getType();
        cityList = gson.fromJson(cityJson, listCityType);
        districtList = gson.fromJson(districtJson, listDistrictType);
        wardList = gson.fromJson(wardJson, listWardType);
        cityEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> nameList = new ArrayList<>();
                for (City city : cityList) {
                    nameList.add(city.getName());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, nameList);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Thành phố");
                builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedCity = cityList.get(which);
                        cityEdt.setText(selectedCity.getName());
                        districtEdt.setText("");
                        wardEdt.setText("");
                        selectedDistrict = null;
                    }
                });
                builder.show();
            }
        });
        districtEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> nameList = new ArrayList<>();
                List<District> list = new ArrayList<>();
                if(selectedCity != null){
                    for (District district : districtList) {
                        if(district.getParent_code().equals(selectedCity.getCode())){
                            nameList.add(district.getName());
                            list.add(district);
                        }
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, nameList);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Quận / Huyện");
                builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedDistrict = list.get(which);
                        districtEdt.setText(selectedDistrict.getName());
                        wardEdt.setText("");
                    }
                });
                builder.show();
            }
        });
        wardEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> nameList = new ArrayList<>();
                List<Ward> list = new ArrayList<>();
                if(selectedCity != null && selectedDistrict != null){
                    for (Ward ward : wardList) {
                        if(ward.getParent_code().equals(selectedDistrict.getCode())){
                            nameList.add(ward.getName());
                            list.add(ward);
                        }
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, nameList);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Phường / Xã");
                builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedWard = list.get(which);
                        wardEdt.setText(selectedWard.getName());
                    }
                });
                builder.show();
            }
        });
//        cityEdt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                cityLayoutEdt.setError(null);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//        districtEdt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                districtLayoutEdt.setError(null);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//        wardEdt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                wardLayoutEdt.setError(null);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//        edtStreet.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                edtLayoutStreet.setError(null);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//        edtAddress.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                edtLayoutAddress.setError(null);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
        ActivityPost activityPost = (ActivityPost) getActivity();
        if(!isDataSet && activityPost.roomToEdit != null){
            setData(activityPost.roomToEdit);
            isDataSet = true;
        }
        return v;
    }

    public String loadJSONFromAsset(String filename, Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public LocationTemp getLocation(){
        return new LocationTemp(edtStreet.getText().toString(), edtAddress.getText().toString(), selectedCity, selectedDistrict, selectedWard);
    }

    public void setData(Room room){
        selectedCity = room.getLocation().getCity();
        cityEdt.setText(selectedCity.getName());

        selectedDistrict = room.getLocation().getDistrict();
        districtEdt.setText(selectedDistrict.getName());

        selectedWard = room.getLocation().getWard();
        wardEdt.setText(selectedWard.getName());

        edtStreet.setText(room.getLocation().getStreet());
        edtAddress.setText(room.getLocation().getAddress());
    }

    public boolean validateData(){
        boolean flag = true;
        if(TextUtils.isEmpty(cityEdt.getText().toString().trim())){
            cityLayoutEdt.setError("Vui lòng chọn Thành phố");
            flag = false;
        }
        if(TextUtils.isEmpty(districtEdt.getText().toString().trim())){
            districtLayoutEdt.setError("Vui lòng chọn Quận / Huyện");
            flag = false;
        }
        if(TextUtils.isEmpty(wardEdt.getText().toString().trim())){
            wardLayoutEdt.setError("Vui lòng chọn Phường / Xã");
            flag = false;
        }
        if(TextUtils.isEmpty(edtStreet.getText().toString().trim())){
            edtLayoutStreet.setError("Vui lòng nhập tên đường");
            flag = false;
        }
        if(TextUtils.isEmpty(edtAddress.getText().toString().trim())){
            edtLayoutAddress.setError("Vui lòng nhập số nhà");
            flag = false;
        }
        return flag;
    }
}
