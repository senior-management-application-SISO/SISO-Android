package com.project.siso.home;

import static com.project.siso.home.DetailSignUpActivity.selectedAdmin;
import static com.project.siso.home.DetailSignUpActivity.selectedTeam;
import static com.project.siso.home.DetailSignUpActivity.selectedVillageHall;
import static com.project.siso.home.HomeActivity.userInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.project.siso.MainActivity;
import com.project.siso.databinding.ActivitySignUpBinding;
import com.project.siso.domain.Users;
import com.project.siso.httpserver.PostHttpClient;

import java.sql.Date;
import java.time.LocalDateTime;

import okhttp3.FormBody;
import okhttp3.RequestBody;

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
        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.name.toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (binding.id.toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (binding.id.toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (binding.id.toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "생년월일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (binding.id.toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "집 주소를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (binding.id.toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "휴대전화를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (selectedVillageHall == null) {
                    Toast.makeText(getApplicationContext(), "상세 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    signUp();
                }
            }
        });
    }

    public void signUp() {
        try {
            int checked;

            if (binding.alone.isChecked()) {
                checked = 1;
            } else {
                checked = 0;
            }

            RequestBody formBody = new FormBody.Builder()
                    .add("userName", binding.name.getText().toString())
                    .add("userId", binding.id.getText().toString())
                    .add("password", binding.password.getText().toString())
                    .add("dateOfBirth", binding.dateOfBirth.getText().toString())
                    .add("address", binding.address.getText().toString())
                    .add("phoneNumber", binding.phoneNumber.getText().toString())
                    .add("alone", String.valueOf(checked))
                    .add("teamId", String.valueOf(selectedTeam.getId()))
//                    .add("usersLocationId")
                    .add("adminId", String.valueOf(selectedAdmin.getId()))
                    .add("villageHallId", String.valueOf(selectedVillageHall.getId()))
                    .build();

            String request = "restapi/user";

            PostHttpClient postHttpClient = new PostHttpClient(request, formBody);

            Thread th = new Thread(postHttpClient);
            th.start();
            String result = null;

            long start = System.currentTimeMillis();

            while (result == null) {
                result = postHttpClient.getResult();
                long end = System.currentTimeMillis();
                if (end - start > 2000) {
                    Toast.makeText(getApplicationContext(), "서버 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            if (result.isEmpty()) {
                Toast.makeText(getApplicationContext(), "입력 정보를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "회원가입을 성공하였습니다.", Toast.LENGTH_SHORT).show();
                saveUserLocation(result);
                finish();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void saveUserLocation(String userId){
            try {
                RequestBody formBody = new FormBody.Builder().build();

                String request = "restapi/userslocation/save?userId=" + userId;

                PostHttpClient postHttpClient = new PostHttpClient(request, formBody);

                Thread th = new Thread(postHttpClient);
                th.start();
                String result = null;

                long start = System.currentTimeMillis();

                while (result == null) {
                    result = postHttpClient.getResult();
                    long end = System.currentTimeMillis();
                    if (end - start > 2000) {
                        Toast.makeText(getApplicationContext(), "서버 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                System.out.println("result = " + result);
            } catch (Exception e) {
                System.out.println(e);
            }
    }
}