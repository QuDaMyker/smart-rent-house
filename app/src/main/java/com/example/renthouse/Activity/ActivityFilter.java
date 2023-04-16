package com.example.renthouse.Activity;


import static androidx.core.content.ContextCompat.getColorStateList;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;


public class ActivityFilter extends AppCompatActivity {
    String[] buttonOption = {
            "Kí túc xá/Homestay",
            "Phòng cho thuê",
            "Nhà nguyên căn",
            "Căn hộ"
    };
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

    MaterialButton[] materialButtonFilter = new MaterialButton[4];
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

        params = viewPager.getLayoutParams();
        params.height = 400;
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;    //500px
        viewPager.setLayoutParams(params);

        HorizontalScrollView horizontalScrollViewResult = findViewById(R.id.horizontalDisplayResult);
        horizontalScrollViewResult.setVisibility(View.GONE);

        handleButtonnFilter();

    }

    public void handleButtonnFilter() {
        for (int i = 0; i < materialButtonFilter.length; i ++) {
            int finalI = i;
            materialButtonFilter[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!materialButtonFilter[finalI].isChecked()) {
                        materialButtonFilter[finalI].setIcon(getDrawable(R.drawable.button_expand_icon));
                        materialButtonFilter[finalI].setIconTint(getColorStateList(R.color.text_selector_filter));
                        viewPager.setVisibility(View.GONE);
                    }
                    else {
                        materialButtonFilter[finalI].setIcon(getDrawable(R.drawable.button_collapse_icon_when_selecting));
                        materialButtonFilter[finalI].setIconTint(getColorStateList(R.color.text_selector_filter));
                        viewPager.setVisibility(View.VISIBLE);
                        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;    //500px
                        viewPager.setLayoutParams(params);

                        switch (finalI) {
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
                            default:
                                params.height = 400;
                                viewPager.setCurrentItem(0);
                        }
                    }
                }
            });
        }
    }

}