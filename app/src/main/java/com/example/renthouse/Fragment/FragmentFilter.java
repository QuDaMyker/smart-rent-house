package com.example.renthouse.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.renthouse.Adapter.FilterApdapter;
import com.example.renthouse.FragmentFilter.FragmentAmount;
import com.example.renthouse.FragmentFilter.FragmentResult;
import com.example.renthouse.FragmentFilter.FragmentSort;
import com.example.renthouse.FragmentFilter.FragmentPrice;
import com.example.renthouse.FragmentFilter.FragmentType;
import com.example.renthouse.FragmentFilter.FragmentUtilities;
import com.example.renthouse.Interface.IAmountValueChangeListener;
import com.example.renthouse.Interface.IClickDeleteItemFilterListener;
import com.example.renthouse.Interface.IPriceValueChangeListener;
import com.example.renthouse.Interface.ISortValueChangeListener;
import com.example.renthouse.Interface.ITypeValueChangeListener;
import com.example.renthouse.Interface.IUtilitiesValueChangeListener;
import com.example.renthouse.OOP.ObjectFilter;
import com.example.renthouse.OOP.ObjectSearch;
import com.example.renthouse.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class FragmentFilter extends Fragment {
    MaterialButton[] materialButtonFilter = new MaterialButton[5];
    private Context mContext;
    private FragmentPrice fragmentPrice;
    private FragmentUtilities fragmentUtilities;
    private FragmentType fragmentType;
    private FragmentAmount fragmentAmount;
    private FragmentResult fragmentResult;
    private FragmentSort fragmentSort;
    private ObjectSearch objectSearch;
    private RecyclerView recyclerView;
    private FilterApdapter filterApdapter;
    private static int POSITION = -1;
    private LinearLayout linearLayout;
    private ObjectFilter objectFilter;
    private AppCompatButton buttonDeleteFilterTool;
    private AppCompatButton buttonApply;
    private LinearLayout linearLayoutControl;

    public FragmentFilter(String address) {
        objectFilter = new ObjectFilter();
        objectSearch = new ObjectSearch();
        objectSearch.setPath(address);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filter, container, false);
        materialButtonFilter[0] = view.findViewById(R.id.buttonPrice); // buttonPrice
        materialButtonFilter[1] = view.findViewById(R.id.buttonUtilities); // buttonUtilities
        materialButtonFilter[2] = view.findViewById(R.id.buttonType); // buttonType
        materialButtonFilter[3] = view.findViewById(R.id.buttonAmount); // buttonAmount
        materialButtonFilter[4] = view.findViewById(R.id.buttonOptionOther); // buttonOptionOther
        recyclerView = view.findViewById(R.id.recycleViewFilter);
        linearLayout = view.findViewById(R.id.linearLayoutResultFilter);
        buttonDeleteFilterTool = view.findViewById(R.id.buttonDeleteFilterTool);
        buttonApply = view.findViewById(R.id.buttonApply);


        fragmentResult = new FragmentResult();

        fragmentPrice = new FragmentPrice(new IPriceValueChangeListener() {
            @Override
            public void onValuePriceChangeListener() {
                objectSearch.setPrice(fragmentPrice.getValue());
                objectFilter.setPriceString(fragmentPrice.getValueFilter());
                filterApdapter.setData(objectFilter.getPriceString(), 0);
                if (filterApdapter.getItemCount() != 0) {
                    linearLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        fragmentUtilities = new FragmentUtilities(new IUtilitiesValueChangeListener() {
            @Override
            public void onValueUtilitiesChangeListener(String utility) {
                objectSearch.getUtilities().add(utility);
                objectFilter.getUtilitesString().add(utility);
                filterApdapter.setData(utility, 1);
                if (filterApdapter.getItemCount() != 0) {
                    linearLayout.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onValueUtilitiesDeleteListener(String utility) {
                try {
                    filterApdapter.deleteItemtUtilities(utility);
                    objectSearch.getUtilities().remove(utility);
                    objectFilter.getUtilitesString().remove(utility);
                } catch (IndexOutOfBoundsException e) {
                    Log.d("Số lượng nè", String.valueOf(objectSearch.getUtilities().size()));
                }
                if (filterApdapter.getItemCount() == 0) {
                    linearLayout.setVisibility(View.GONE);
                }
            }
        });
        fragmentType = new FragmentType(new ITypeValueChangeListener() {
            @Override
            public void onValueTypeChangeListener() {
                objectSearch.setType(fragmentType.getValueString());
                objectFilter.setTypeRoom(fragmentType.getValueString());
                filterApdapter.setData(objectFilter.getTypeRoom(), 2);
                if (filterApdapter.getItemCount() != 0) {
                    linearLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        fragmentAmount = new FragmentAmount(new IAmountValueChangeListener() {
            @Override
            public void onValueAmountChangeListener() {
                objectSearch.setAmount(fragmentAmount.getAmount());
                objectSearch.setGender(fragmentAmount.getGender());
                switch (objectSearch.getGender()) {
                    case "Nữ":
                        fragmentAmount.POSITION = 0;
                        break;
                    case "Nam":
                        fragmentAmount.POSITION = 1;
                        break;
                    case "Khác":
                        fragmentAmount.POSITION = 2;
                        break;
                    default:
                        fragmentAmount.POSITION = -1;
                }
                objectFilter.setAmountAndGender(fragmentAmount.getValueString());
                filterApdapter.setData(objectFilter.getAmountAndGender(), 3);
                if (filterApdapter.getItemCount() != 0) {
                    linearLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        fragmentSort = new FragmentSort(new ISortValueChangeListener() {
            @Override
            public void onValueSortChangeListener() {
                objectSearch.setSort(fragmentSort.getValueString());
                objectFilter.setSortString(fragmentSort.getValueString());
                filterApdapter.setData(objectFilter.getSortString(), 4);
                if (filterApdapter.getItemCount() != 0) {
                    linearLayout.setVisibility(View.VISIBLE);
                }
            }
        });


        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager1);

        filterApdapter = new FilterApdapter(new IClickDeleteItemFilterListener() {
            @Override
            public void onItemDeletePriceListener() {
                fragmentPrice.resetFragment();
                objectSearch.setPrice(new ArrayList<>());
                objectFilter.setPriceString("");
                if (filterApdapter.getItemCount() == 1) {
                    linearLayout.setVisibility(View.GONE);
                }
                if (POSITION != 0) {
                    setButtonUnChecked(0);
                }
            }
            @Override
            public void onItemDeleteTypeListener() {
                fragmentType.resetFragment();
                objectSearch.setType("");
                objectFilter.setTypeRoom("");
                if (filterApdapter.getItemCount() == 1) {
                    linearLayout.setVisibility(View.GONE);
                }
                if (POSITION != 2) {
                    setButtonUnChecked(2);
                }
            }

            @Override
            public void onItemDeleteUtilitiesListener(String content) {
                if (POSITION == 1) {
                    fragmentUtilities.setButtonUnCheckedString(content);
                }
                objectSearch.getUtilities().remove(content);
                objectFilter.getUtilitesString().remove(content);
                if (filterApdapter.getItemCount() == 1) {
                    linearLayout.setVisibility(View.GONE);
                }
                if (POSITION != 1 && objectSearch.getUtilities().size() == 0) {
                    setButtonUnChecked(1);
                }
            }

            @Override
            public void onItemDeleteAmountListener() {
                fragmentAmount.resetFragment();
                objectFilter.setAmountAndGender("");
                objectSearch.setAmount(-1);
                objectSearch.setGender("");
                if (filterApdapter.getItemCount() == 1) {
                    linearLayout.setVisibility(View.GONE);
                }
                if (POSITION != 3) {
                    setButtonUnChecked(3);
                }
            }

            @Override
            public void onItemDeleteSortListener() {
                fragmentSort.resetFragment();
                objectSearch.setSort("");
                if (filterApdapter.getItemCount() == 1) {
                    linearLayout.setVisibility(View.GONE);
                }
                if (POSITION != 4) {
                    setButtonUnChecked(4);
                }
            }
        });
        recyclerView.setAdapter(filterApdapter);
        linearLayout.setVisibility(View.GONE);
        handleButtonnFilter();
        _initSetButtonPrice();

        buttonDeleteFilterTool = view.findViewById(R.id.buttonDeleteFilterTool);
        buttonDeleteFilterTool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa bộ lọc không?");

                // Nút xác nhận xóa
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!objectSearch.getPrice().isEmpty()) {
                            fragmentPrice.resetFragment();
                        }
                        if (!objectSearch.getUtilities().isEmpty()) {
                            if (POSITION == 1) {
                                fragmentUtilities.resetFragment();
                            }
                        }
                        if (objectSearch.getType().isEmpty()) {
                            fragmentType.resetFragment();
                        }
                        if (objectSearch.getAmount() != -1) {
                            fragmentAmount.resetFragment();
                        }
                        if (objectSearch.getSort().isEmpty()) {
                            fragmentSort.resetFragment();
                        }
                        for (int i = 0; i < 5; i ++) {
                            if (i == POSITION) {
                                continue;
                            }
                            setButtonUnChecked(i);
                        }
                        objectSearch.clearData();
                        objectFilter.clearData();
                        filterApdapter.clearData();
                        linearLayout.setVisibility(View.GONE);
                    }
                });

                // Nút hủy
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Đóng hộp thoại và không xóa
                        dialog.dismiss();
                    }
                });

                // Hiển thị hộp thoại
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        buttonApply = view.findViewById(R.id.buttonApply);

        buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutControl.setVisibility(View.GONE);
                fragmentResult.setObjectSearch(objectSearch);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.linearLayoutFragment, fragmentResult);
                fragmentTransaction.commit();
                Log.d("hehehe", String.valueOf(objectSearch));
                unChekedButton();
                POSITION = -1;
            }
        });
        linearLayoutControl = view.findViewById(R.id.linearLayoutControl);
        return view;
    }

    public void _initSetButtonPrice() {
        materialButtonFilter[0].setBackgroundColor(getResources().getColor(R.color.Primary_40));
        materialButtonFilter[0].setTextColor(getResources().getColor(R.color.white));
        POSITION = 0;
        replaceFragment(fragmentPrice);
    }
    public void unChekedButton() {
        switch (POSITION) {
            case 0:
                if (!fragmentPrice.hasValue()) {
                    setButtonUnChecked(POSITION);
                } else {
                    setButtonChecked(POSITION);
                }
                fragmentPrice.clearFocus();
                break;
            case 1:
                if (!fragmentUtilities.hasValue()) {
                    setButtonUnChecked(POSITION);
                } else {
                    setButtonChecked(POSITION);
                }
                break;
            case 2:
                if (!fragmentType.hasValue()) {
                    setButtonUnChecked(POSITION);
                } else {
                    setButtonChecked(POSITION);
                }
                break;
            case 3:
                Log.d("Stated resume", String.valueOf(fragmentAmount.isResume));
                Log.d("Stated delete", String.valueOf(fragmentAmount.isDelete));

                if (!fragmentAmount.hasValue()) {
                    setButtonUnChecked(POSITION);
                } else {
                    setButtonChecked(POSITION);
                }
                break;
            case 4:
                if (!fragmentSort.hasValue()) {
                    setButtonUnChecked(POSITION);
                } else {
                    setButtonChecked(POSITION);
                }
                break;
        }
    }
    private void setButtonUnChecked(int position){
        materialButtonFilter[position].setBackgroundColor(getResources().getColor(R.color.Secondary_90));
        materialButtonFilter[position].setTextColor(getResources().getColor(R.color.Secondary_40));
        materialButtonFilter[position].setIcon(getResources().getDrawable(R.drawable.ic_expand));
    }
    private void setButtonChecked(int position){
        materialButtonFilter[position].setBackgroundColor(getResources().getColor(R.color.Primary_98));
        materialButtonFilter[position].setTextColor(getResources().getColor(R.color.Primary_40));
        materialButtonFilter[position].setIcon(getResources().getDrawable(R.drawable.ic_expand_checked));
    }
    private void setButtonChoose(int position) {
        materialButtonFilter[position].setBackgroundColor(getResources().getColor(R.color.Primary_40));
        materialButtonFilter[position].setTextColor(getResources().getColor(R.color.white));
        materialButtonFilter[position].setIcon(getResources().getDrawable(R.drawable.ic_collapse));
    }
    public void handleButtonnFilter() {
        for (int i = 0; i < materialButtonFilter.length; i++) {
            int finalI = i;
            materialButtonFilter[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setButtonChoose(finalI);
                    if (POSITION == finalI) {
                        return;
                    }
                    linearLayoutControl.setVisibility(View.VISIBLE);
                    unChekedButton();
                    POSITION = finalI;
                    setFragment(finalI);
                }
            });
        }
    }
    public void setFragment(int position) {
        switch (position) {
            case 0:
                replaceFragment(fragmentPrice);
                break;
            case 1:
                fragmentUtilities.buttonChecked = objectSearch.getUtilities();
                replaceFragment(fragmentUtilities);
                break;
            case 2:
                replaceFragment(fragmentType);
                break;
            case 3:
                replaceFragment(fragmentAmount);
                break;
            case 4:
                replaceFragment(fragmentSort);
                break;
            default:
                replaceFragment(fragmentPrice);
        }
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.linearLayoutFragment, fragment);
        fragmentTransaction.commit();
    }
}