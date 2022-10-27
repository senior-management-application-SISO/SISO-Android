package com.project.siso;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.project.siso.databinding.ActivityMainBinding;

import com.project.siso.databinding.ActivityVillageHallBinding;
import com.project.siso.home.SignUpActivity;
import com.project.siso.mealfriend.MealFriendActivity;
import com.project.siso.medicine.MedicineActivity;
import com.project.siso.qr.QrCodeScanActivity;
import com.project.siso.villagehall.VillageHallActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main_Activity";

    private ActivityMainBinding binding;
    private ActivityVillageHallBinding villageHallBinding;

    private ImageView ivMenu;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        ivMenu = findViewById(R.id.iv_menu);
        drawerLayout = findViewById(R.id.drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //액션바 변경하기(들어갈 수 있는 타입 : Toolbar type
        setSupportActionBar(toolbar);

        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        setListeners();


    }

    private void setListeners() {
        binding.villageHall.setOnClickListener(v ->
            startActivity(new Intent(getApplicationContext(), VillageHallActivity.class))
        );
        binding.medicineButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MedicineActivity.class)));
        binding.qrCheck.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), QrCodeScanActivity.class)));
        binding.mealFriendButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MealFriendActivity.class)));
    }
}
