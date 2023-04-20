package com.example.renthouse.Activity.FragmentPost;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.renthouse.R;
import com.google.android.material.button.MaterialButton;

public class FragmentUtilities  extends Fragment {
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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_post_utilities, container, false);
        createButtons(v);
        return v;
    }
    private void createButtons(View v) {
        GridLayout gridLayout = v.findViewById(R.id.grid_layout);

        for (int i = 0; i < buttonTitles.length; i++) {
            MaterialButton button = new MaterialButton(v.getContext());
            button.setPadding(15,0,15,0);
            button.setText(buttonTitles[i]);
            button.setIcon(ContextCompat.getDrawable(v.getContext(), iconIds[i]));
            button.setCheckable(true);
            button.setIconTint(ContextCompat.getColorStateList(v.getContext(), R.color.button_text_selector));
            button.setTextColor(ContextCompat.getColorStateList(v.getContext(), R.color.button_text_selector));
            button.setTextSize(16);
            button.setBackgroundTintList(ContextCompat.getColorStateList(v.getContext(), R.color.button_background_selector));
            button.setIconGravity(MaterialButton.ICON_GRAVITY_TEXT_START);
            button.setCornerRadius(16);
            button.setLayoutParams(new GridLayout.LayoutParams(
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f),
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f)
            ));
            gridLayout.addView(button);
        }
    }
}
