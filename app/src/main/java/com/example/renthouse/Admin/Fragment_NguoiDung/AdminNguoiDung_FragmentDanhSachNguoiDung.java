package com.example.renthouse.Admin.Fragment_NguoiDung;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.renthouse.Admin.Activity.Admin_ActivityThongTinNguoiDung;
import com.example.renthouse.Admin.Adapter.NguoiDungAdapter;
import com.example.renthouse.Admin.OOP.NguoiDung;
import com.example.renthouse.Admin.listeners.ItemNguoiDungListener;
import com.example.renthouse.Admin.listeners.LoadDataFragment;
import com.example.renthouse.OOP.AccountClass;
import com.example.renthouse.R;
import com.example.renthouse.databinding.FragmentAdminNguoiDungDanhSachNguoiDungBinding;
import com.example.renthouse.utilities.Constants;
import com.example.renthouse.utilities.PreferenceManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public class AdminNguoiDung_FragmentDanhSachNguoiDung extends Fragment implements ItemNguoiDungListener, LoadDataFragment {
    private FragmentAdminNguoiDungDanhSachNguoiDungBinding binding;
    private List<NguoiDung> nguoiDungs;
    private List<NguoiDung> filterSearch;
    private PreferenceManager preferenceManager;
    private NguoiDungAdapter nguoiDungAdapter;
    private FirebaseDatabase database;
    private Boolean filtersoPhongTang = true;
    private Boolean filterNgayThamgia = false;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminNguoiDungDanhSachNguoiDungBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

        init();
        preferenceManager = new PreferenceManager(getContext());
        setListeners();
        loadData();


        return view;
    }

    private void loadData() {
        binding.searchView.setQuery("", false);
        progressDialog.show();
        DatabaseReference reference = database.getReference();
        Query query = reference.child("Accounts").orderByChild("blocked").equalTo(false);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<NguoiDung> tempNguoiDungs = new ArrayList<>(); // Danh sách tạm thời để lưu trữ các NguoiDung
                int dataSnapshotCount = (int) dataSnapshot.getChildrenCount(); // Số lượng dữ liệu trong DataSnapshot
                AtomicInteger count = new AtomicInteger(0); // Biến đếm để kiểm tra khi nào dữ liệu đã được tải xong

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String key = snapshot.getKey();
                    AccountClass account = snapshot.getValue(AccountClass.class);
                    Query query1 = reference.child("Rooms").orderByChild("createdBy/email").equalTo(account.getEmail());
                    query1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int countRoom = (int) snapshot.getChildrenCount();
                            tempNguoiDungs.add(new NguoiDung(key, account, countRoom));
                            Log.d("count danh sach", "Count = " + count);
                            // Kiểm tra khi nào dữ liệu đã được tải xong
                            if (count.incrementAndGet() == dataSnapshotCount) {
                                nguoiDungs.addAll(tempNguoiDungs);
                                // them data tu nguoi dung vao filter
                                loadObjects();
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "Load Du Lieu Thanh Cong", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Xử lý khi có lỗi xảy ra
                        }
                    });
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý khi có lỗi xảy ra
                progressDialog.dismiss();
            }
        });
    }


    private void init() {
        nguoiDungs = new ArrayList<>();
        filterSearch = new ArrayList<>();
        nguoiDungAdapter = new NguoiDungAdapter(getContext(), filterSearch, this);
        binding.recycleView.setAdapter(nguoiDungAdapter);
        database = FirebaseDatabase.getInstance();
    }

    private void setListeners() {

        binding.filterSoPhong.setOnClickListener(v -> {
            filtersoPhongTang = !filtersoPhongTang;
            filterNgayThamgia = false;
            binding.filterSoPhong.setBackgroundResource(R.drawable.bg_radius_solid_primary_60);
            binding.filterNgayThamGia.setBackgroundResource(R.drawable.bg_radius_primary_40);
            if (filtersoPhongTang) {
                binding.imageFilterSoPhongTang.setImageResource(R.drawable.ic_arrow_downward);
                Collections.sort(filterSearch, (obj1, obj2) -> obj2.getSoLuongPhong() - obj1.getSoLuongPhong());
                nguoiDungAdapter.notifyDataSetChanged();
            } else {
                binding.imageFilterSoPhongTang.setImageResource(R.drawable.ic_arrow_upward);
                Collections.sort(filterSearch, (obj1, obj2) -> obj1.getSoLuongPhong() - obj2.getSoLuongPhong());
                nguoiDungAdapter.notifyDataSetChanged();
            }
        });

        binding.filterNgayThamGia.setOnClickListener(v -> {
            filterNgayThamgia = !filterNgayThamgia;
            filtersoPhongTang = false;
            binding.filterNgayThamGia.setBackgroundResource(R.drawable.bg_radius_solid_primary_60);
            binding.filterSoPhong.setBackgroundResource(R.drawable.bg_radius_primary_40);
            if (filterNgayThamgia) {
                binding.imageFilterNgayThamGia.setImageResource(R.drawable.ic_arrow_downward);
                Collections.sort(filterSearch, new Comparator<NguoiDung>() {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    @Override
                    public int compare(NguoiDung obj1, NguoiDung obj2) {
                        try {
                            Date date1 = dateFormat.parse(obj1.getAccountClass().getNgayTaoTaiKhoan());
                            Date date2 = dateFormat.parse(obj2.getAccountClass().getNgayTaoTaiKhoan());
                            return date1.compareTo(date2);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return 0;
                    }
                });
                nguoiDungAdapter.notifyDataSetChanged();
            } else {
                binding.imageFilterNgayThamGia.setImageResource(R.drawable.ic_arrow_upward);
                Collections.sort(filterSearch, new Comparator<NguoiDung>() {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    @Override
                    public int compare(NguoiDung obj1, NguoiDung obj2) {
                        try {
                            Date date1 = dateFormat.parse(obj1.getAccountClass().getNgayTaoTaiKhoan());
                            Date date2 = dateFormat.parse(obj2.getAccountClass().getNgayTaoTaiKhoan());
                            return date2.compareTo(date1);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return 0;
                    }
                });
                nguoiDungAdapter.notifyDataSetChanged();
            }
        });

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterObjects(newText);
                return true;
            }
        });

        binding.searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideKeyboard();
                }
            }
        });
    }
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(binding.searchView.getWindowToken(), 0);
        }
    }

    private void loadObjects() {
        filterSearch.clear();
        filterSearch.addAll(nguoiDungs);
        nguoiDungAdapter.notifyDataSetChanged();
    }
    public boolean searchWithAccent(String query, String data) {
        // Bỏ dấu từ chuỗi tìm kiếm
        String queryNoAccent = removeAccent(query.toLowerCase());
        // Bỏ dấu từ dữ liệu
        String dataNoAccent = removeAccent(data.toLowerCase());

        // Kiểm tra xem chuỗi tìm kiếm có tồn tại trong dữ liệu bỏ dấu hay không
        return dataNoAccent.contains(queryNoAccent);
    }

    public String removeAccent(String str) {
        String normalizedStr = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalizedStr).replaceAll("").toLowerCase();
    }

    private void filterObjects(String query) {
        filterSearch.clear();
        for (NguoiDung obj : nguoiDungs) {
            if (searchWithAccent(query, obj.getAccountClass().getFullname())) {
                filterSearch.add(obj);
            }
        }

        /*filterSearch.clear();
        for (NguoiDung obj : nguoiDungs) {
            if (obj.getAccountClass().getFullname().toLowerCase().contains(query.toLowerCase())) {
                filterSearch.add(obj);
            }
        }*/
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                nguoiDungAdapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    public void onItemNguoiDungClick(NguoiDung nguoiDung) {
        Intent intent = new Intent(getActivity(), Admin_ActivityThongTinNguoiDung.class);
        intent.putExtra(Constants.KEY_NGUOIDUNG, nguoiDung);
        mStartForResult.launch(intent);
    }

    private ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent intent = result.getData();
                nguoiDungs.clear();
                filterSearch.clear();
                nguoiDungAdapter.notifyDataSetChanged();
                loadData();
            }
        }
    });

    @Override
    public void loadDataFragment() {
        nguoiDungs.clear();
        filterSearch.clear();
        nguoiDungAdapter.notifyDataSetChanged();
        loadData();
    }
}