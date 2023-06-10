package com.example.renthouse.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.HorizontalScrollView;

import com.example.renthouse.Activity.ViewPagerAdapter;
import com.example.renthouse.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class FragmentFilter extends Fragment {
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
    private Context mContext;

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

        viewPager = view.findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        materialButtonFilter[0] = view.findViewById(R.id.buttonPrice); // buttonPrice
        materialButtonFilter[1] = view.findViewById(R.id.buttonUtilities); // buttonUtilities
        materialButtonFilter[2] = view.findViewById(R.id.buttonType); // buttonType
        materialButtonFilter[3] = view.findViewById(R.id.buttonAmount); // buttonAmount
        materialButtonFilter[4] = view.findViewById(R.id.buttonOptionOther); // buttonOptionOther



        params = viewPager.getLayoutParams();
        params.height = 400;
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;    //500px
        viewPager.setLayoutParams(params);

        HorizontalScrollView horizontalScrollViewResult = view.findViewById(R.id.horizontalDisplayResult);
        horizontalScrollViewResult.setVisibility(View.GONE);
        _initSetButtonPrice();
        handleButtonnFilter();

        return view;
    }

    public boolean hasButtonChecked() {
        for (int i = 0; i < materialButtonFilter.length; i++) {
            if (materialButtonFilter[i].isChecked() == true) {
                return true;
            }
        }
        return false;
    }

    public void _initSetButtonPrice() {
        materialButtonFilter[0].setChecked(true);
    }

    public void unChekedButton(int position) {
        for (int i = 0; i < materialButtonFilter.length; i++) {
            if (i == position)
                continue;
            else {
                materialButtonFilter[i].setChecked(false);
                materialButtonFilter[i].setTextColor(ContextCompat.getColor(mContext, R.color.Secondary_40));
                materialButtonFilter[i].setIcon(ContextCompat.getDrawable(mContext, R.drawable.ic_expand_unchecked));                 // materialButtonFilter[i].setIconTint(getColorStateList(R.color.text_selector_filter));
            }
        }
    }

    public void handleButtonnFilter() {
        for (int i = 0; i < materialButtonFilter.length; i++) {
            int finalI = i;
            materialButtonFilter[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!materialButtonFilter[finalI].isChecked()) {
                        materialButtonFilter[finalI].setTextColor(ContextCompat.getColor(mContext, R.color.Secondary_40));
                        materialButtonFilter[finalI].setIcon(ContextCompat.getDrawable(mContext, R.drawable.button_expand_icon));
                        materialButtonFilter[finalI].setIconTint(ContextCompat.getColorStateList(mContext, R.color.text_selector_filter));

                        viewPager.setVisibility(View.GONE);
                    } else {
                        materialButtonFilter[finalI].setIcon(ContextCompat.getDrawable(mContext, R.drawable.button_collapse_icon_when_selecting));
                        materialButtonFilter[finalI].setIconTint(ContextCompat.getColorStateList(mContext, R.color.text_selector_filter));
                        materialButtonFilter[finalI].setTextColor(ContextCompat.getColor(mContext, R.color.white));
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

    public void setPageViewerControl(int position) {
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

    private void closeKeyBoard() {
        if (mContext instanceof Activity) {
            Activity activity = (Activity) mContext;
            View view = activity.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

}