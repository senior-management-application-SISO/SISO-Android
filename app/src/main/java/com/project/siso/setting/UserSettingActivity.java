package com.project.siso.setting;

import static com.project.siso.home.HomeActivity.userInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.project.siso.MainActivity;
import com.project.siso.databinding.ActivityAdminSettingBinding;
import com.project.siso.databinding.ActivityUserSettingBinding;
import com.project.siso.domain.Users;
import com.project.siso.httpserver.PostHttpClient;

import java.text.SimpleDateFormat;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class UserSettingActivity extends AppCompatActivity {
    private ActivityUserSettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUserSettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setEditText();

        clickListener();
    }

    public void clickListener() {
        binding.cancelButton.setOnClickListener(v -> finish());
        binding.editButton.setOnClickListener(v -> updateInfo());
    }

    public void setEditText() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
        String strNowDate = simpleDateFormat.format(userInfo.getDateOfBirth());
        binding.name.setText(userInfo.getUserName());
        binding.alone.setChecked(userInfo.getAlone());
        binding.id.setText(userInfo.getUserId());
        binding.password.setText(userInfo.getPassword());
        binding.dateOfBirth.setText(strNowDate);
        binding.address.setText(userInfo.getAddress());
        binding.phoneNumber.setText(userInfo.getPhoneNumber());
    }

    public void updateInfo() {
        try {
            Boolean alone = false;

            if(binding.alone.isChecked()){
                alone = true;
            } else {
                alone = false;
            }


            RequestBody formBody = new FormBody.Builder()
                    .add("userName", binding.name.getText().toString())
                    .add("password", binding.password.getText().toString())
                    .add("address", binding.address.getText().toString())
                    .add("phoneNumber", binding.phoneNumber.getText().toString())
                    .add("alone", alone.toString())
                    .build();

            String request = "restapi/updateuser/" + userInfo.getId();

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

            Toast.makeText(getApplicationContext(), "회원 정보가 수정되었습니다.", Toast.LENGTH_SHORT).show();

            Gson gson = new Gson();

            formBody = new FormBody.Builder()
                    .add("userId", userInfo.getUserId())
                    .add("password", binding.password.getText().toString())
                    .build();

            request = "restapi/login";

            postHttpClient = new PostHttpClient(request, formBody);

            th = new Thread(postHttpClient);
            th.start();
            result = null;

            start = System.currentTimeMillis();

            while (result == null) {
                result = postHttpClient.getResult();
                long end = System.currentTimeMillis();
                if (end - start > 2000) {
                    Toast.makeText(getApplicationContext(), "서버 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            userInfo = gson.fromJson(result, Users.class);

            SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE);
            SharedPreferences.Editor autoLogin = sharedPreferences.edit();

            autoLogin.remove("pw");
            autoLogin.putString("pw", userInfo.getPassword());
            autoLogin.apply();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}