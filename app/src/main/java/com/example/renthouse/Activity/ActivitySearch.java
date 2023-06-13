package com.example.renthouse.Activity;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
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
import com.example.renthouse.Interface.IClickItemAddressListener;
import com.example.renthouse.R;
import com.example.renthouse.SharedPreferences.DataLocalManager;
import com.example.renthouse.Test.Location;
import com.example.renthouse.Test.LocationAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ActivitySearch extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LocationAdapter locationAdapter;
    private SearchView searchView;
    private TextView textViewHuy;
    private TextView textViewHistorySearch;
    private List<Location> listHistoryLocation;
    private TextView textViewNoneResult;
    private DataLocalManager dataLocalManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = findViewById(R.id.searchViewAddress);
        recyclerView = findViewById(R.id.recycleViewHistorySearch);
        textViewHistorySearch = findViewById(R.id.textViewHistorySearch);
        searchView.requestFocus();
        textViewHuy = findViewById(R.id.textViewHuy);;
        textViewNoneResult = findViewById(R.id.textViewNoneResult);
        textViewNoneResult.setVisibility(View.GONE);
        dataLocalManager = DataLocalManager.getInstance(getApplicationContext());
        // Đọc dữ liệu từ file json

        // Đọc lịch sử tìm kiếm
        listHistoryLocation = new ArrayList<Location>();
        readHistoryLocation();

        // Set dữ liệu recycleView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        locationAdapter = new LocationAdapter(listHistoryLocation, this, "Hồ Chí Minh", new IClickItemAddressListener() {
            @Override
            public void onItemClick(String address) {
                searchView.setQuery(address, true);
                showFragmentFilter();
            }

            @Override
            public void onFilterCount(int count) {
                if (count == 0) {
                    if (textViewHistorySearch.getVisibility() == View.GONE) {
                        textViewNoneResult.setVisibility(View.GONE);
                    } else {
                        textViewNoneResult.setVisibility(View.VISIBLE);
                    }
                } else {
                    textViewNoneResult.setVisibility(View.GONE);
                }
            }
        });
        recyclerView.setAdapter(locationAdapter);

        // Truyền dòng kẻ giữa các item trong recycleview
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                showFragmentFilter();
                searchView.clearFocus();
                locationAdapter.getFilter().filter(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                locationAdapter.getFilter().filter(newText);
                if (newText.isEmpty()) {
                    textViewHistorySearch.setText("Lịch sử tìm kiếm");
                } else {
                    textViewHistorySearch.setText("Gợi ý");
                }
                int itemCount = locationAdapter.getItemCount();
                return false;
            }
        });
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideFragmentFilter();
                }
            }
        });
        textViewHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void showFragmentFilter() {
        textViewHistorySearch.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentFilter fragmentFilter = new FragmentFilter();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Xóa tất cả các Fragment đang tồn tại trong LinearLayout trước khi thêm Fragment mới
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        fragmentTransaction.replace(R.id.linearLayoutHistorySearch, fragmentFilter);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void hideFragmentFilter() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentFilter fragment = (FragmentFilter) fragmentManager.findFragmentById(R.id.linearLayoutHistorySearch);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();

        textViewHistorySearch.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);

    }
    public void readHistoryLocation() {
        // Đọc lịch sử tìm kiếm ở đây
        Set<String> listHistorySearch = new HashSet<>();
        listHistorySearch = dataLocalManager.getSearchHistory();
        Log.d("Số lượng", String.valueOf(listHistorySearch.size()));
        if (listHistorySearch.size() != 0) {
            for (String location : listHistorySearch) {
                listHistoryLocation.add(new Location(location));
            }
        }
    }


}