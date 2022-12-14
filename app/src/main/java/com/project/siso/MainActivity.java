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
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
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
import com.project.siso.home.HomeActivity;
import com.project.siso.httpserver.GetHttpClient;
import com.project.siso.httpserver.PostHttpClient;
import com.project.siso.mealfriend.ApplicantMealFriendActivity;
import com.project.siso.mealfriend.DetailMealFriends;
import com.project.siso.mealfriend.HostMealFriendActivity;
import com.project.siso.mealfriend.MealFriendActivity;
import com.project.siso.medicine.activities.AlarmMainActivity;
import com.project.siso.qr.QrCodeScanActivity;
import com.project.siso.setting.SettingActivity;
import com.project.siso.utilities.GpsTracker;
import com.project.siso.villagehall.UsersVillageHallActivity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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

        textView.setText(userInfo.getUserName() + "???");


        //????????? ????????????(????????? ??? ?????? ?????? : Toolbar type
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
            binding.attendanceText.setText("???????????? ??????");
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
                    binding.attendanceText.setText("???????????? ??????");
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
            toast = Toast.makeText(this, "\'??????\' ????????? ?????? ??? ???????????? ???????????????.", Toast.LENGTH_SHORT);
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
                    Toast.makeText(getApplicationContext(), "?????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
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

            // ?????? ????????? PERMISSIONS_REQUEST_CODE ??????, ????????? ????????? ???????????? ??????????????????

            boolean check_result = true;


            // ?????? ???????????? ??????????????? ???????????????.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }

            if (check_result) {
                //?????? ?????? ????????? ??? ??????
            } else {
                // ????????? ???????????? ????????? ?????? ????????? ??? ?????? ????????? ??????????????? ?????? ???????????????.2 ?????? ????????? ????????????.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(MainActivity.this, "?????? ?????? ????????? ?????????????????????. ?????? ?????? ???????????? ???????????? ??????????????????.", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "?????? ?????? ????????? ?????????????????????. ??????(??? ??????)?????? ???????????? ???????????? ?????????. ", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    void checkRunTimePermission() {

        //????????? ????????? ??????
        // 1. ?????? ???????????? ????????? ????????? ???????????????.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. ?????? ???????????? ????????? ?????????
            // ( ??????????????? 6.0 ?????? ????????? ????????? ???????????? ???????????? ????????? ?????? ????????? ?????? ???????????????.)


            // 3.  ?????? ?????? ????????? ??? ??????


        } else {  //2. ????????? ????????? ????????? ?????? ????????? ????????? ????????? ???????????????. 2?????? ??????(3-1, 4-1)??? ????????????.

            // 3-1. ???????????? ????????? ????????? ??? ?????? ?????? ????????????
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. ????????? ???????????? ?????? ?????????????????? ???????????? ????????? ????????? ???????????? ????????? ????????????.
                Toast.makeText(MainActivity.this, "??? ?????? ??????????????? ?????? ?????? ????????? ???????????????.", Toast.LENGTH_LONG).show();
                // 3-3. ??????????????? ????????? ????????? ?????????. ?????? ????????? onRequestPermissionResult?????? ???????????????.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);

            } else {
                // 4-1. ???????????? ????????? ????????? ??? ?????? ?????? ???????????? ????????? ????????? ?????? ?????????.
                // ?????? ????????? onRequestPermissionResult?????? ???????????????.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }

    //??????????????? GPS ???????????? ?????? ????????????
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("?????? ????????? ????????????");
        builder.setMessage("?????? ???????????? ???????????? ?????? ???????????? ???????????????.\n"
                + "?????? ????????? ???????????????????");
        builder.setCancelable(true);
        builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
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
                //???????????? GPS ?????? ???????????? ??????
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS ????????? ?????????");
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
