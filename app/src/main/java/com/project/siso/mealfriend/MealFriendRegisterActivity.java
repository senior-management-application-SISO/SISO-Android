package com.project.siso.mealfriend;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.NumberPicker;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.project.siso.databinding.ActivityMealFriendRegisterBinding;
import com.project.siso.home.HomeActivity;
import com.project.siso.httpserver.PostHttpClient;
import okhttp3.FormBody;
import okhttp3.RequestBody;

import java.time.LocalDateTime;

public class MealFriendRegisterActivity extends AppCompatActivity {

    private ActivityMealFriendRegisterBinding binding;
    NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        binding = ActivityMealFriendRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setNumberPicker();

        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int width = (int) (dm.widthPixels * 0.9); // Display 사이즈의 90%
        int height = (int) (dm.heightPixels * 0.9); // Display 사이즈의 90%
        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;


        clickListener();
    }

    private void setNumberPicker() {
        numberPicker = binding.memNumber;
        numberPicker.setMaxValue(5);
        numberPicker.setMinValue(1);
        numberPicker.setValue(3);
    }

    private void clickListener() {
        binding.mealFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDiningFriend();
            }
        });
    }

    private void saveDiningFriend() {
        try {
            String a = String.valueOf(LocalDateTime.now());

            RequestBody formBody = new FormBody.Builder()
                    .add("memNumber",     String.valueOf(binding.memNumber.getValue()))
                    .add("currentNumber", String.valueOf(0))
                    .add("time",          String.valueOf(LocalDateTime.now()))
                    .add("address",       String.valueOf(HomeActivity.userInfo.getAddress()))
                    .add("name",          String.valueOf(HomeActivity.userInfo.getUserName()))
                    .add("phoneNumber",   String.valueOf(HomeActivity.userInfo.getPhoneNumber()))
                    .add("memo",          binding.memo.getText().toString())
                    .add("state",         String.valueOf(1))
                    .add("teamId",        String.valueOf(HomeActivity.userInfo.getTeamId()))
                    .add("usersId",       String.valueOf(HomeActivity.userInfo.getId()))
                    .build();

            String request = "restapi/dining-friends/save";

            PostHttpClient postHttpClient = new PostHttpClient(request, formBody);

            Thread th = new Thread(postHttpClient);
            th.start();
            String result = null;

            long start = System.currentTimeMillis();

            while (result == null) {
                result = postHttpClient.getResult();
                long end = System.currentTimeMillis();
                if (end - start > 30000000) {
                    Toast.makeText(getApplicationContext(), "서버 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            if (result.equals("saved")) {
                Toast.makeText(getApplicationContext(), "식사 친구 모으기 시작!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "식사 친구 모으기 다시 시도", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}