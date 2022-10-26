package com.project.siso.mealfriend;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.project.siso.R;
import com.project.siso.databinding.ActivityMealFriendBinding;
import com.project.siso.databinding.ActivityQrCodeScanBinding;

import java.util.ArrayList;

public class MealFriendActivity extends AppCompatActivity {

    private ActivityMealFriendBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMealFriendBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    ArrayList<MealFriend> items= new ArrayList<>(); //리사이클러 뷰가 보여줄 대량의 데이터를 가지고 있는 리시트객체
    MealFriendAdapter adapter;   //리사이클러뷰가 보여줄 뷰을 만들어내는 객체참조변수

    @Override
    protected void onResume() {
        super.onResume();

        //임이의 대량의 데이터 추가
        items.add(new MealFriend(3, "asdads", "newyork"));
        items.add(new MealFriend(4, "sde", "fds"));

        //아답터생성 및 리사이클러뷰에 설정
        adapter= new MealFriendAdapter(this, items);
        binding.mealFriendList.setAdapter(adapter);

        //*추가** 다른 방법의 바인딩클래스 사용 아답터 예제소개 *****
        //MyAdapter2 adapter2= new MyAdapter2(this, items);
        //mainBinding.recycler.setAdapter(adapter2);
    }
}