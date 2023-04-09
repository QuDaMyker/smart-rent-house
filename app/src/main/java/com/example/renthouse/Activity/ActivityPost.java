package com.example.renthouse.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;

import com.example.renthouse.R;
import com.google.android.material.button.MaterialButton;

public class ActivityPost extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_confirm);
        //createButtons();
    }

    private void createButtons() {
        GridLayout gridLayout = findViewById(R.id.grid_layout);

        for (int i = 0; i < buttonTitles.length; i++) {
            MaterialButton button = new MaterialButton(this);
            button.setPadding(15,0,15,0);
            button.setText(buttonTitles[i]);
            button.setIcon(getDrawable(iconIds[i]));
            button.setCheckable(true);
            button.setIconTint(getColorStateList(R.color.button_text_selector));
            button.setTextColor(getColorStateList(R.color.button_text_selector));
            button.setTextSize(16);
            button.setBackgroundTintList(getColorStateList(R.color.button_background_selector));
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