package com.project.siso.villagehall;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.project.siso.databinding.ActivityVillageHallBinding;
import com.project.siso.httpserver.GetHttpClient;

import java.util.ArrayList;
import java.util.List;

public class VillageHallActivity extends AppCompatActivity {

    private ActivityVillageHallBinding binding;
    private String userListStringJson = null;
    private List<Users> usersList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVillageHallBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    ArrayList<Users> items= new ArrayList<>(); //리사이클러 뷰가 보여줄 대량의 데이터를 가지고 있는 리시트객체
    VillageHallAdapter adapter;   //리사이클러뷰가 보여줄 뷰을 만들어내는 객체참조변수

    @Override
    protected void onResume() {
        super.onResume();

        getVillageHall();

        //임의의 대량의 데이터 추가
//        items.add(new VillageHall("joe", "010-1111-1111"));
//        items.add(new VillageHall("woo", "010-2222-2222"));
//        items.add(new VillageHall("hyeon", "010-3333-3333"));

        //아답터생성 및 리사이클러뷰에 설정
        adapter= new VillageHallAdapter(this, items);
        binding.recycler.setAdapter(adapter);

    }

    private void getVillageHall() {
        try {
            String request = "restapi/hallstate/3";
            GetHttpClient httpclient = new GetHttpClient(request);
            Thread th = new Thread(httpclient);
            th.start();
            String result = null;

            long start = System.currentTimeMillis();

            while (result == null) {
                result = httpclient.getResult();
                long end = System.currentTimeMillis();
                if (end - start > 2000) {
                    return;
                }
            }
            userListStringJson = result;
            jsonParsing(userListStringJson);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void jsonParsing(String userListStringJson) {
        Gson gson = new Gson();
        Users users = gson.fromJson(userListStringJson, Users.class);
        System.out.println(users);
    }


}

