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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
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
    private LatLng location = new LatLng(10.823099, 106.629662);
    private LatLng currentLocation = new LatLng(21.027763, 105.834160);
    private MarkerOptions place1, place2;
    private Polyline currentPolyline;
    private GeoApiContext geoApiContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Initialize GeoApiContext
        geoApiContext = new GeoApiContext.Builder()
                .apiKey("AIzaSyDQjIt9yAJVT7qEIJ7-epCSAy7Wn0PmGgQ")
                .build();

        // Set up the locations
        place1 = new MarkerOptions().position(new LatLng(21.027763, 105.834160)).title("Location 1");
        place2 = new MarkerOptions().position(new LatLng(10.823099, 106.629662)).title("Location 2");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add markers for location 1 and location 2
        mMap.addMarker(place1);
        mMap.addMarker(place2);

        // Move camera to the first location
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place1.getPosition(), 15f));

        // Fetch and draw the route between the two locations
        fetchRoute(place1.getPosition(), place2.getPosition());
    }

    private void fetchRoute(LatLng origin, LatLng destination) {
        DirectionsApi.newRequest(geoApiContext)
                .mode(TravelMode.DRIVING)
                .origin(new com.google.maps.model.LatLng(origin.latitude, origin.longitude))
                .destination(new com.google.maps.model.LatLng(destination.latitude, destination.longitude))
                .setCallback(new PendingResult.Callback<DirectionsResult>() {
                    @Override
                    public void onResult(DirectionsResult result) {
                        if (result.routes != null && result.routes.length > 0) {
                            DirectionsRoute route = result.routes[0];
                            List<LatLng> decodedPoints = PolyUtil.decode(route.overviewPolyline.getEncodedPath());

                            runOnUiThread(() -> drawPolyline(decodedPoints));
                        }
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        runOnUiThread(() -> Toast.makeText(MapsActivity.this, "Failed to fetch directions", Toast.LENGTH_SHORT).show());
                    }
                });
    }

    private void drawPolyline(List<LatLng> points) {
        if (currentPolyline != null) {
            currentPolyline.remove();
        }

        PolylineOptions polylineOptions = new PolylineOptions()
                .addAll(points)
                .color(ContextCompat.getColor(this, R.color.purple_200))
                .width(10f);

        currentPolyline = mMap.addPolyline(polylineOptions);
    }
}
