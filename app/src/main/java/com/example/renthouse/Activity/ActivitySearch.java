package com.example.renthouse.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.Fragment.FragmentFilter;
import com.example.renthouse.R;
import com.example.renthouse.Test.Location;
import com.example.renthouse.Test.LocationAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ActivitySearch extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LocationAdapter locationAdapter;
    private SearchView searchView;
    private TextView textViewHuy;
    private TextView textViewHistorySearch;
    private List<Location> listHistoryLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = findViewById(R.id.searchViewAddress);
        recyclerView = findViewById(R.id.recycleViewHistorySearch);
        textViewHistorySearch = findViewById(R.id.textViewHistorySearch);
        searchView.requestFocus();
        textViewHuy = findViewById(R.id.textViewHuy);

        // Đọc dữ liệu từ file json


        // Đọc lịch sử tìm kiếm
        listHistoryLocation = new ArrayList<Location>();
        readHistoryLocation();

        // Set dữ liệu recycleView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        locationAdapter = new LocationAdapter(listHistoryLocation, this, "Hồ Chí Minh");
        recyclerView.setAdapter(locationAdapter);

        // Truyền dòng kẻ giữa các item trong recycleview
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                textViewHistorySearch.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                FragmentFilter fragmentFilter = new FragmentFilter();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.linearLayoutHistorySearch, fragmentFilter);
                fragmentTransaction.commit();

                searchView.clearFocus();
                locationAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                locationAdapter.getFilter().filter(newText);
                if (newText.isEmpty()) {
                    textViewHistorySearch.setText("Lịch sử tìm kiếm");
                } else {
                    textViewHistorySearch.setText("Gợi ý");
                }
                return false;
            }

        });
        textViewHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

//    private List<Location> getListLocation() {
//        List<Location> list = new ArrayList<>();
//        list.add(new Location("Thu Duc"));
//        list.add(new Location("Quan 1"));
//        list.add(new Location("Quan 2"));
//        list.add(new Location("Quan 3"));
//        list.add(new Location("Quan 4"));
//        list.add(new Location("Quan 5"));
//        list.add(new Location("Quan 6"));
//        list.add(new Location("Quan 7"));
//        list.add(new Location("Quan 8"));
//        list.add(new Location("Quan 9"));
//        list.add(new Location("Quan 10"));
//
//        return list;
//    }
    private void readHistoryLocation() {
        // Đọc lịch sử tìm kiếm ở đây
        listHistoryLocation.add(new Location("Thu Duc"));
        listHistoryLocation.add(new Location("Quan 1"));
        listHistoryLocation.add(new Location("Quan 2"));
        listHistoryLocation.add(new Location("Quan 3"));
    }
}