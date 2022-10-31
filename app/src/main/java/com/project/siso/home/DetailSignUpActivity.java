package com.project.siso.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.project.siso.databinding.ActivityDetailSignUpBinding;
import com.project.siso.home.admin.Admin;
import com.project.siso.home.admin.AdminPopUpActivity;
import com.project.siso.home.team.TeamPopUpActivity;
import com.project.siso.home.team.Teams;
import com.project.siso.home.villagehall.VillageHall;
import com.project.siso.home.villagehall.VillageHallPopUpActivity;

public class DetailSignUpActivity extends AppCompatActivity {
    private ActivityDetailSignUpBinding binding;

    //최종 입력한 정보
    public static Admin selectedAdmin;
    public static Teams selectedTeam;
    public static VillageHall selectedVillageHall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailSignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    public void mOnPopupClick(View v) {
        //데이터 담아서 팝업(액티비티) n호출
        Intent intent = new Intent(this, TeamPopUpActivity.class);
        intent.putExtra("data", "Popup");
        startActivityForResult(intent, 1);
    }

    public void mOnPopupClick2(View v) {
        //데이터 담아서 팝업(액티비티) n호출
        Intent intent = new Intent(this, AdminPopUpActivity.class);
        intent.putExtra("data", "Popup");
        startActivityForResult(intent, 1);
    }

    public void mOnPopupClick3(View v) {
        //데이터 담아서 팝업(액티비티) n호출
        Intent intent = new Intent(this, VillageHallPopUpActivity.class);
        intent.putExtra("data", "Popup");
        startActivityForResult(intent, 1);
    }
}