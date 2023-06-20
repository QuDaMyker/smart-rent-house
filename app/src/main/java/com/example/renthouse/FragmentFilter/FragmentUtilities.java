package com.example.renthouse.FragmentFilter;

import static androidx.core.content.ContextCompat.getColorStateList;
import static androidx.core.content.ContextCompat.getDrawable;

import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import com.example.renthouse.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentUtilities#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentUtilities extends Fragment {
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

    int[] iconCheckedIds = {
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

    int[] iconUnCheckedIds = {
            R.drawable.ic_wc_unchecked,
            R.drawable.ic_window_unchecked,
            R.drawable.ic_wifi_unchecked,
            R.drawable.ic_kitchen_unchecked,
            R.drawable.ic_laundry_unchecked,
            R.drawable.ic_fridge_unchecked,
            R.drawable.ic_motobike_unchecked,
            R.drawable.ic_security_unchecked,
            R.drawable.ic_timer_unchecked,
            R.drawable.ic_owner_unchecked,
            R.drawable.ic_entresol_unchecked,
            R.drawable.ic_pet_unchecked,
            R.drawable.ic_bed_unchecked,
            R.drawable.ic_wardrobe_unchecked,
            R.drawable.ic_cool_unchecked
    };
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GridLayout gridLayout;
    public ArrayList<Integer> buttonChecked;
    public FragmentUtilities() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentUtilities.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentUtilities newInstance(String param1, String param2) {
        FragmentUtilities fragment = new FragmentUtilities();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_utilities, container, false);

        gridLayout = view.findViewById(R.id.grid_layout_fragment_utilities);
        for (int i = 0; i < buttonTitles.length; i++) {
            MaterialButton button = new MaterialButton(getContext());
            button.setPadding(15,0,15,0);
            button.setText(buttonTitles[i]);
            button.setIcon(getDrawable(getContext(), iconUnCheckedIds[i]));
            button.setTextColor(getResources().getColor(R.color.Secondary_40));
            button.setBackgroundColor(getResources().getColor(R.color.Secondary_90));
            button.setCheckable(true);
            button.setIconGravity(MaterialButton.ICON_GRAVITY_TEXT_START);
            button.setIconTintMode(PorterDuff.Mode.MULTIPLY);
            button.setTextSize(11);

            button.setCornerRadius(4);
            button.setLayoutParams(new GridLayout.LayoutParams(
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f),
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f)
            ));
            gridLayout.addView(button);
        }

        setValue(buttonChecked);

        int childCount = gridLayout.getChildCount();
        for (int i = 0; i< childCount; i++) {
            View child = gridLayout.getChildAt(i);
            if (child instanceof  MaterialButton) {
                MaterialButton button = (MaterialButton) child;
                int finalI = i;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (button.isChecked()) {
                            setButtonChecked(finalI);
                        } else {
                            setButtonUnChecked(finalI);
                        }
//                        Log.d("Stated", String.valueOf(button.isChecked()));
                    }
                });
            }
        }
        return view;
    }

    private void setButtonChecked(int position) {
        View child = gridLayout.getChildAt(position);
        if (child instanceof MaterialButton) {
            MaterialButton button = (MaterialButton) child;
            button.setIcon(getDrawable(getContext(), iconCheckedIds[position]));
            button.setTextColor(getResources().getColor(R.color.Primary_40));
            button.setBackgroundColor(getResources().getColor(R.color.Primary_98));
            button.setChecked(true);
        }
    }

    private void setButtonUnChecked(int position) {
        View child = gridLayout.getChildAt(position);
        if (child instanceof MaterialButton) {
            MaterialButton button = (MaterialButton) child;
            button.setIcon(getDrawable(getContext(), iconUnCheckedIds[position]));
            button.setTextColor(getResources().getColor(R.color.Secondary_40));
            button.setBackgroundColor(getResources().getColor(R.color.Secondary_90));
            button.setIconTintMode(PorterDuff.Mode.MULTIPLY);
            button.setChecked(false);
        }
    }

    public ArrayList<Integer> getValue(){
        ArrayList<Integer> listIdx = new ArrayList<>();
        int childCount = gridLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = gridLayout.getChildAt(i);
            if (child instanceof MaterialButton) {
                MaterialButton button = (MaterialButton) child;
                if(button.isChecked()){
                    listIdx.add(i);
                }
            }
        }
        return listIdx;
    }
    public ArrayList<String> getValueString() {
        ArrayList<String> listIdx = new ArrayList<>();
        int childCount = gridLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = gridLayout.getChildAt(i);
            if (child instanceof MaterialButton) {
                MaterialButton button = (MaterialButton) child;
                if(button.isChecked()){
                    listIdx.add(String.valueOf(button.getText()));
                }
            }
        }
        return listIdx;
    }
    public boolean hasValue() {
        boolean flag = true;
        if (getValue().isEmpty()) {
            flag = false;
        }
        return flag;
    }
    public void setValue(ArrayList<Integer> listButtonChecked){
        for (int i : listButtonChecked) {
            View view = gridLayout.getChildAt(i);
            if (view instanceof MaterialButton) {
                MaterialButton button = (MaterialButton) view;
                button.setChecked(false);
                setButtonChecked(i);
            }
        }
    }
}