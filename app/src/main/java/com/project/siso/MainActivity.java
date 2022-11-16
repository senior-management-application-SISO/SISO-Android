package com.project.siso;

import static com.project.siso.home.HomeActivity.userDFId;
import static com.project.siso.home.HomeActivity.userInfo;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import com.google.gson.Gson;
import com.project.siso.adapter.FriendListAdapter;
import com.project.siso.databinding.ActivityMainBinding;
import com.project.siso.domain.UserInfoState;
import com.project.siso.domain.Users;
import com.project.siso.home.HomeActivity;
import com.project.siso.home.admin.AdminAdapter;
import com.project.siso.home.admin.AdminCountyOffice;
import com.project.siso.httpserver.GetHttpClient;
import com.project.siso.httpserver.PostHttpClient;
import com.project.siso.mealfriend.ApplicantMealFriendActivity;
import com.project.siso.mealfriend.DetailMealFriends;
import com.project.siso.mealfriend.HostMealFriendActivity;
import com.project.siso.mealfriend.MealFriendActivity;
import com.project.siso.mealfriend.MealFriends;
import com.project.siso.medicine.activities.AlarmMainActivity;
import com.project.siso.qr.QrCodeScanActivity;
import com.project.siso.setting.SettingActivity;
import com.project.siso.utilities.GpsTracker;
import com.project.siso.villagehall.UsersVillageHallActivity;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity {
    private GpsTracker gpsTracker;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};


    private ActivityMainBinding binding;

    List<UserInfoState> userInfoStateList;
    FriendListAdapter friendListAdapter;

    private long backKeyPressedTime = 0;
    private Toast toast;

    private ImageView ivMenu;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView nav;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        setUserLocation();

        setListeners();

        setFriendList();
    }

    private void init() {

        ivMenu = findViewById(R.id.iv_menu);
        drawerLayout = findViewById(R.id.drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        nav = findViewById(R.id.navigation);

        View nav_header_view = nav.inflateHeaderView(R.layout.header);

        textView = (TextView) nav_header_view.findViewById(R.id.title_name);

        textView.setText(userInfo.getUserName() + "님");


        //액션바 변경하기(들어갈 수 있는 타입 : Toolbar type
        setSupportActionBar(toolbar);

        userInfoStateList = new ArrayList<>();
        friendListAdapter = new FriendListAdapter(this, userInfoStateList);
    }

    private void setListeners() {
        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);

            }
        });

        binding.villageHall.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(), UsersVillageHallActivity.class))
        );
        binding.medicineButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), AlarmMainActivity.class)));
        binding.qrCheck.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), QrCodeScanActivity.class)));
        binding.mealFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                DetailMealFriends diningFriend = checkMyDiningFriend();

                if (diningFriend == null) {
                    intent = new Intent(getApplicationContext(), MealFriendActivity.class);
                } else {
                    userDFId = diningFriend.getId();

                    if (diningFriend.getPhoneNumber().equals(HomeActivity.userInfo.getPhoneNumber())) {
                        intent = new Intent(getApplicationContext(), HostMealFriendActivity.class);
                    } else {
                        intent = new Intent(getApplicationContext(), ApplicantMealFriendActivity.class);
                    }
                }
                startActivity(intent);
            }
        });

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.logout) {
                    SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("id");
                    editor.remove("pw");
                    editor.apply();

                    userInfo = null;
                    userDFId = -1L;

                    finish();
                }

                if (id == R.id.setting) {
                    Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                    startActivity(intent);
                }

                return false;
            }
        });

        binding.attendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attendance();
            }
        });
    }

    private void attendance() {
        GetHttpClient httpclient = new GetHttpClient("restapi/userstate/" + userInfo.getId());
        Thread th = new Thread(httpclient);
        th.start();
        String result = null;

        long start = System.currentTimeMillis();

        while (result == null) {
            result = httpclient.getResult();
            long end = System.currentTimeMillis();
            if (end - start > 3000) {
                return;
            }
        }
        if (result.equals("ok")) {
            binding.attendanceText.setText("출석체크 완료");
            binding.attendanceButton.setEnabled(false);
            binding.attendanceButton.setBackgroundResource(R.drawable.round_enabled);
        }
    }

    private void setFriendList() {
        userInfoStateList.clear();
        friendListAdapter = new FriendListAdapter(this, userInfoStateList);
        binding.friendListRecyclerView.setAdapter(friendListAdapter);

        GetHttpClient httpclient = new GetHttpClient("restapi/userstate/detail/byteamid/" + userInfo.getTeamId());
        Thread th = new Thread(httpclient);
        th.start();
        String result = null;

        long start = System.currentTimeMillis();

        while (result == null) {
            result = httpclient.getResult();
            long end = System.currentTimeMillis();
            if (end - start > 3000) {
                return;
            }
        }
        Gson gson = new Gson();

        UserInfoState[] admins = gson.fromJson(result.toString(), UserInfoState[].class);
        userInfoStateList = Arrays.asList(admins);

        List<UserInfoState> list = new ArrayList<>();

        for (UserInfoState userInfoState : userInfoStateList) {
            if (!userInfoState.getId().equals(userInfo.getId())) {
                list.add(userInfoState);
            } else {
                StringBuffer sb = new StringBuffer();
                sb.append(userInfoState.getDate());

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime dateTime = LocalDateTime.parse(sb.replace(10, 11, " "), formatter);
                LocalDate today = LocalDate.now();

                if (dateTime.getYear() == today.getYear() && dateTime.getMonthValue() == today.getMonthValue() && dateTime.getDayOfMonth() == today.getDayOfMonth()) {
                    binding.attendanceText.setText("출석체크 완료");
                    binding.attendanceButton.setEnabled(false);
                    binding.attendanceButton.setBackgroundResource(R.drawable.round_enabled);
                }
            }
        }

        friendListAdapter = new FriendListAdapter(this, list);
        binding.friendListRecyclerView.setAdapter(friendListAdapter);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
            return;
        }

        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
            toast.cancel();
            moveTaskToBack(true);
            finishAndRemoveTask();

            System.exit(0);
        }
    }

    private void setUserLocation() {
        if (checkLocationServicesStatus()) {
            checkRunTimePermission();
        } else {
            showDialogForLocationServiceSetting();
        }

        gpsTracker = new GpsTracker(MainActivity.this);

        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();

        postData(latitude, longitude);
    }

    private void postData(double latitude, double longitude) {
        try {
            RequestBody formBody = new FormBody.Builder()
                    .add("lat", String.valueOf(latitude))
                    .add("lon", String.valueOf(longitude))
                    .build();

            String request = "restapi/userslocation/update?userId=" + userInfo.getId();

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
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        super.onRequestPermissionsResult(permsRequestCode, permissions, grandResults);
        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }

            if (check_result) {
                //위치 값을 가져올 수 있음
            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(MainActivity.this, "위치 접근 권한이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "위치 접근 권한이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    void checkRunTimePermission() {

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음


        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(MainActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);

            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }

    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case GPS_ENABLE_REQUEST_CODE:
                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 돼있음");
                        checkRunTimePermission();
                        return;
                    }
                }
                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private DetailMealFriends checkMyDiningFriend() {

        String request = "restapi/dining-friends/select/byuserid/" + HomeActivity.userInfo.getId();
        GetHttpClient httpClient = new GetHttpClient(request);
        Thread th = new Thread(httpClient);
        th.start();
        String result = null;

        long start = System.currentTimeMillis();

        while (result == null) {
            result = httpClient.getResult();
            long end = System.currentTimeMillis();
            if (end - start > 3000) {
                return null;
            }
        }

        Gson gson = new Gson();

        return gson.fromJson(result, DetailMealFriends.class);
    }

}
