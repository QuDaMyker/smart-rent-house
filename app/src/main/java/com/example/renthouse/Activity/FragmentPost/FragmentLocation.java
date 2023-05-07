package com.example.renthouse.Activity.FragmentPost;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.renthouse.OOP.City;
import com.example.renthouse.OOP.District;
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

    TextInputEditText cityEdt;
    TextInputEditText districtEdt;
    TextInputEditText wardEdt;
    City selectedCity;
    District selectedDistrict;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_post_location, container, false);
        cityEdt = (TextInputEditText) v.findViewById(R.id.cityEdt);
        districtEdt = (TextInputEditText) v.findViewById(R.id.districtEdt);
        wardEdt = (TextInputEditText) v.findViewById(R.id.wardEdt);
        String cityJson = loadJSONFromAsset("cities.json");
        String districtJson = loadJSONFromAsset("districts.json");
        String wardJson = loadJSONFromAsset("wards.json");
        Gson gson = new Gson();
        Type listCityType = new TypeToken<List<City>>(){}.getType();
        Type listDistrictType = new TypeToken<List<District>>(){}.getType();
        Type listWardType = new TypeToken<List<Ward>>(){}.getType();

        List<City> cityList = gson.fromJson(cityJson, listCityType);
        List<District> districtList = gson.fromJson(districtJson, listDistrictType);
        List<Ward> wardList = gson.fromJson(wardJson, listWardType);

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
                        Ward selectedWard = list.get(which);
                        wardEdt.setText(selectedWard.getName());
                    }
                });
                builder.show();
            }
        });
        return v;
    }

    public String loadJSONFromAsset(String filename) {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open(filename);
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
}
