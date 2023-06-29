package com.example.renthouse.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.renthouse.Adapter.UniAdapter;
import com.example.renthouse.OOP.City;
import com.example.renthouse.OOP.University;
import com.example.renthouse.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FindByMapsActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 101;
    private RecyclerView recyclerView;
    private UniAdapter uniAdapter;
    private SearchView searchView;
    private LinearLayout introView;
    private List<University> itemList = new ArrayList<>();
    private Button locationBtn;
    private MarkerOptions currPlace;
    ImageButton btn_Back;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_by_maps);
        MapsInitializer.initialize(getApplicationContext());
        introView = findViewById(R.id.introView);
        locationBtn = findViewById(R.id.locationBtn);
        btn_Back = findViewById(R.id.btn_Back);
        String uniJson = loadJSONFromAsset("universities.json");
        Gson gson = new Gson();
        Type listUniType = new TypeToken<List<University>>(){}.getType();
        itemList = gson.fromJson(uniJson, listUniType);

        recyclerView = findViewById(R.id.recycleViewUniSearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        uniAdapter = new UniAdapter(itemList);
        recyclerView.setAdapter(uniAdapter);
        uniAdapter.setIntroView(introView);


        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                uniAdapter.filter(newText);
                return true;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchView.setQuery("", false);
                return false;
            }
        });

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLastLocation();
            }
        });



        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        uniAdapter.setOnItemClickListener(new UniAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, University u) {
                String address = u.getAddress();
                currPlace = convertAddressToMarkerOptions(address);
                Intent intent = new Intent(FindByMapsActivity.this, MapsActivity.class);
                intent.putExtra("currPlace", currPlace);
                startActivity(intent);
            }
        });
    }

    private String loadJSONFromAsset(String filename) {
        String json = null;
        try {
            InputStream is = this.getAssets().open(filename);
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

    private MarkerOptions convertAddressToMarkerOptions(String address) {
        Geocoder geocoder = new Geocoder(this);

        try {
            List<Address> addresses = geocoder.getFromLocationName(address, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address firstAddress = addresses.get(0);

                double latitude = firstAddress.getLatitude();
                double longitude = firstAddress.getLongitude();
                BitmapDescriptor markerIcon = BitmapDescriptorFactory.fromResource(R.drawable.marker_you);
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(new LatLng(latitude, longitude))
                        .title(address)
                        .icon(markerIcon);

                return markerOptions;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null; // Trả về null nếu không tìm thấy tọa độ
    }

    void getLastLocation(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(FindByMapsActivity.this);
        if (ActivityCompat.checkSelfPermission(FindByMapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(FindByMapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(FindByMapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    double curLatitude = location.getLatitude();
                    double curLongitude = location.getLongitude();
                    BitmapDescriptor markerIcon = BitmapDescriptorFactory.fromResource(R.drawable.marker_you);

                    currPlace = new MarkerOptions().position(new LatLng(curLatitude, curLongitude)).icon(markerIcon);
                    Intent intent = new Intent(FindByMapsActivity.this, MapsActivity.class);
                    intent.putExtra("currPlace", currPlace);
                    startActivity(intent);
                }
            }
        });
    }
}