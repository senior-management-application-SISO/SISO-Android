package com.project.siso;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import com.project.siso.databinding.ActivityMainBinding;
import com.project.siso.mealfriend.MealFriendActivity;
import com.project.siso.medicine.activities.AlarmMainActivity;
import com.project.siso.qr.QrCodeScanActivity;
import com.project.siso.villagehall.UsersVillageHallActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main_Activity";

    private ActivityMainBinding binding;

    private long backKeyPressedTime = 0;
    private Toast toast;

    private ImageView ivMenu;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ivMenu = findViewById(R.id.iv_menu);
        drawerLayout = findViewById(R.id.drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        nav = findViewById(R.id.navigation);

        //액션바 변경하기(들어갈 수 있는 타입 : Toolbar type
        setSupportActionBar(toolbar);

        setListeners();
    }

    private void setListeners() {
        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);

            }
        });

        binding.villageHall.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(), UsersVillageHallActivity.class))
        );
        binding.medicineButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), AlarmMainActivity.class)));
        binding.qrCheck.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), QrCodeScanActivity.class)));
        binding.mealFriendButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MealFriendActivity.class)));

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.logout) {
                    SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("id");
                    editor.remove("pw");
                    editor.apply();

                    finish();
                }

                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }

        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
            toast.cancel();
            moveTaskToBack(true);
            finishAndRemoveTask();

            System.exit(0);
        }
    }
}
