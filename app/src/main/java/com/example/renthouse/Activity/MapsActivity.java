package com.example.renthouse.Activity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
        /*geoApiContext = new GeoApiContext.Builder()
                .apiKey("AIzaSyDQjIt9yAJVT7qEIJ7-epCSAy7Wn0PmGgQ")
                .build();

        // Set up the locations
        place1 = new MarkerOptions().position(new LatLng(21.027763, 105.834160)).title("Location 1");
        place2 = new MarkerOptions().position(new LatLng(10.823099, 106.629662)).title("Location 2");*/
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add markers or customize map settings here
        // For example:
        LatLng startLatLng = new LatLng(37.7749, -122.4194); // Replace with your start location coordinates
        mMap.addMarker(new MarkerOptions().position(startLatLng).title("Start"));

        LatLng endLatLng = new LatLng(34.0522, -118.2437); // Replace with your end location coordinates
        mMap.addMarker(new MarkerOptions().position(endLatLng).title("End"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startLatLng, 12)); // Set the initial camera position

        requestDirections(startLatLng, endLatLng);
    }

    public void requestDirections(LatLng origin, LatLng destination) {
        String apiKey = "AIzaSyDQjIt9yAJVT7qEIJ7-epCSAy7Wn0PmGgQ"; // Replace with your actual Google Maps API key
        String url = "https://maps.googleapis.com/maps/api/directions/json?" +
                "origin=" + origin.latitude + "," + origin.longitude +
                "&destination=" + destination.latitude + "," + destination.longitude +
                "&key=" + apiKey;

        new FetchDirectionsTask().execute(url);
    }

    private class FetchDirectionsTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                reader.close();
                inputStream.close();

                return result.toString();
            } catch (Exception e) {
                Log.e("FetchDirectionsTask", "Error fetching directions", e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    JSONObject json = new JSONObject(result);
                    JSONArray routes = json.getJSONArray("routes");
                    if (routes.length() > 0) {
                        JSONObject route = routes.getJSONObject(0);
                        JSONObject overviewPolyline = route.getJSONObject("overview_polyline");
                        String encodedPolyline = overviewPolyline.getString("points");

                        List<LatLng> polylinePoints = decodePolyline(encodedPolyline);

                        PolylineOptions polylineOptions = new PolylineOptions()
                                .addAll(polylinePoints)
                                .width(10)
                                .color(Color.BLUE);

                        mMap.addPolyline(polylineOptions);
                    }
                } catch (Exception e) {
                    Log.e("FetchDirectionsTask", "Error parsing directions JSON", e);
                }
            }
        }

        private List<LatLng> decodePolyline(String encodedPolyline) {
            List<LatLng> points = new ArrayList<>();
            int index = 0;
            int len = encodedPolyline.length();
            int lat = 0, lng = 0;

            while (index < len) {
                int shift = 0, result = 0;
                int b;
                do {
                    b = encodedPolyline.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lat += dlat;

                shift = 0;
                result = 0;
                do {
                    b = encodedPolyline.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lng += dlng;

                LatLng point = new LatLng((double) lat / 1E5, (double) lng / 1E5);
                points.add(point);
            }

            return points;
        }
    }
}
