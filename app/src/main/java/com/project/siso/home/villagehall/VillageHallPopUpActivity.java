package com.project.siso.home.villagehall;

import static com.project.siso.home.DetailSignUpActivity.RESULT_OK_SELECTED_VH;
import static com.project.siso.home.DetailSignUpActivity.selectedVillageHall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import com.google.gson.Gson;
import com.project.siso.databinding.ActivityTeamPopUpBinding;
import com.project.siso.databinding.ActivityVillageHallBinding;
import com.project.siso.databinding.ActivityVillageHallPopUpBinding;
import com.project.siso.home.team.TeamAdapter;
import com.project.siso.home.team.Teams;
import com.project.siso.httpserver.GetHttpClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VillageHallPopUpActivity extends AppCompatActivity {
    private ActivityVillageHallPopUpBinding binding;

    ArrayList<VillageHall> items = new ArrayList<>(); //리사이클러 뷰가 보여줄 대량의 데이터를 가지고 있는 리시트객체
    VillageHallAdapter adapter;   //리사이클러뷰가 보여줄 뷰을 만들어내는 객체참조변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        binding = ActivityVillageHallPopUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int width = (int) (dm.widthPixels * 0.9); // Display 사이즈의 90%
        int height = (int) (dm.heightPixels * 0.9); // Display 사이즈의 90%
        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;

        setClickListener();
    }

    private void setClickListener() {
        binding.searchTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTeamList(binding.searchVillageHallEdit.getText().toString());
            }
        });
    }

    @Override
    protected void onResume() {
        //임의의 대량의 데이터 추가
//        items.add(new Teams("team1", "address1"));
//        items.add(new Teams("team2", "address2"));
//        items.add(new Teams("team3", "address3"));

        //아답터생성 및 리사이클러뷰에 설정
        super.onResume();
        adapter = new VillageHallAdapter(this, items);
        binding.recycler.setAdapter(adapter);
    }

    private void setTeamList(String villageHallName) {
        items.clear();
        adapter = new VillageHallAdapter(this, items);
        binding.recycler.setAdapter(adapter);

        GetHttpClient httpclient = new GetHttpClient("restapi/villagehall/" + villageHallName);
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
        Gson gson = new Gson();

        VillageHall[] villageHalls = gson.fromJson(result.toString(), VillageHall[].class);
        List<VillageHall> list = Arrays.asList(villageHalls);

        for (VillageHall villageHall : list) {
            items.add(new VillageHall(villageHall.getHallName(), villageHall.getAddress()));
        }

        adapter = new VillageHallAdapter(this, items);
        binding.recycler.setAdapter(adapter);
    }

    public void mOnClose(View v) {
        Intent intent = new Intent();
        intent.putExtra("villageHallName", selectedVillageHall.getHallName());
        setResult(RESULT_OK_SELECTED_VH, intent);

        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }
}