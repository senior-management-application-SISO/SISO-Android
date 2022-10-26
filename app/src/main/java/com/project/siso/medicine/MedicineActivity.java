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
import com.project.siso.home.HomeActivity;

public class MedicineActivity extends AppCompatActivity {

    private ActivityMedicineBinding binding;
    static final String CHANNEL_ID = "channelId";
    static final int notificationId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);



        String textTitle = "Notification Title";
        String textContent = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";


        // 채널 생성
        createNotificationChannel();

        // 내가 보여주고자 하는 Activity 클래스 지정
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // NotificationCompat.Builder(context, "채널이름")
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_home)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                // 위에서 정의한 pendingIntent 지정
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        //호출
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId, builder.build());
    }

    private void createNotificationChannel() {
        // Android8.0 이상인지 확인
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // 채널에 필요한 정보 제공
            CharSequence name = getString(R.string.app_name);
            String description = getString(R.string.app_name);

            // 중요도 설정, Android7.1 이하는 다른 방식으로 지원한다.(위에서 설명)
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            // 채널 생성
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void setListener() {
//        binding.notificationButton.setOnClickListener();

    }

}