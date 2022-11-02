package com.project.siso.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.project.siso.databinding.ActivityAdminSettingBinding;
import com.project.siso.databinding.ActivityQrCodeScanBinding;

public class AdminSettingActivity extends AppCompatActivity {
    private ActivityAdminSettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminSettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}