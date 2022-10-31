package com.project.siso.villagehall;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;
import com.project.siso.databinding.ActivityVillageHallBinding;
import com.project.siso.httpserver.GetHttpClient;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UsersVillageHallActivity extends AppCompatActivity {

    private ActivityVillageHallBinding binding;
    private String userListStringJson = null;
    private List<Users> users = new ArrayList<>();

    ArrayList<Users> items= new ArrayList<>(); //리사이클러 뷰가 보여줄 대량의 데이터를 가지고 있는 리시트객체
    UsersVillageHallAdapter adapter;   //리사이클러뷰가 보여줄 뷰을 만들어내는 객체참조변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVillageHallBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onResume() {
        super.onResume();

        getVillageHall();

        //임의의 대량의 데이터 추가
//        items.add(new VillageHall("joe", "010-1111-1111"));
//        items.add(new VillageHall("woo", "010-2222-2222"));
//        items.add(new VillageHall("hyeon", "010-3333-3333"));
        for (Users user : users) {
            items.add(new Users(user.getUserName(), user.getPhoneNumber()));
        }

        //아답터생성 및 리사이클러뷰에 설정
        adapter= new UsersVillageHallAdapter(this, items);
        binding.recycler.setAdapter(adapter);

    }

    private void getVillageHall() {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE);
            Long villageHallId = sharedPreferences.getLong("villageHallId", 0L);

            String request = "restapi/hallstate/" + villageHallId;
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
            users = jsonParsing(userListStringJson);
            System.out.println("users = " + users);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private List<Users> jsonParsing(String userListStringJson) throws JSONException {
        Gson gson = new Gson();

        Users[] users = gson.fromJson(userListStringJson.toString(), Users[].class);
        List<Users> list = Arrays.asList(users);

        return list;
    }


}

