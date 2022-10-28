package com.project.siso.home;

import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.project.siso.databinding.ActivityDetailSignUpBinding;
import com.project.siso.home.team.TeamPopUpActivity;
import com.project.siso.home.team.Teams;

public class DetailSignUpActivity extends AppCompatActivity {
    private ActivityDetailSignUpBinding binding;

    public static Teams selectedTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailSignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setListeners();
    }

    //클릭 이벤트
    private void setListeners() {
        binding.searchAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==1) {
            if (resultCode==RESULT_OK) {
                //데이터 받기
                String result = data.getStringExtra("result");
                binding.txtTeam.setText(result);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void mOnPopupClick(View v) {
        //데이터 담아서 팝업(액티비티) n호출
        Intent intent = new Intent(this, TeamPopUpActivity.class);
        intent.putExtra("data", "Popup");
        startActivityForResult(intent, 1);
    }
}