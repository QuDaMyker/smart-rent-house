package com.example.renthouse.Activity;

import android.os.Bundle;
import android.view.View;
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

import java.util.ArrayList;
import java.util.List;

public class ActivitySearch extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LocationAdapter locationAdapter;
    private SearchView searchView;

    private TextView textViewHistorySearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = findViewById(R.id.searchViewAddress);
        recyclerView = findViewById(R.id.recycleViewHistorySearch);
        textViewHistorySearch = findViewById(R.id.textViewHistorySearch);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        locationAdapter = new LocationAdapter(getListLocation());
        recyclerView.setAdapter(locationAdapter);

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
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
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