package com.project.siso.mealfriend;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.project.siso.databinding.ActivityMealFriendRegisterBinding;
import com.project.siso.home.HomeActivity;
import com.project.siso.httpserver.PostHttpClient;

import okhttp3.FormBody;
import okhttp3.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MealFriendRegisterActivity extends AppCompatActivity {

    private ActivityMealFriendRegisterBinding binding;
    NumberPicker numberPicker;
    int y = 0, m = 0, d = 0, h = 0, mi = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        binding = ActivityMealFriendRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.address.setText(HomeActivity.userInfo.getAddress());

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
                if (y == 0 || h == 0) {
                    Toast.makeText(getApplicationContext(), "날짜 또는 시간을 설정해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    saveDiningFriend();
                }
            }
        });

        binding.setDateButton.setOnClickListener(v -> showDate());
        binding.setTimeButton.setOnClickListener(v -> showTime());
    }

    private void saveDiningFriend() {
        try {
            String a = String.valueOf(LocalDateTime.now());

            RequestBody formBody = new FormBody.Builder()
                    .add("memNumber", String.valueOf(binding.memNumber.getValue()))
                    .add("currentNumber", String.valueOf(0))
                    .add("address", binding.address.getText().toString())
                    .add("name", String.valueOf(HomeActivity.userInfo.getUserName()))
                    .add("phoneNumber", String.valueOf(HomeActivity.userInfo.getPhoneNumber()))
                    .add("memo", binding.memo.getText().toString())
                    .add("state", String.valueOf(1))
                    .add("teamId", String.valueOf(HomeActivity.userInfo.getTeamId()))
                    .add("usersId", String.valueOf(HomeActivity.userInfo.getId()))
                    .build();

            String request = "restapi/dining-friends/save/" + y + "-" + m + "-" + d + " " + h + ":" + mi;

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
                Toast.makeText(getApplicationContext(), "식사 친구 모으기 시작!", Toast.LENGTH_SHORT).show();
                HomeActivity.userDFId = 1L;
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "식사 친구 모으기 다시 시도", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void showDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                y = year;
                m = month + 1;
                d = dayOfMonth;

                binding.setDateButton.setText(y + "년 " + m + "월 " + d + "일");

            }
        }, LocalDate.now().getYear(), LocalDate.now().plusMonths(-1).getMonthValue(), LocalDate.now().getDayOfMonth());

        datePickerDialog.setMessage("메시지");
        datePickerDialog.show();
    }

    void showTime() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                h = hourOfDay;
                mi = minute;

                binding.setTimeButton.setText(h + "시 " + mi + "분");

            }
        }, 12, 0, true);

        timePickerDialog.setMessage("메시지");
        timePickerDialog.show();
    }
}