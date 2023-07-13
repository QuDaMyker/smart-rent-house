package com.example.renthouse.Admin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.renthouse.Activity.ActivityRecentSeen;
import com.example.renthouse.Adapter.RoomLatestAdapter;
import com.example.renthouse.OOP.Region;
import com.example.renthouse.OOP.Room;
import com.example.renthouse.R;
import com.example.renthouse.databinding.ActivityAdminStatisticsBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("IntegerDivisionInFloatingPointContext")
public class Admin_ActivityStatistics extends AppCompatActivity {
    private ArrayList<Room> listRoom;
    private ProgressDialog mProgressDialog;
    private long priceMin;
    private long priceMax;
    private long priceAverage;
    private List<String> dateInWeek;
    private List<Integer> amountInDateList;
    private List<Integer> amountRentInDateList;
    private List<String> regionList;
    private List<Integer> amountRoom;
    private PieChart pieChart;
    private boolean flag = false;
    private TextView textViewPriceMin;
    private TextView textViewPriceMax;
    private TextView textViewPriceAverage;
    private DecimalFormat decimalFormat = new DecimalFormat("#,###");
    private BarChart barChart;
    private ImageButton btn_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_statistics);
        listRoom = new ArrayList<>();
        regionList = new ArrayList<>();
        amountRoom = new ArrayList<>();
        dateInWeek = new ArrayList<>();
        getRoom();
        amountInDateList = new ArrayList<>(Collections.nCopies(7, 0));
        amountRentInDateList = new ArrayList<>(Collections.nCopies(7, 0));
        pieChart = findViewById(R.id.charHotRegion);
        addDateInSameWeek();
        textViewPriceMin = findViewById(R.id.textViewPriceMin);
        textViewPriceMax = findViewById(R.id.textViewPriceMax);
        textViewPriceAverage = findViewById(R.id.textViewPriceAverage);
        barChart = findViewById(R.id.barChart);
        btn_back = findViewById(R.id.btn_Back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setUpPieChart() {
        Log.d("Region", String.valueOf(amountRoom));
        ArrayList<PieEntry> region = new ArrayList<>();
        for (int i = 0; i < regionList.size(); i++) {
            region.add(new PieEntry(amountRoom.get(i), regionList.get(i)));
        }
        ArrayList<Integer> colors = new ArrayList<>();

        colors.add(getResources().getColor(R.color.Primary_95));
        colors.add(getResources().getColor(R.color.Primary_80));
        colors.add(getResources().getColor(R.color.Primary_60));
        colors.add(getResources().getColor(R.color.Primary_40));
        colors.add(getResources().getColor(R.color.Primary_10));

//        ArrayList<Integer> colorsText = new ArrayList<>();
//        colors.add(getColor(R.color.Secondary_20));
//        colors.add(getColor(R.color.Secondary_40));
//        colors.add(getColor(R.color.Secondary_60));
//        colors.add(getColor(R.color.Secondary_80));
//        colors.add(getColor(R.color.Secondary_90));

        PieDataSet pieDataSet = new PieDataSet(region, "");
        pieDataSet.setColors(colors);;
        pieDataSet.setValueTextColor(getColor(R.color.Secondary_20));
        pieDataSet.setValueTextSize(10f);

        pieDataSet.setSelectionShift(5f); // Độ phóng to khi entry được chọn


        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.animate();
        pieChart.invalidate();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            if (mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(Admin_ActivityStatistics.this);
                mProgressDialog.setMessage(getString(R.string.loading));
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.show();
            }
            else {
                xuLyDuLieuRoom();
                setUpPieChart();
                setUpBarChart();
            }
        }
    }

    private void setUpBarChart() {
        BarDataSet barDataSet1 = new BarDataSet(barEntriesRent(), "Số phòng được thuê");
        barDataSet1.setColor(Color.parseColor("#FFD601"));

        BarDataSet barDataSet2 = new BarDataSet(barEntriesAmount(), "Số phòng được đăng");
        barDataSet2.setColor(Color.parseColor("#0077B6"));

        BarData data = new BarData(barDataSet1, barDataSet2);
        barChart.setData(data);
        XAxis xAxis = barChart.getXAxis();
        data.setBarWidth(0.15f);
        String[] days = new String[7];
        for (int i = 0; i < 7; i++){
            days[i] = dateInWeek.get(i).substring(0, 5);
        }
        xAxis.setValueFormatter(new IndexAxisValueFormatter(days));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
//
        barChart.setDragEnabled(true);
        xAxis.setAxisMinimum(-0.2f);
        xAxis.setDrawAxisLine(false);
        xAxis.setAxisMaximum(days.length - 0.2f);

        YAxis yAxisLeft = barChart.getAxisLeft();
        yAxisLeft.setGranularity(1f); // Bước đơn vị trục y
//        barChart.setVisibleXRangeMaximum(3);
        float barSpace = 0.2f;
        float groupSpace = 0.25f;
//
        //barChart.getXAxis().setAxisMaximum(0);
        //barChart.getXAxis().setAxisMaximum(0 + barChart.getBarData().getGroupWidth(groupSpace, barSpace) * 7);
//
//        barChart.getAxisLeft().setAxisMaximum(0);
        barChart.groupBars(0, groupSpace, barSpace);


        barChart.setDrawGridBackground(false);
        barChart.getAxisRight().setTextColor(getResources().getColor(R.color.white));
        barChart.getXAxis().removeAllLimitLines();
        barChart.getDescription().setEnabled(false);
        barChart.invalidate();
    }
    private ArrayList<BarEntry> barEntriesRent() {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0; i < dateInWeek.size(); i++){
            Log.d("Số lượng", String.valueOf(amountRentInDateList.get(i)));
            barEntries.add(new BarEntry(i + 1,amountRentInDateList.get(i)));
        }
        return barEntries;
    }
    private ArrayList<BarEntry> barEntriesAmount() {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0; i < dateInWeek.size(); i++){
            Log.d("Số lượng đã thuê", String.valueOf(amountInDateList.get(i)));
            barEntries.add(new BarEntry(i + 1,amountInDateList.get(i)));
        }
        return barEntries;
    }
    public void getRoom() {
        LocalDate ngayHienTai = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            ngayHienTai = LocalDate.now();
        }

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Rooms");
        LocalDate finalNgayHienTai = ngayHienTai;
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("Các ngày trong tuần", String.valueOf(dateInWeek));
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Room room = dataSnapshot.getValue(Room.class);
                    String date = room.getDateTime().split(" ")[1];
                    Log.d("Ngày đăng phòng", date);
                    if (dateInWeek.contains(date.trim())) {
                        int position = dateInWeek.indexOf(date);
                        amountInDateList.set(position, amountInDateList.get(position) + 1);
                        if (room.isRented()) {
                            amountRentInDateList.set(position, amountRentInDateList.get(position) + 1);
                        }
                    }
                    listRoom.add(room);
                }
                getRegion();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void getRegion() {
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("PopularRegion");
        reference1.addListenerForSingleValueEvent((new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Region region = dataSnapshot.getValue(Region.class);
                    if (regionList.contains(region.getCityNameWithType())) {
                        int position = regionList.indexOf(region.getCityNameWithType());
                        amountRoom.set(position, amountRoom.get(position) + 1);
                    } else {
                        regionList.add(region.getCityNameWithType());
                        amountRoom.add(1);
                    }
                }
                mProgressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }));
    }
    public void xuLyDuLieuRoom() {
        // Tính toán giá trị trung bình
        long minPrice = listRoom.get(0).getPrice();
        long maxPrice = listRoom.get(0).getPrice();
        long sumPrice = 0;
        int count = 0;

        for (Room r : listRoom) {
            int price = r.getPrice();

            if (price < minPrice) {
                minPrice = price;
            }

            if (price > maxPrice) {
                maxPrice = price;
            }

            sumPrice += price;
            count++;
        }
        long roundedValue = Math.round(sumPrice / (500000 * count)) * 500000;
        priceMin = minPrice;
        priceMax = maxPrice;
        priceAverage = roundedValue;

        String formattedNumber1 = decimalFormat.format(priceMin);
        String formattedNumber2 = decimalFormat.format(priceAverage);
        String formattedNumber3 = decimalFormat.format(priceMax);

        String price1 = formattedNumber1.replaceAll(",", ".") + " đ";
        String price2 = formattedNumber2.replaceAll(",", ".") + " đ";
        String price3 = formattedNumber3.replaceAll(",", ".") + " đ";

        textViewPriceMin.setText(price1);
        textViewPriceMax.setText(price3);
        textViewPriceAverage.setText(price2);




    }
    private void addDateInSameWeek() {
        LocalDate ngayHienTai = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            ngayHienTai = LocalDate.now();
        }

        // Tạo định dạng cho ngày
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        }

        // Tìm ngày đầu tiên trong tuần
        LocalDate ngayDauTuan = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            ngayDauTuan = ngayHienTai.with(DayOfWeek.MONDAY);
        }

        // Liệt kê các ngày cùng một tuần và định dạng thành chuỗi
        for (int i = 0; i < 7; i++) {
            LocalDate ngay = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                ngay = ngayDauTuan.plusDays(i);
            }
            String ngayFormatted = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                ngayFormatted = ngay.format(formatter);
            }
            dateInWeek.add(ngayFormatted);
        }
    }
}