package com.project.siso.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.project.siso.databinding.ActivityAdminSettingBinding;
import com.project.siso.databinding.ActivityUserSettingBinding;

public class UserSettingActivity extends AppCompatActivity {
    private ActivityUserSettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUserSettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}