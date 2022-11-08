package com.project.siso.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.project.siso.MainActivity;
import com.project.siso.R;
import com.project.siso.databinding.ActivityHomeBinding;
import com.project.siso.domain.Users;
import com.project.siso.httpserver.GetHttpClient;
import com.project.siso.httpserver.PostHttpClient;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private Users user;

    //유저 정보
    public static Users userInfo;

    private long backKeyPressedTime = 0;

    private Toast toast;

    String loginId, loginPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE);

        loginId = sharedPreferences.getString("id", null);
        loginPwd = sharedPreferences.getString("pw", null);

        if (loginId != null && loginPwd != null) {
            user = new Users();
            user.setUserId(loginId);
            user.setPassword(loginPwd);

            loginCheck(user);
        }

        setListeners();
    }

    //클릭 이벤트
    private void setListeners() {
        binding.signupButton.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class)));

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = new Users();
                user.setUserId(binding.id.getText().toString());
                user.setPassword(binding.password.getText().toString());

                loginCheck(user);
            }
        });
    }

    private void loginCheck(Users user) {
        try {
            Gson gson = new Gson();

            RequestBody formBody = new FormBody.Builder()
                    .add("userId", user.getUserId())
                    .add("password", user.getPassword())
                    .build();

            String request = "restapi/login";

            PostHttpClient postHttpClient = new PostHttpClient(request, formBody);

            Thread th = new Thread(postHttpClient);
            th.start();
            String result = null;

            long start = System.currentTimeMillis();

            while (result == null) {
                result = postHttpClient.getResult();
                long end = System.currentTimeMillis();
                if (end - start > 5000) {
                    Toast.makeText(getApplicationContext(), "서버 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            userInfo = gson.fromJson(result, Users.class);
            System.out.println(userInfo);


            if (result.isEmpty()) {
                Toast.makeText(getApplicationContext(), "아이디 또는 비밀번호가 맞지 않습니다.", Toast.LENGTH_SHORT).show();
            } else {
                SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE);
                SharedPreferences.Editor autoLogin = sharedPreferences.edit();

                autoLogin.putString("id", user.getUserId());
                autoLogin.putString("pw", user.getPassword());
                autoLogin.putLong("villageHallId", userInfo.getVillageHallId());
                autoLogin.putLong("teamId", userInfo.getTeamId());

                autoLogin.apply();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
            toast.cancel();
            moveTaskToBack(true);
            finishAndRemoveTask();

            System.exit(0);
        }
    }
}