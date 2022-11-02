package com.project.siso.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.project.siso.databinding.ActivityAdminSettingBinding;
import com.project.siso.databinding.ActivitySettingBinding;
import com.project.siso.medicine.activities.AlarmMainActivity;

public class SettingActivity extends AppCompatActivity {

    private ActivitySettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        clickListener();
    }

    public void clickListener() {
        binding.userSetting.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), UserSettingActivity.class)));
        binding.adminSetting.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), AdminSettingActivity.class)));
    }
}