package com.project.siso.home.team;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.google.gson.Gson;
import com.project.siso.R;
import com.project.siso.databinding.ActivityDetailSignUpBinding;
import com.project.siso.databinding.ActivityTeamPopUpBinding;
import com.project.siso.home.SignUpActivity;
import com.project.siso.httpserver.GetHttpClient;
import com.project.siso.villagehall.Users;
import com.project.siso.villagehall.VillageHallAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeamPopUpActivity extends AppCompatActivity {
    private ActivityTeamPopUpBinding binding;

    ArrayList<Teams> items = new ArrayList<>(); //리사이클러 뷰가 보여줄 대량의 데이터를 가지고 있는 리시트객체
    TeamAdapter adapter;   //리사이클러뷰가 보여줄 뷰을 만들어내는 객체참조변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        binding = ActivityTeamPopUpBinding.inflate(getLayoutInflater());
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
                setTeamList(binding.searchTeamEdit.getText().toString());
            }
        });
    }

    @Override
    protected void onResume() {
        //임의의 대량의 데이터 추가
        items.add(new Teams("team1", "address1"));
        items.add(new Teams("team2", "address2"));
        items.add(new Teams("team3", "address3"));

        //아답터생성 및 리사이클러뷰에 설정
        super.onResume();
        adapter = new TeamAdapter(this, items);
        binding.recycler.setAdapter(adapter);
    }

    private void setTeamList(String teamName) {
        GetHttpClient httpclient = new GetHttpClient("restapi/team/" + teamName);
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

        Teams[] teams = gson.fromJson(result.toString(), Teams[].class);
        List<Teams> list = Arrays.asList(teams);

        for (Teams team : list) {
            items.add(new Teams(team.getTeamName(), team.getTeamAddress()));
        }

        adapter = new TeamAdapter(this, items);
        binding.recycler.setAdapter(adapter);
    }

    public void mOnClose(View v) {
        Intent intent = new Intent();
        intent.putExtra("result", "Close Popup");
        setResult(RESULT_OK, intent);

        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        return;
    }
}