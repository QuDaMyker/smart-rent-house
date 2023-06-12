package com.example.renthouse.Activity;

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
import android.widget.Toast;

import com.example.renthouse.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.android.PolyUtil;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.TravelMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private final int REQUEST_CODE = 299;
    private boolean locationPermissionGranted = false;
    private GoogleMap mMap;
    private MarkerOptions place1, place2;
    private Polyline currentPolyline;
    private GeoApiContext geoApiContext;

    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //ask permission
        }
        else{
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        double curLatitude = location.getLatitude();
                        double curLongitude = location.getLongitude();
                        BitmapDescriptor markerIcon = BitmapDescriptorFactory.fromResource(R.drawable.marker_person);

                        place1 = new MarkerOptions().position(new LatLng(curLatitude, curLongitude)).icon(markerIcon);


                        mapFragment.getMapAsync(MapsActivity.this);
                    }
                }
            });
        }

        // Initialize GeoApiContext
        geoApiContext = new GeoApiContext.Builder()
                .apiKey("AIzaSyDQjIt9yAJVT7qEIJ7-epCSAy7Wn0PmGgQ")
                .build();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add markers for location 1 and location 2
        mMap.addMarker(place1);

        List<String> locationStrings = new ArrayList<>();
        locationStrings.add("201 Đặng Văn Bi, Bình Thọ, Thủ Đức, Thành phố Hồ Chí Minh, Vietnam");
        locationStrings.add("190 Đặng Văn Bi, Bình Thọ, Thủ Đức, Thành phố Hồ Chí Minh, Vietnam");

        for (String locationString : locationStrings) {
            // Use Geocoder to convert the location string to coordinates
            Geocoder geocoder = new Geocoder(MapsActivity.this);
            try {
                List<Address> addresses = geocoder.getFromLocationName(locationString, 1);
                if (addresses != null && addresses.size() > 0) {
                    Address address = addresses.get(0);
                    double latitude = address.getLatitude();
                    double longitude = address.getLongitude();
                    LatLng locationLatLng = new LatLng(latitude, longitude);

                    // Calculate the distance between current location and geocoded location
                    float[] results = new float[1];
                    Location.distanceBetween(place1.getPosition().latitude, place1.getPosition().longitude, latitude, longitude, results);
                    float distance = results[0];
                    BitmapDescriptor markerIcon = BitmapDescriptorFactory.fromResource(R.drawable.marker_home);

                    // Define the maximum distance for a location to be considered "nearby"
                    float maxDistance = 1000; // in meters

                    // Check if the location is within the desired range
                    if (distance <= maxDistance) {
                        // Create a marker for the nearby location
                        MarkerOptions markerOptions = new MarkerOptions()
                                .position(locationLatLng)
                                .title(locationString)
                                .icon(markerIcon);

                        // Add the marker to the map
                        mMap.addMarker(markerOptions);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Move camera to the first location
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place1.getPosition(), 15f));

    }

}
