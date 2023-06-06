package com.example.renthouse.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.renthouse.R;
import com.example.renthouse.Test.Location;
import com.example.renthouse.Test.LocationAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentSearch extends Fragment {
    private RecyclerView recyclerView;
    private LocationAdapter locationAdapter;
    private SearchView searchView;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);


        recyclerView = view.findViewById(R.id.recycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        locationAdapter = new LocationAdapter(getListLocation());
        recyclerView.setAdapter(locationAdapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        return view;
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