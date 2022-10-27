package com.project.siso.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.project.siso.databinding.ActivitySignUpBinding;

public class DetailSignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}