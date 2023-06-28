package com.example.renthouse.Admin.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.renthouse.Admin.Activity.Admin_ActivityBaoCaoNguoiDung;
import com.example.renthouse.R;
import com.example.renthouse.databinding.FragmentAdminHomeBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class Admin_FragmentHome extends Fragment {
    private FragmentAdminHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminHomeBinding.inflate(getLayoutInflater());
        //View view = inflater.inflate(R.layout.fragment_admin__home, container, false);
        View view = binding.getRoot();
        // Inflate the layout for this fragment


        setListener();

        return view;
    }
    private void setListener() {
        binding.linearBaoCaoNguoiDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Admin_ActivityBaoCaoNguoiDung.class);
                startActivity(intent);
            }
        });

    }
    private void setChartBar() {
        BarChart barChart = binding.barChart;

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1, 50)); // Giá trị và vị trí của thanh bar thứ nhất
        barEntries.add(new BarEntry(2, 70)); // Giá trị và vị trí của thanh bar thứ hai
        // Thêm các giá trị và vị trí của các thanh bar khác vào danh sách barEntries

        BarDataSet barDataSet = new BarDataSet(barEntries, "Data Set");
        barDataSet.setColors(Color.RED, Color.GREEN, Color.BLUE);




        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.invalidate();

        // set mau cho truc tung ben trai
        barChart.getAxisLeft().setTextColor(Color.RED);
        //set mau cho truc tung ben phai
        barChart.getAxisRight().setTextColor(Color.GREEN);
        // set mau cho truc hoanh o tren
        barChart.getXAxis().setTextColor(Color.WHITE);
        // set mau va ten cho ten bang chu thich
        barChart.getLegend().setTextColor(Color.YELLOW);
        barChart.getLegend().setEnabled(true);
        // set ten va mau cho ten bieu do
        barChart.getDescription().setTextColor(Color.WHITE);
        barChart.getDescription().setText("Biểu đồ cột");
    }

}