package com.project.siso.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.project.siso.databinding.ActivityDetailSignUpBinding;
import com.project.siso.home.team.TeamPopUpActivity;
import com.project.siso.home.team.Teams;

public class DetailSignUpActivity extends AppCompatActivity {
    private ActivityDetailSignUpBinding binding;

    public static Teams selectedTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailSignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setListeners();
    }

    //클릭 이벤트
    private void setListeners() {
        binding.searchAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    public void mOnPopupClick(View v) {
        //데이터 담아서 팝업(액티비티) n호출
        Intent intent = new Intent(this, TeamPopUpActivity.class);
        intent.putExtra("data", "Popup");
        startActivityForResult(intent, 1);
    }
}