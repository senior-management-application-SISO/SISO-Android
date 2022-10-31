package com.project.siso.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.project.siso.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setListeners();
    }

    //클릭 이벤트
    private void setListeners() {
        binding.cancelButton.setOnClickListener(v -> finish());
        binding.settings.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), DetailSignUpActivity.class)));
    }
}