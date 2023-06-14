package com.example.renthouse.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.android.PolyUtil;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.TravelMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final int AUTOCOMPLETE_REQUEST_CODE = 71;
    private final int REQUEST_CODE = 299;
    private boolean locationPermissionGranted = false;
    private GoogleMap mMap;
    private List<Room> roomList = new ArrayList<>();
    private MarkerOptions currPlace;
    private Polyline currentPolyline;
    private GeoApiContext geoApiContext;
    MaterialButton locationButton;
    ImageButton btn_Back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        btn_Back = findViewById(R.id.btn_Back);
        currPlace = getIntent().getParcelableExtra("currPlace");


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        roomList = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Rooms");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Room room = dataSnapshot.getValue(Room.class);
                    roomList.add(room);
                }

                mapFragment.getMapAsync(MapsActivity.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MapsActivity.this, "Lấy danh sách phòng thất bại!", Toast.LENGTH_SHORT).show();
            }
        });



        // Initialize GeoApiContext
        geoApiContext = new GeoApiContext.Builder()
                .apiKey("AIzaSyAgsQYMfzZ7UlCUjlbw0orxxbiLfTsD9NI")
                .build();

        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        locationButton = findViewById(R.id.locationButton);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currPlace.getPosition(), 17f));
            }
        });
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                Room room = (Room) marker.getTag();

                if (room != null) {
                    //TODO: chuyển đến màn hình details
                }
                else{
                    Log.e("E", "Không tồn tại phòng trọ");
                }

                return true;
            }
        });

        mMap.addMarker(currPlace);

        for (Room room : roomList) {
            // Use Geocoder to convert the location string to coordinates
            Geocoder geocoder = new Geocoder(MapsActivity.this);
            try {
                List<Address> addresses = geocoder.getFromLocationName(room.getLocation().LocationToString(), 1);
                if (addresses != null && addresses.size() > 0) {
                    Address address = addresses.get(0);
                    double latitude = address.getLatitude();
                    double longitude = address.getLongitude();
                    LatLng locationLatLng = new LatLng(latitude, longitude);

                    // Calculate the distance between current location and geocoded location
                    float[] results = new float[1];
                    Location.distanceBetween(currPlace.getPosition().latitude, currPlace.getPosition().longitude, latitude, longitude, results);
                    float distance = results[0];
                    BitmapDescriptor markerIcon = BitmapDescriptorFactory.fromResource(R.drawable.marker_home);

                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(locationLatLng)
                            .title(room.getLocation().LocationToString())
                            .icon(markerIcon);

                    // Add the marker to the map
                    Marker marker= mMap.addMarker(markerOptions);
                    marker.setTag(room);

// Define the maximum distance for a location to be considered "nearby"
//                    float maxDistance = 1000; // in meters
//                    // Check if the location is within the desired range
//                    if (distance <= maxDistance) {
//                        // Create a marker for the nearby location
//                        MarkerOptions markerOptions = new MarkerOptions()
//                                .position(locationLatLng)
//                                .title(room.getLocation().LocationToString())
//                                .icon(markerIcon);
//
//                        // Add the marker to the map
//                        mMap.addMarker(markerOptions);
//                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Move camera to the current location
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currPlace.getPosition(), 17f));

    }
}
