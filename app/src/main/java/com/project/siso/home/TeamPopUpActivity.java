package com.project.siso.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.project.siso.databinding.ActivityDetailSignUpBinding;
import com.project.siso.databinding.ActivityTeamPopUpBinding;

public class TeamPopUpActivity extends AppCompatActivity {
    private ActivityTeamPopUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTeamPopUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

}