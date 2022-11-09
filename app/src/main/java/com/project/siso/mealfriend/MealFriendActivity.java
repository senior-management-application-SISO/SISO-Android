package com.project.siso.mealfriend;

import android.app.Activity;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.siso.databinding.ActivityMealFriendBinding;
import com.project.siso.httpserver.GetHttpClient;

import org.json.JSONException;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MealFriendActivity extends AppCompatActivity {

    public static MealFriends selectedMealFriends;
    private ActivityMealFriendBinding binding;

    ArrayList<MealFriends> items = new ArrayList<>(); //리사이클러 뷰가 보여줄 대량의 데이터를 가지고 있는 리시트객체
    MealFriendAdapter adapter;   //리사이클러뷰가 보여줄 뷰을 만들어내는 객체참조변수


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMealFriendBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    @Override
    protected void onResume() {
        super.onResume();

        //임이의 대량의 데이터 추가
//        //임이의 대량의 데이터 추가
//        items.add(new MealFriend(3, "asdads", "newyork"));
//        items.add(new MealFriend(4, "sde", "fds"));
//
//        //아답터생성 및 리사이클러뷰에 설정
//        adapter = new MealFriendAdapter(this, items);
//        binding.mealFriendList.setAdapter(adapter);

        items.clear();

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE);
        Long teamId = sharedPreferences.getLong("teamId", 0L);

        String request = "restapi/dining-friends/select/" + teamId;
        GetHttpClient httpClient = new GetHttpClient(request);
        Thread th = new Thread(httpClient);
        th.start();
        String result = null;

        long start = System.currentTimeMillis();

        while (result == null) {
            result = httpClient.getResult();
            long end = System.currentTimeMillis();
            if (end - start > 3000) {
                return;
            }
        }

        Gson gson = new Gson();

        MealFriends[] mealFriends = gson.fromJson(result, MealFriends[].class);
        List<MealFriends> list = Arrays.asList(mealFriends);


        System.out.println("mealFriedns = " + mealFriends);

        for (MealFriends mealFriend : list) {
            items.add(new MealFriends(mealFriend.getId(), mealFriend.getMemNumber(), mealFriend.getCurrentNumber(), mealFriend.getTime(), mealFriend.getAddress(), mealFriend.getName(), mealFriend.getPhoneNumber(), mealFriend.getMemo(), mealFriend.getState(), mealFriend.getTeamId(), mealFriend.getUsersId()));
        }

        //아답터생성 및 리사이클러뷰에 설정
        adapter = new MealFriendAdapter(this, items);
        binding.mealFriendList.setAdapter(adapter);

        binding.total.setText("식사 친구: " + items.size() + "개");

        //*추가** 다른 방법의 바인딩클래스 사용 아답터 예제소개 *****
        //MyAdapter2 adapter2= new MyAdapter2(this, items);
        //mainBinding.recycler.setAdapter(adapter2);
    }
}