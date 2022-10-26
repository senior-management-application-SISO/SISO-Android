package com.project.siso.medicine;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.project.siso.R;
import com.project.siso.databinding.ActivityMedicineBinding;
import com.project.siso.databinding.ActivityMedicineSetTimeBinding;
import com.project.siso.home.HomeActivity;
import com.project.siso.mealfriend.MealFriendActivity;

public class MedicineActivity extends AppCompatActivity {

    private ActivityMedicineBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMedicineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setListener();
    }

    private void setListener() {
        binding.setTimeButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MedicineSetTimeActivity.class)));

    }

}