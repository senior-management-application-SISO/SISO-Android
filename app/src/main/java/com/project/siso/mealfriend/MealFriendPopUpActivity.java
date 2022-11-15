package com.project.siso.mealfriend;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.gson.Gson;
import com.project.siso.databinding.ActivityMealFriendPopUpBinding;
import com.project.siso.home.HomeActivity;
import com.project.siso.httpserver.GetHttpClient;
import com.project.siso.httpserver.PostHttpClient;
import okhttp3.FormBody;
import okhttp3.RequestBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class MealFriendPopUpActivity extends AppCompatActivity {

    private ActivityMealFriendPopUpBinding binding;

    private DetailMealFriends detailMealFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        binding = ActivityMealFriendPopUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int width = (int) (dm.widthPixels * 0.9); // Display 사이즈의 90%
        int height = (int) (dm.heightPixels * 0.9); // Display 사이즈의 90%
        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;

        long mealFriendId = Long.parseLong(getIntent().getStringExtra("mealFriend"));

        getMealFriendInfo(mealFriendId);

        setClickListener();
    }

    private void getMealFriendInfo(long mealFriendId) {
        GetHttpClient httpclient = new GetHttpClient("restapi/dining-friends/select/detail/" + mealFriendId);
        Thread th = new Thread(httpclient);
        th.start();
        String result = null;

        long start = System.currentTimeMillis();

        while (result == null) {
            result = httpclient.getResult();
            long end = System.currentTimeMillis();
            if (end - start > 3000) {
                return;
            }
        }
        Gson gson = new Gson();

        DetailMealFriends[] mealFriends = gson.fromJson(result.toString(), DetailMealFriends[].class);
        List<DetailMealFriends> mealFriend = Arrays.asList(mealFriends);

        binding.name.setText(mealFriend.get(0).getName());
        binding.address.setText(mealFriend.get(0).getAddress());

        StringBuffer sb = new StringBuffer();
        sb.append(mealFriend.get(0).getTime());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(sb.replace(10, 11, " "), formatter);
        binding.time.setText(String.valueOf(dateTime.getYear() + "년 " + dateTime.getMonthValue() + "월 " + dateTime.getDayOfMonth() + "일 " + dateTime.getDayOfWeek() + " " + dateTime.getHour() + "시 " + dateTime.getMinute() + "분"));

        binding.phoneNumber.setText(mealFriend.get(0).getPhoneNumber());
        String members = "";
        for (int i = 0; i < mealFriend.size(); i++) {
            members += mealFriend.get(i).getUserName() + "님 ";
        }
        binding.members.setText(members);
        binding.memo.setText(mealFriend.get(0).getMemo());

        detailMealFriends = mealFriend.get(0);
    }


    private void setClickListener() {
        binding.mealFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerMealFriend();
            }
        });
    }

    private void registerMealFriend() {
        try {
            RequestBody formBody = new FormBody.Builder()
                    .add("usersId", String.valueOf(HomeActivity.userInfo.getId()))
                    .add("diningFriendsId", String.valueOf(detailMealFriends.getId()))
                    .build();

            String request = "restapi/dining-friends/save/dining-friends-users";

            PostHttpClient postHttpClient = new PostHttpClient(request, formBody);

            Thread th = new Thread(postHttpClient);
            th.start();
            String result = null;

            long start = System.currentTimeMillis();

            while (result == null) {
                result = postHttpClient.getResult();
                long end = System.currentTimeMillis();
                if (end - start > 3000) {
                    Toast.makeText(getApplicationContext(), "서버 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            if (result.equals("saved")) {
                Toast.makeText(getApplicationContext(), "식사 신청되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "식사 신청을 실패했습니다.", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}