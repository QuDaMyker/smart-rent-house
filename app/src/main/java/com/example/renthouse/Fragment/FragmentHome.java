package com.example.renthouse.Fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.renthouse.Activity.ActivityPost;
import com.example.renthouse.Activity.FindByMapsActivity;
import com.example.renthouse.Activity.MapsActivity;
import com.example.renthouse.Activity.NoficationActivity;
import com.example.renthouse.Adapter.NotificationAdapter;
import com.example.renthouse.Adapter.PhoBienAdapter;
import com.example.renthouse.ITEM.itemPhoBien_HomeFragment;
import com.example.renthouse.OOP.City;
import com.example.renthouse.OOP.District;
import com.example.renthouse.OOP.Notification;
import com.example.renthouse.OOP.Ward;
import com.example.renthouse.databinding.FragmentHomeBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class FragmentHome extends Fragment {
    FragmentHomeBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private List<itemPhoBien_HomeFragment> listItemPhoBien_HomeFragment;
    private PhoBienAdapter phoBienAdapter;
    private FusedLocationProviderClient fusedLocationProviderClient;
    double latitude;
    double longitude;
    private String currentLocation = null;
    private final static int REQUEST_CODE = 100;
    ;
    private List<City> cityList;
    private List<District> districtList;
    private List<Ward> wardList;
    private List<District> listDis;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());



        updateUI();
        getLastLocation();






        binding.btnDangBai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ActivityPost.class));
            }
        });

        binding.btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), FindByMapsActivity.class));
            }
        });

        binding.notificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), NoficationActivity.class));
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference notificationsRef = database.getReference("Notifications");
                DatabaseReference accountsRef = database.getReference("Accounts");
                accountsRef.orderByChild("email").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot accountSnapshot : dataSnapshot.getChildren()) {
                            String accountKey = accountSnapshot.getKey();

                            DatabaseReference userRef = notificationsRef.child(accountKey);
                            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot notificationSnapshot : snapshot.getChildren()) {
                                        Notification notification = notificationSnapshot.getValue(Notification.class);
                                        notification.setRead(true);
                                        notificationSnapshot.getRef().setValue(notification);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Xử lý khi có lỗi xảy ra
                    }
                });
            }
        });




        return view;
    }


    private List<itemPhoBien_HomeFragment> getListItemPhoBien_HomeFragment() {
        List<itemPhoBien_HomeFragment> list = new ArrayList<>();


        return list;
    }

    private void updateUI() {
        if (currentUser != null) {
            binding.tvXinchao.setText("Hi, " + currentUser.getDisplayName());
        }


    }


    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        try {
                            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                            latitude = addresses.get(0).getLatitude();
                            longitude = addresses.get(0).getLongitude();

                            String address = getCompleteAddressString(latitude, longitude);
                            //Toast.makeText(getContext(), address, Toast.LENGTH_SHORT).show();
                            binding.tvCurrentLocation.setText(address);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } else {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        try {
                            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                            latitude = addresses.get(0).getLatitude();
                            longitude = addresses.get(0).getLongitude();

                            String address = getCompleteAddressString(latitude, longitude);

                            binding.tvCurrentLocation.setText(address);
                            currentLocation = address;
                            Log.d("Current Location", address);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            //askPermission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(getContext(), "Please provide the required permission", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null && addresses.size() > 0) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder();

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString().trim();
                Log.d("My Current Location", strReturnedAddress.toString());
            } else {
                Log.d("My Current Location", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("My Current Location", "Cannot get Address!");
        }
        return strAdd;
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