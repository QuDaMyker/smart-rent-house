package com.example.renthouse.Activity.FragmentPost;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renthouse.Adapter.PhotoAdapter;
import com.example.renthouse.R;
import com.google.android.material.button.MaterialButton;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.util.ArrayList;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class FragmentUtilities  extends Fragment {

    private LinearLayout addImg;
    private LinearLayout imgLayout;
    private MaterialButton addImgBtn;
    private RecyclerView rcvPhoto;
    private PhotoAdapter photoAdapter;

    private List<Uri> uriListImg = new ArrayList<>();

    TextView warningTxt;
    GridLayout gridLayout;
    
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
        gridLayout = v.findViewById(R.id.grid_layout);
        createButtons(v);
        
        addImg = (LinearLayout) v.findViewById(R.id.addImg);
        imgLayout = (LinearLayout) v.findViewById(R.id.imgLayout);
        addImgBtn = (MaterialButton) v.findViewById(R.id.addImgBtn);
        rcvPhoto = (RecyclerView) v.findViewById(R.id.rcvPhoto);
        warningTxt = v.findViewById(R.id.warningTxt);
        photoAdapter = new PhotoAdapter(getActivity());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        rcvPhoto.setLayoutManager(gridLayoutManager);
        rcvPhoto.setAdapter(photoAdapter);

        photoAdapter.setOnButtonClickListener(new PhotoAdapter.OnButtonClickListener() {
            @Override
            public void onButtonClick(int position) {
                uriListImg.remove(position);
                photoAdapter.setData(uriListImg);
                if(uriListImg.isEmpty()){
                    addImgBtn.setVisibility(View.VISIBLE);
                    imgLayout.setVisibility(View.GONE);
                    addImgBtn.setText("Bấm vào đây để đăng hình ảnh từ thư viện nhé!");
                    addImgBtn.setTextColor(ContextCompat.getColor(getContext(), R.color.Primary_60));
                    warningTxt.setText("(Tối thiểu 4 ảnh, tối đa 20 ảnh)");
                    warningTxt.setTextColor(ContextCompat.getColor(getContext(), R.color.Secondary_60));
                }
            }
        });
        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermission();
            }
        });
        addImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermission();
            }
        });
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList("utilIdx", getUtilitiesIdx());
        setArguments(bundle);
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle = getArguments();
        if (bundle != null) {
            ArrayList<Integer> utilIdx = bundle.getIntegerArrayList("utilIdx");
            for (int i = 0; i < utilIdx.size(); i++) {
                int idx = utilIdx.get(i);
                View child = gridLayout.getChildAt(idx);
                if (child instanceof MaterialButton) {
                    MaterialButton button = (MaterialButton) child;
                    button.setChecked(true);
                }
            }
        }
        if(uriListImg != null && !uriListImg.isEmpty()){
            photoAdapter.setData(uriListImg);
            addImgBtn.setVisibility(View.GONE);
            imgLayout.setVisibility(View.VISIBLE);
        }
        else{
            addImgBtn.setVisibility(View.VISIBLE);
            imgLayout.setVisibility(View.GONE);
        }
    }

    private void requestPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                selectImagesFromGallery();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(getActivity(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }

    private void selectImagesFromGallery() {
        TedBottomPicker.with(getActivity())
                .setPeekHeight(1600)
                .showTitle(false)
                .setCompleteButtonText("Done")
                .setEmptySelectionText("No Select")
                .showMultiImage(new TedBottomSheetDialogFragment.OnMultiImageSelectedListener() {
                    @Override
                    public void onImagesSelected(List<Uri> uriList) {
                        if(uriList != null && !uriList.isEmpty()){
                            uriListImg.addAll(uriList);
                            photoAdapter.setData(uriListImg);
                            addImgBtn.setVisibility(View.GONE);
                            imgLayout.setVisibility(View.VISIBLE);
                            addImgBtn.setText("Bấm vào đây để đăng hình ảnh từ thư viện nhé!");
                            addImgBtn.setTextColor(ContextCompat.getColor(getContext(), R.color.Primary_60));
                            warningTxt.setText("(Tối thiểu 4 ảnh, tối đa 20 ảnh)");
                            warningTxt.setTextColor(ContextCompat.getColor(getContext(), R.color.Secondary_60));
                        }
                    }
                });
    }

    private void createButtons(View v) {

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

    public ArrayList<String> getUtilities(){
        ArrayList<String> listUtilities = new ArrayList<>();
        if(gridLayout != null){
            int childCount = gridLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = gridLayout.getChildAt(i);
                if (child instanceof MaterialButton) {
                    MaterialButton button = (MaterialButton) child;
                    if(button.isChecked()){
                        listUtilities.add(button.getText().toString());
                    }
                }
            }
        }
        return listUtilities;
    }

    public ArrayList<Integer> getUtilitiesIdx(){
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

    public List<Uri> getUriListImg() {
        return uriListImg;
    }
    public boolean validateData(){
        boolean flag = true;
        if(uriListImg.size() < 4){
            addImgBtn.setText("Vui lòng chọn tối thiểu 4 ảnh");
            addImgBtn.setTextColor(ContextCompat.getColor(getContext(), R.color.Red_50));
            warningTxt.setText("Vui lòng chọn tối thiểu 4 ảnh");
            warningTxt.setTextColor(ContextCompat.getColor(getContext(), R.color.Red_50));
            flag = false;
        }
        if(uriListImg.size() > 20){
            addImgBtn.setText("Vui lòng chọn tối đa 20 ảnh");
            addImgBtn.setTextColor(ContextCompat.getColor(getContext(), R.color.Red_50));
            warningTxt.setText("Vui lòng chọn tối đa 20 ảnh");
            warningTxt.setTextColor(ContextCompat.getColor(getContext(), R.color.Red_50));
            flag = false;
        }
        return flag;
    }
}
