package com.example.renthouse.Fragment;

import android.content.Context;
import android.content.Intent;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.example.renthouse.Activity.ActivityDetails;
import com.example.renthouse.Activity.ActivityRecentSeen;
import com.example.renthouse.Activity.ActivitySearch;
import com.example.renthouse.Adapter.OutstandingRoomAdapter;
import com.example.renthouse.Adapter.PhoBienAdapter;
import com.example.renthouse.FragmentFilter.FragmentPhongNoiBat;
import com.example.renthouse.Interface.ItemClick;
import com.example.renthouse.Interface.ItemClickPhoBien;
import com.example.renthouse.OOP.Notification;
import com.example.renthouse.OOP.PhoBien;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;
import com.example.renthouse.utilities.Constants;
import com.example.renthouse.utilities.PreferenceManager;
import com.example.renthouse.Activity.ActivityPost;
import com.example.renthouse.Activity.FindByMapsActivity;
import com.example.renthouse.Activity.NoficationActivity;
import com.example.renthouse.ITEM.itemPhoBien_HomeFragment;
import com.example.renthouse.OOP.City;
import com.example.renthouse.OOP.District;
import com.example.renthouse.OOP.Ward;
import com.example.renthouse.databinding.FragmentHomeBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.badge.ExperimentalBadgeUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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
    private ProgressDialog progressDialog;
    private List<Room> outstandingList;
    private OutstandingRoomAdapter outstandingRoomAdapter;
    private RecyclerView outstanding_recyclerView;
    private List<PhoBien> phoBienList;
    private PhoBienAdapter phoBienAdapter;
    private RecyclerView phobien_recyclerView;
    private PreferenceManager preferenceManager;
    @Override
    @ExperimentalBadgeUtils
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        phobien_recyclerView = view.findViewById(R.id.recycleView_phobien);
        preferenceManager = new PreferenceManager(getContext());
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

        //updateNotSeenNumber(view);

        updateUI();
        //getLastLocation();

        binding.linearLayout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ActivityRecentSeen.class));
            }
        });

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
            }
        });
        binding.textInputFindRoom.clearFocus();
        binding.textInputFindRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.textInputFindRoom.clearFocus();
                Intent intent = new Intent(getActivity(), ActivitySearch.class);
                startActivity(intent);
            }
        });
        return view;
    }

//    @Override
//    @ExperimentalBadgeUtils
//    public void onResume() {
//        super.onResume();
//        updateNotSeenNumber(binding.getRoot());
//    }

    /*@ExperimentalBadgeUtils
    private void updateNotSeenNumber(View view) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference notiRef = database.getReference("Notifications");

        String currentUserID = preferenceManager.getString(Constants.KEY_USER_KEY);

        Query query = notiRef.child(currentUserID).orderByChild("read").equalTo(false);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int notSeenCount = 0;
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Notification notification = childSnapshot.getValue(Notification.class);
                    if (notification != null) {
                        notSeenCount++;
                    }
                }
                handleNotSeenNumber(notSeenCount, view);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/

    @ExperimentalBadgeUtils
    private void handleNotSeenNumber(int notSeenCount, View view) {
        binding.notificationBtn.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                BadgeDrawable badgeDrawable = BadgeDrawable.create(getContext());
                badgeDrawable.setNumber(notSeenCount);
                badgeDrawable.setVerticalOffset(70);
                badgeDrawable.setHorizontalOffset(70);

                BadgeUtils.attachBadgeDrawable(badgeDrawable, binding.notificationBtn, view.findViewById(R.id.layout));

                binding.notificationBtn.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void setDataPhoBien() {
        phoBienList = new ArrayList<>();
        phoBienAdapter = new PhoBienAdapter(getContext(), phoBienList, new ItemClickPhoBien() {
            @Override
            public void onItemClick(PhoBien phoBien) {
                binding.textInputFindRoom.clearFocus();
                Intent intent = new Intent(getContext(), ActivitySearch.class);
                intent.putExtra("selectedPhoBien", phoBien);
                startActivity(intent);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false);
        phobien_recyclerView.setLayoutManager(linearLayoutManager);
        phobien_recyclerView.setAdapter(phoBienAdapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("PopularRoom");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<PhoBien> temPhoBien = new ArrayList<>();

                for (DataSnapshot Snapshot: snapshot.getChildren()){
                    String idphobien = Snapshot.getKey();
                    String image = Snapshot.child("Image").getValue(String.class);
                    String name = Snapshot.child("Name").getValue(String.class);
                    PhoBien phobien = new PhoBien(image, idphobien,name);

                    temPhoBien.add(phobien);
                }
                phoBienList.addAll(temPhoBien);
                phoBienAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });


    }

    private void setDataOutstandingRoom() {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.phong_noi_bat, new FragmentPhongNoiBat());
        fragmentTransaction.commit();

//        outstandingList = new ArrayList<>();
//        outstandingRoomAdapter = new OutstandingRoomAdapter(getContext(), outstandingList, new ItemClick() {
//            @Override
//            public void onItemClick(Room room) {
//                Intent intent = new Intent(getContext(), ActivityDetails.class);
//                intent.putExtra("selectedRoom", room);
//                startActivity(intent);
//            }
//        });
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
//        outstanding_recyclerView.setLayoutManager(gridLayoutManager);
//        outstanding_recyclerView.setAdapter(outstandingRoomAdapter);
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference reference = database.getReference();
//        Query query = reference.child("Rooms");
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                List<Room> temroom = new ArrayList<>();
//                for (DataSnapshot Snapshot : snapshot.getChildren()){
//                    Room room = Snapshot.getValue((Room.class));
//                    if (room.getStatus().equals("approved")) {
//                        temroom.add(room);
//                    }
//                }
//                outstandingList.addAll(temroom);
//                outstandingRoomAdapter.notifyDataSetChanged();
//                progressDialog.dismiss();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                progressDialog.dismiss();
//            }
//        });
    }


    private void updateUI() {
        progressDialog.show();
        if (currentUser != null) {
            binding.tvXinchao.setText("Hi, " + currentUser.getDisplayName());
        }

        setDataOutstandingRoom();
        setDataPhoBien();
    }


    private void getLastLocation() {
        Context context = getContext();
        if(context == null) {
            return;
        }
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        try {
                            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
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
                            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
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