package com.project.siso.mealfriend;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.project.siso.databinding.ActivityApplicantMealFriendBinding;
import com.project.siso.databinding.ActivityMealFriendBinding;
import com.project.siso.home.HomeActivity;
import com.project.siso.httpserver.GetHttpClient;
import com.project.siso.httpserver.PostHttpClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class ApplicantMealFriendActivity extends AppCompatActivity {
    private ActivityApplicantMealFriendBinding binding;

    private DetailMealFriends detailMealFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityApplicantMealFriendBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        setListener();
    }

    private void init() {
        getMealFriendInfo();
    }

    private void setListener() {
        binding.backButton.setOnClickListener(v -> finish());
        binding.outButton.setOnClickListener(v -> out());
    }

    private void out() {
        try {
            RequestBody formBody = new FormBody.Builder()
                    .add("usersId", String.valueOf(HomeActivity.userInfo.getId()))
                    .add("diningFriendsId", String.valueOf(HomeActivity.userDFId))
                    .build();

            String request = "restapi/dining-friends/delete/dining-friends-users";

            PostHttpClient postHttpClient = new PostHttpClient(request, formBody);

            Thread th = new Thread(postHttpClient);
            th.start();
            String result = null;

            long start = System.currentTimeMillis();

            while (result == null) {
                result = postHttpClient.getResult();
                long end = System.currentTimeMillis();
                if (end - start > 3000) {
                    Toast.makeText(getApplicationContext(), "?????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            if (result.equals("deleted")) {
                Toast.makeText(getApplicationContext(), "?????? ???????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                HomeActivity.userDFId = -1L;
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "?????? ??????", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void getMealFriendInfo() {
        GetHttpClient httpclient = new GetHttpClient("restapi/dining-friends/select/detail/" + HomeActivity.userDFId);
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
        binding.time.setText(String.valueOf(dateTime.getYear() + "??? " + dateTime.getMonthValue() + "??? " + dateTime.getDayOfMonth() + "??? " + dateTime.getDayOfWeek() + " " + dateTime.getHour() + "??? " + dateTime.getMinute() + "???"));

        binding.phoneNumber.setText(mealFriend.get(0).getPhoneNumber());
        String members = "";
        for (int i = 0; i < mealFriend.size(); i++) {
            members += mealFriend.get(i).getUserName() + "??? ";
        }
        binding.members.setText(members);
        binding.memo.setText(mealFriend.get(0).getMemo());

        detailMealFriends = mealFriend.get(0);
    }

}