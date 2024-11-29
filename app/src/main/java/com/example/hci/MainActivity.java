package com.example.hci;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.annotation.NonNull;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

// 알림 관련 클래스 import
import com.example.hci.notification.AlarmScheduler;
import com.example.hci.notification.NotificationHelper;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.hci.databinding.ActivityMainBinding;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AlarmScheduler alarmScheduler;
    private NotificationHelper notificationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_health, R.id.navigation_survey, R.id.navigation_home,
                R.id.navigation_diary, R.id.navigation_lighting)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController); // 변경된 부분

        // 알림 권한 요청 (Android 13 이상)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestNotificationPermission();
        }
        
        // 초기화
        alarmScheduler = new AlarmScheduler(this);
        notificationHelper = new NotificationHelper(this);
        
        // 일사량 알림 설정 (매일 오후 1-3시)
        alarmScheduler.scheduleSunlightReminder();
        
        // 테스트용: 5초 후에 알림 보내기
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            notificationHelper.showSurveyReminder();
        }, 5000); // 5초 후
    }

    private void requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this, 
                android.Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                1
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한이 허용됨
                Toast.makeText(this, "알림 권한이 허용되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                // 권한이 거부됨
                Toast.makeText(this, "알림 권한이 거부되었습니다. 설정에서 권한을 허용해주세요.", Toast.LENGTH_LONG).show();
            }
        }
    }

    // 설문 완료 시 호출할 메서드
    public void onSurveyCompleted() {
        // 기존 알림 취소
        alarmScheduler.cancelAllReminders();
        // 7일 후 알림 설정
        alarmScheduler.scheduleSurveyReminder();
    }

    // 설문 미완료 시 호출할 메서드 (7일 지났을 때)
    public void onSurveyOverdue() {
        // 매일 알림 활성화
        alarmScheduler.activateDailyReminder();
    }
    //알림시간 체크 로직ㄴ
    @Override
    protected void onResume() {
        super.onResume();
        
        // 마지막 설문 날짜 확인
        SharedPreferences prefs = getSharedPreferences("NotificationPrefs", Context.MODE_PRIVATE);
        long lastSurveyDate = prefs.getLong("lastSurveyDate", 0);
        
        // 7일이 지났는지 확인
        if (lastSurveyDate > 0) {
            long sevenDaysInMillis = 7 * 24 * 60 * 60 * 1000L;
            if (System.currentTimeMillis() - lastSurveyDate > sevenDaysInMillis) {
                // 7일이 지났으면 매일 알림 활성화
                onSurveyOverdue();
            }
        }
    }



}
