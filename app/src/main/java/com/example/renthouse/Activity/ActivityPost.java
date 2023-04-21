package com.example.renthouse.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ScrollView;

import com.example.renthouse.Activity.FragmentPost.FragmentConfirm;
import com.example.renthouse.Activity.FragmentPost.FragmentInformation;
import com.example.renthouse.Activity.FragmentPost.FragmentLocation;
import com.example.renthouse.Activity.FragmentPost.FragmentUtilities;
import com.example.renthouse.R;
import com.google.android.material.button.MaterialButton;
import com.shuhart.stepview.StepView;

public class ActivityPost extends AppCompatActivity {
    StepView stepView;
    ScrollView scrollView;
    int position;
    MaterialButton nextBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        stepView = (StepView) findViewById(R.id.step_view);
        stepView.done(false);
        nextBtn = (MaterialButton) findViewById(R.id.nextBtn);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        position = 0;
        stepView.setOnStepClickListener(new StepView.OnStepClickListener() {
            @Override
            public void onStepClick(int step) {
                switch (step){
                    case 0: {
                        replaceFragmentContent(new FragmentInformation());
                        position = 0;
                        stepView.done(false);
                        stepView.go(position, true);

                        nextBtn.setBackgroundColor(getColor(R.color.white));
                        nextBtn.setIcon(getDrawable(R.drawable.ic_arrow_right));
                        nextBtn.setIconGravity(MaterialButton.ICON_GRAVITY_TEXT_END);
                        nextBtn.setIconSize(40);
                        nextBtn.setIconPadding(10);
                        nextBtn.setIconTint(getColorStateList(R.color.Primary_60));
                        nextBtn.setTextColor(getColor(R.color.Primary_60));
                        nextBtn.setText("Tiếp theo");
                        break;
                    }
                    case 1: {
                        replaceFragmentContent(new FragmentLocation());
                        position = 1;
                        stepView.done(false);
                        stepView.go(position, true);

                        nextBtn.setBackgroundColor(getColor(R.color.white));
                        nextBtn.setIcon(getDrawable(R.drawable.ic_arrow_right));
                        nextBtn.setIconGravity(MaterialButton.ICON_GRAVITY_TEXT_END);
                        nextBtn.setIconSize(40);
                        nextBtn.setIconPadding(10);
                        nextBtn.setIconTint(getColorStateList(R.color.Primary_60));
                        nextBtn.setTextColor(getColor(R.color.Primary_60));
                        nextBtn.setText("Tiếp theo");
                        break;
                    }
                    case 2: {
                        replaceFragmentContent(new FragmentUtilities());
                        position = 2;
                        stepView.done(false);
                        stepView.go(position, true);

                        nextBtn.setBackgroundColor(getColor(R.color.white));
                        nextBtn.setIcon(getDrawable(R.drawable.ic_arrow_right));
                        nextBtn.setIconGravity(MaterialButton.ICON_GRAVITY_TEXT_END);
                        nextBtn.setIconSize(40);
                        nextBtn.setIconPadding(10);
                        nextBtn.setIconTint(getColorStateList(R.color.Primary_60));
                        nextBtn.setTextColor(getColor(R.color.Primary_60));
                        nextBtn.setText("Tiếp theo");
                        break;
                    }
                    case 3: {
                        replaceFragmentContent(new FragmentConfirm());
                        position = 3;
                        stepView.done(false);
                        stepView.go(position, true);
                        nextBtn.setBackgroundColor(getColor(R.color.Primary_60));
                        nextBtn.setIcon(getDrawable(R.drawable.ic_add));
                        nextBtn.setIconGravity(MaterialButton.ICON_GRAVITY_TEXT_START);
                        nextBtn.setIconSize(40);
                        nextBtn.setIconPadding(10);
                        nextBtn.setIconTint(getColorStateList(R.color.white));
                        nextBtn.setTextColor(getColor(R.color.white));
                        nextBtn.setText("Đăng bài");
                        break;
                    }
                    default:{
                        position = 0;
                        stepView.done(true);

                        nextBtn.setBackgroundColor(getColor(R.color.white));
                        nextBtn.setIcon(getDrawable(R.drawable.ic_arrow_right));
                        nextBtn.setIconGravity(MaterialButton.ICON_GRAVITY_TEXT_END);
                        nextBtn.setIconSize(40);
                        nextBtn.setIconPadding(10);
                        nextBtn.setIconTint(getColorStateList(R.color.Primary_60));
                        nextBtn.setTextColor(getColor(R.color.Primary_60));
                        nextBtn.setText("Tiếp theo");
                        break;
                    }
                }
                scrollView.setScrollY(0);
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (position){
                    case 0: {
                        replaceFragmentContent(new FragmentLocation());
                        position = 1;
                        stepView.done(false);
                        stepView.go(position, true);
                        nextBtn.setBackgroundColor(getColor(R.color.white));
                        nextBtn.setIcon(getDrawable(R.drawable.ic_arrow_right));
                        nextBtn.setIconGravity(MaterialButton.ICON_GRAVITY_TEXT_END);
                        nextBtn.setIconSize(40);
                        nextBtn.setIconPadding(10);
                        nextBtn.setIconTint(getColorStateList(R.color.Primary_60));
                        nextBtn.setTextColor(getColor(R.color.Primary_60));
                        nextBtn.setText("Tiếp theo");
                        break;
                    }
                    case 1: {
                        replaceFragmentContent(new FragmentUtilities());
                        position = 2;
                        stepView.done(false);
                        stepView.go(position, true);

                        nextBtn.setBackgroundColor(getColor(R.color.white));
                        nextBtn.setIcon(getDrawable(R.drawable.ic_arrow_right));
                        nextBtn.setIconGravity(MaterialButton.ICON_GRAVITY_TEXT_END);
                        nextBtn.setIconSize(40);
                        nextBtn.setIconPadding(10);
                        nextBtn.setIconTint(getColorStateList(R.color.Primary_60));
                        nextBtn.setTextColor(getColor(R.color.Primary_60));
                        nextBtn.setText("Tiếp theo");
                        break;
                    }
                    case 2: {
                        replaceFragmentContent(new FragmentConfirm());
                        position = 3;
                        stepView.done(false);
                        stepView.go(position, true);
                        nextBtn.setBackgroundColor(getColor(R.color.Primary_60));
                        nextBtn.setIcon(getDrawable(R.drawable.ic_add));
                        nextBtn.setIconGravity(MaterialButton.ICON_GRAVITY_TEXT_START);
                        nextBtn.setIconSize(40);
                        nextBtn.setIconPadding(10);
                        nextBtn.setIconTint(getColorStateList(R.color.white));
                        nextBtn.setTextColor(getColor(R.color.white));
                        nextBtn.setText("Đăng bài");
                        break;
                    }
                    default:{
                        position = 0;
                        stepView.done(true);

                        nextBtn.setBackgroundColor(getColor(R.color.white));
                        nextBtn.setIcon(getDrawable(R.drawable.ic_arrow_right));
                        nextBtn.setIconGravity(MaterialButton.ICON_GRAVITY_TEXT_END);
                        nextBtn.setIconSize(40);
                        nextBtn.setIconPadding(10);
                        nextBtn.setIconTint(getColorStateList(R.color.Primary_60));
                        nextBtn.setTextColor(getColor(R.color.Primary_60));
                        nextBtn.setText("Tiếp theo");
                        break;
                    }
                }
                scrollView.setScrollY(0);
            }
        });
        replaceFragmentContent(new FragmentInformation());
    }

    protected void replaceFragmentContent(Fragment fragment) {

        if (fragment != null) {

            FragmentManager fmgr = getSupportFragmentManager();

            FragmentTransaction ft = fmgr.beginTransaction();

            ft.replace(R.id.container_body, fragment);

            ft.commit();

        }

    }
}

