package com.example.renthouse.Fragment;

import android.content.Context;
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
import com.example.renthouse.FragmentFilter.FragmentOptionOther;
import com.example.renthouse.FragmentFilter.FragmentPrice;
import com.example.renthouse.FragmentFilter.FragmentType;
import com.example.renthouse.FragmentFilter.FragmentUtilities;
import com.example.renthouse.Interface.IClickItemFilterListener;
import com.example.renthouse.OOP.ObjectFilter;
import com.example.renthouse.OOP.ObjectSearch;
import com.example.renthouse.R;
import com.google.android.material.button.MaterialButton;

public class FragmentFilter extends Fragment {
    MaterialButton[] materialButtonFilter = new MaterialButton[5];
    private Context mContext;
    private FragmentPrice priceFragment;
    private FragmentUtilities utilitiesFragment;
    private FragmentType typeFragment;
    private FragmentAmount amountFragment;
    private FragmentOptionOther optionOtherFragment;
    private ObjectSearch objectSearch;
    private RecyclerView recyclerView;
    private FilterApdapter filterApdapter;
    private static int POSITION = -1;
    private LinearLayout linearLayout;
    private ObjectFilter objectFilter;
    private AppCompatButton buttonDeleteFilterTool;
    private AppCompatButton buttonApply;
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

        priceFragment = new FragmentPrice();
        utilitiesFragment = new FragmentUtilities();
        typeFragment = new FragmentType();
        amountFragment = new FragmentAmount();
        optionOtherFragment = new FragmentOptionOther();
        objectFilter = new ObjectFilter();
        objectSearch = new ObjectSearch();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        filterApdapter = new FilterApdapter(new IClickItemFilterListener() {
            @Override
            public void onItemDeletePriceListener() {
                priceFragment.resetFragment();
            }

            @Override
            public void onItemDeleteTypeListener() {
                typeFragment.resetFragment();
            }

            @Override
            public void onItemDeleteUtilitiesListener() {

            }

            @Override
            public void onItemDeleteAmountListener() {
                amountFragment.resetFragment();
            }

            @Override
            public void onItemDeleteSortListener() {
                optionOtherFragment.resetFragment();
            }
        });
        recyclerView.setAdapter(filterApdapter);

        linearLayout.setVisibility(View.GONE);
        handleButtonnFilter();
        _initSetButtonPrice();
        return view;
    }
    public void _initSetButtonPrice() {
        materialButtonFilter[0].setBackgroundColor(getResources().getColor(R.color.Primary_40));
        materialButtonFilter[0].setTextColor(getResources().getColor(R.color.white));
        POSITION = 0;
        replaceFragment(priceFragment);
    }
    public void unChekedButton() {
        switch (POSITION) {
            case 0:
                if (!priceFragment.hasValue()) {
                    setButtonUnChecked();
                } else {
                    setButtonChecked();
                    priceFragment.clearFocus();
                    objectSearch.setPrice(priceFragment.getValue());

                    if (!objectSearch.getPrice().isEmpty()) {
                        objectFilter.setPriceString(priceFragment.getValueFilter());
                        filterApdapter.setData(objectFilter.getPriceString(), 0);
                        linearLayout.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case 1:
                if (!utilitiesFragment.hasValue()) {
                    setButtonUnChecked();
                    objectSearch.setUtilities(utilitiesFragment.getValue());
                } else {
                    setButtonChecked();
                    objectSearch.setUtilities(utilitiesFragment.getValue());
                }
                objectFilter.setUtilitesString(utilitiesFragment.getValueString());
                filterApdapter.setDataUtilites(objectFilter.getUtilitesString());
                break;
            case 2:
                if (!typeFragment.hasValue()) {
                    setButtonUnChecked();
                } else {
                    setButtonChecked();
                    objectSearch.setType(typeFragment.getValue());
                    if (objectSearch.getType() != -1) {
                        objectFilter.setTypeRoom(typeFragment.getValueString());
                        filterApdapter.setData(objectFilter.getTypeRoom(), 2);
                    }
                }
                break;
            case 3:
                setButtonChecked();
                objectSearch.setAmount(amountFragment.getValue().get(0));
                objectSearch.setGender(amountFragment.getValue().get(1));
                amountFragment.POSITION = objectSearch.getGender();
                objectFilter.setAmountAndGender(amountFragment.getValueString());
                filterApdapter.setData(objectFilter.getAmountAndGender(), 3);
                break;
            case 4:
                if (!optionOtherFragment.hasValue()) {
                    setButtonUnChecked();
                } else {
                    setButtonChecked();
                    objectSearch.setSort(optionOtherFragment.getValue());
                    if (objectSearch.getSort() != -1) {
                        objectFilter.setSortString(optionOtherFragment.getValueString());
                        filterApdapter.setData(objectFilter.getSortString(), 4);
                    }
                }
                break;
        }
    }
    private void setButtonUnChecked(){
        materialButtonFilter[POSITION].setBackgroundColor(getResources().getColor(R.color.Secondary_90));
        materialButtonFilter[POSITION].setTextColor(getResources().getColor(R.color.Secondary_40));
        materialButtonFilter[POSITION].setIcon(getResources().getDrawable(R.drawable.ic_expand));
    }
    private void setButtonChecked(){
        materialButtonFilter[POSITION].setBackgroundColor(getResources().getColor(R.color.Primary_98));
        materialButtonFilter[POSITION].setTextColor(getResources().getColor(R.color.Primary_40));
        materialButtonFilter[POSITION].setIcon(getResources().getDrawable(R.drawable.ic_expand_checked));
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
                replaceFragment(priceFragment);
                break;
            case 1:
                Log.d("Số lượng", String.valueOf(objectSearch.getUtilities()));
                utilitiesFragment.buttonChecked = objectSearch.getUtilities();
                replaceFragment(utilitiesFragment);
                break;
            case 2:
                replaceFragment(typeFragment);
                break;
            case 3:
                replaceFragment(amountFragment);
                break;
            case 4:
                replaceFragment(optionOtherFragment);
                break;
            default:
                replaceFragment(priceFragment);
        }
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.linearLayoutFragment, fragment);
        fragmentTransaction.commit();
    }
}