package com.project.siso.home;

import android.widget.EditText;

import androidx.annotation.Nullable;
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

    public static int RESULT_OK_SELECTED_ADMIN = 111;
    public static int RESULT_OK_SELECTED_TEAM = 222;
    public static int RESULT_OK_SELECTED_VH = 333;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailSignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        clickListener();
    }

    public void clickListener() {
        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedAdmin == null) {
                    Toast.makeText(getApplicationContext(), "관리자를 설정해주세요.", Toast.LENGTH_SHORT).show();
                } else if (selectedTeam == null) {
                    Toast.makeText(getApplicationContext(), "소속을 설정해주세요.", Toast.LENGTH_SHORT).show();
                } else if (selectedVillageHall == null) {
                    Toast.makeText(getApplicationContext(), "마을회관을 설정해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    //회원가입
                    finish();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK_SELECTED_ADMIN) {
                //데이터 받기
                String result = data.getStringExtra("adminName");
                binding.txtAdmin.setText(result);
            }

            if (resultCode == RESULT_OK_SELECTED_TEAM) {
                String result = data.getStringExtra("teamName");
                binding.txtTeam.setText(result);
            }

            if (resultCode == RESULT_OK_SELECTED_VH) {
                String result = data.getStringExtra("villageHallName");
                binding.txtCountyVillage.setText(result);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void mOnPopupClick1(View v) {
        //데이터 담아서 팝업(액티비티) n호출
        Intent intent = new Intent(this, AdminPopUpActivity.class);
        intent.putExtra("data", "Popup");
        startActivityForResult(intent, 1);
    }

    public void mOnPopupClick2(View v) {
        if (selectedAdmin == null) {
            Toast.makeText(getApplicationContext(), "관리자를 설정해주세요.", Toast.LENGTH_SHORT).show();
        } else {
            //데이터 담아서 팝업(액티비티) n호출
            Intent intent = new Intent(this, TeamPopUpActivity.class);
            intent.putExtra("data", "Popup");
            startActivityForResult(intent, 1);
        }
    }

    public void mOnPopupClick3(View v) {
        if (selectedAdmin == null) {
            Toast.makeText(getApplicationContext(), "관리자를 설정해주세요.", Toast.LENGTH_SHORT).show();
        } else {

            //데이터 담아서 팝업(액티비티) n호출
            Intent intent = new Intent(this, VillageHallPopUpActivity.class);
            intent.putExtra("data", "Popup");
            startActivityForResult(intent, 1);
        }
    }
}