package com.example.renthouse.Activity;


import static androidx.core.content.ContextCompat.getColorStateList;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.renthouse.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;


public class ActivityFilter extends AppCompatActivity {

    String[] buttonTitles = {
            "WC riêng",
            "Cửa sổ",
            "Wifi",
            "Nhà bếp",
            "Máy giặt",
            "Tủ lạnh",
            "Chỗ để xe",
            "An ninh",
            "Tự do",
            "Chủ riêng",
            "Gác lửng",
            "Thú cưng",
            "Giường",
            "Tủ đồ",
            "Máy lạnh"
    };

    int[] iconIds = {
            R.drawable.ic_wc,
            R.drawable.ic_window,
            R.drawable.ic_wifi,
            R.drawable.ic_kitchen,
            R.drawable.ic_laundry,
            R.drawable.ic_fridge,
            R.drawable.ic_motobike,
            R.drawable.ic_security,
            R.drawable.ic_timer,
            R.drawable.outline_person_24,
            R.drawable.ic_entresol,
            R.drawable.ic_pet,
            R.drawable.ic_bed,
            R.drawable.ic_wardrobe,
            R.drawable.ic_cool
    };

    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    ViewGroup.LayoutParams params;

    MaterialButton[] materialButtonFilter = new MaterialButton[5];
    TextInputEditText textInputEditTextSearchAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        viewPager = findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        materialButtonFilter[0] = findViewById(R.id.buttonPrice); // buttonPrice
        materialButtonFilter[1] = findViewById(R.id.buttonUtilities); // buttonUtilities
        materialButtonFilter[2] = findViewById(R.id.buttonType); // buttonType
        materialButtonFilter[3] = findViewById(R.id.buttonAmount); // buttonAmount
        materialButtonFilter[4] = findViewById(R.id.buttonOptionOther); // buttonOptionOther

        textInputEditTextSearchAddress = findViewById(R.id.textInputEditTextSearchAddress);


        params = viewPager.getLayoutParams();
        params.height = 400;
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;    //500px
        viewPager.setLayoutParams(params);

        HorizontalScrollView horizontalScrollViewResult = findViewById(R.id.horizontalDisplayResult);
        horizontalScrollViewResult.setVisibility(View.GONE);
        _initSetButtonPrice();
        handleButtonnFilter();
    }
    public boolean hasButtonChecked() {
        for (int i = 0; i < materialButtonFilter.length; i++) {
            if (materialButtonFilter[i].isChecked() == true) {
                return true;
            }
        }
        return false;
    }
    public void _initSetButtonPrice(){
        materialButtonFilter[0].setChecked(true);
    }
    public void unChekedButton(int position) {
        for (int i = 0; i < materialButtonFilter.length; i++) {
            if (i == position)
                continue;
            else {
                materialButtonFilter[i].setChecked(false);
                materialButtonFilter[i].setTextColor(getColor(R.color.Secondary_40));
                materialButtonFilter[i].setIcon(getDrawable(R.drawable.ic_expand_unchecked));                 // materialButtonFilter[i].setIconTint(getColorStateList(R.color.text_selector_filter));
            }
        }
    }
    public void handleButtonnFilter() {
        for (int i = 0; i < materialButtonFilter.length; i ++) {
            int finalI = i;
            materialButtonFilter[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!materialButtonFilter[finalI].isChecked()) {
                        materialButtonFilter[finalI].setTextColor(getColor(R.color.Secondary_40));
                        materialButtonFilter[finalI].setIcon(getDrawable(R.drawable.button_expand_icon));
                        materialButtonFilter[finalI].setIconTint(getColorStateList(R.color.text_selector_filter));

                        viewPager.setVisibility(View.GONE);
                    }
                    else {
                        materialButtonFilter[finalI].setIcon(getDrawable(R.drawable.button_collapse_icon_when_selecting));
                        materialButtonFilter[finalI].setIconTint(getColorStateList(R.color.text_selector_filter));
                        materialButtonFilter[finalI].setTextColor(getColor(R.color.white));
                        viewPager.setVisibility(View.VISIBLE);

                        unChekedButton(finalI);

                        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;    //500px
                        viewPager.setLayoutParams(params);

                        setPageViewerControl(finalI);
                    }
                }
            });
        }
    }
    public void setPageViewerControl(int position){
        switch (position) {
            case 0:
                params.height = 400;
                viewPager.setCurrentItem(0);
                break;
            case 1:
                params.height = 750;
                viewPager.setCurrentItem(1);
                break;
            case 2:
                params.height = 500;
                viewPager.setCurrentItem(2);
                break;
            case 3:
                params.height = 300;
                viewPager.setCurrentItem(3);
                break;
            case 4:
                params.height = 500;
                viewPager.setCurrentItem(4);
                break;
            default:
                params.height = 400;
                viewPager.setCurrentItem(0);
        }
    }
    private void closeKeyBoard(){
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}