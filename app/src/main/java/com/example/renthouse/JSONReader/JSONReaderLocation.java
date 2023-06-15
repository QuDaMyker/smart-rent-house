package com.example.renthouse.JSONReader;

import android.content.Context;

import com.example.renthouse.Test.Location;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class JSONReaderLocation {
    private List<Location> locationList;
    Context context;
    public JSONReaderLocation(Context context) {
        this.context = context;
        this.locationList = new ArrayList<Location>();
    }

    public List<Location> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void readDatabaseLocation(String currentLocation) {
        String jsonString;
        try {
            InputStream inputStream = context.getAssets().open("wards.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            jsonString = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(jsonString);
            for(int i = 0; i < jsonArray.length();i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                String ward = obj.getString("path_with_type");

                Location location = new Location(ward);
                String normalizedElement = Normalizer.normalize(ward, Normalizer.Form.NFC);
                String normalizedSearchString = Normalizer.normalize(currentLocation, Normalizer.Form.NFC);
                if (normalizedElement.contains(normalizedSearchString)) {
                    locationList.add(location);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
