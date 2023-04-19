package com.example.renthouse.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;

import com.example.renthouse.R;
import com.example.renthouse.Test.Location;
import com.example.renthouse.Test.LocationAdapter;

import java.util.ArrayList;
import java.util.List;

public class ActivitySearch extends AppCompatActivity{
    private RecyclerView recyclerView;
    private LocationAdapter locationAdapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //searchView = (SearchView) findViewById(R.id.searchView);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                locationAdapter.getFilter().filter(query);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                locationAdapter.getFilter().filter(newText);
//                return true;
//            }
//        });

        recyclerView = findViewById(R.id.recycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        locationAdapter = new LocationAdapter(getListLocation());
        recyclerView.setAdapter(locationAdapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);


    }

    private List<Location> getListLocation() {
        List<Location> list = new ArrayList<>();
        list.add(new Location("Thu Duc"));
        list.add(new Location("Quan 1"));
        list.add(new Location("Quan 2"));
        list.add(new Location("Quan 3"));
        list.add(new Location("Quan 4"));
        list.add(new Location("Quan 5"));
        list.add(new Location("Quan 6"));
        list.add(new Location("Quan 7"));
        list.add(new Location("Quan 8"));
        list.add(new Location("Quan 9"));
        list.add(new Location("Quan 10"));

        return list;

    }


}