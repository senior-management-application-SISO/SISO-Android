package com.project.siso;

import android.content.Intent;
<<<<<<< HEAD
=======
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

>>>>>>> befd26b2539de3da4621a2168a7997809ba08b7c
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

<<<<<<< HEAD
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.project.siso.databinding.ActivityMainBinding;
import com.project.siso.medicine.MedicineActivity;
=======
import com.project.siso.databinding.ActivityMainBinding;
import com.project.siso.mealfriend.MealFriendActivity;
import com.project.siso.qr.QrCodeScanActivity;
>>>>>>> befd26b2539de3da4621a2168a7997809ba08b7c

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main_Activity";

    private ActivityMainBinding binding;

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
<<<<<<< HEAD
        binding.medicineButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MedicineActivity.class)));
=======
        binding.qrCheck.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), QrCodeScanActivity.class)));
        binding.mealFriendButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MealFriendActivity.class)));
>>>>>>> befd26b2539de3da4621a2168a7997809ba08b7c
    }
}
