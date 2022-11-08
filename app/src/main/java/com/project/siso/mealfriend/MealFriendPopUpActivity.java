package com.project.siso.mealfriend;

import android.util.DisplayMetrics;
import android.view.Window;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.project.siso.R;
import com.project.siso.databinding.ActivityMealFriendBinding;

public class MealFriendPopUpActivity extends AppCompatActivity {

    private ActivityMealFriendBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        binding = ActivityMealFriendBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int width = (int) (dm.widthPixels * 0.9); // Display 사이즈의 90%
        int height = (int) (dm.heightPixels * 0.9); // Display 사이즈의 90%
        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;

        setClickListener();
    }

    private void setClickListener() {

    }
}