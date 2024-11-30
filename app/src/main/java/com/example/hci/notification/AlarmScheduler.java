package com.example.hci.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import java.util.Calendar;
import java.util.Random;
import com.example.hci.notification.NotificationReceiver;

public class AlarmScheduler {
    private final Context context;
    private final AlarmManager alarmManager;
    private final SharedPreferences prefs;
    private static final String PREFS_NAME = "NotificationPrefs";
    private static final String LAST_SURVEY_DATE = "lastSurveyDate";
    private static final String DAILY_NOTIFICATION_ACTIVE = "dailyNotificationActive";

    public AlarmScheduler(Context context) {
        this.context = context;
        this.alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        this.prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    // 설문 알림 스케줄링 (7일 후 저녁 9시)
    public void scheduleSurveyReminder() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 7); // 7일 후
        calendar.set(Calendar.HOUR_OF_DAY, 21); // 저녁 9시
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.setAction("SURVEY_REMINDER");
        
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
            context,
            1,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.getTimeInMillis(),
            pendingIntent
        );

        prefs.edit().putLong(LAST_SURVEY_DATE, System.currentTimeMillis()).apply();
        prefs.edit().putBoolean(DAILY_NOTIFICATION_ACTIVE, false).apply();
    }

    // 매일 알림 활성화 (설문 미완료시)
    public void activateDailyReminder() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 21); // 저녁 9시
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.setAction("DAILY_SURVEY_REMINDER");
        
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
            context,
            2,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.getTimeInMillis(),
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        );

        prefs.edit().putBoolean(DAILY_NOTIFICATION_ACTIVE, true).apply();
    }

    // 일사량 알림 설정 (매일 오후 1~3시 랜덤)
    public void scheduleSunlightReminder() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 13 + new Random().nextInt(3)); // 13~15시 랜덤
        calendar.set(Calendar.MINUTE, new Random().nextInt(60));
        calendar.set(Calendar.SECOND, 0);

        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.setAction("SUNLIGHT_REMINDER");
        
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
            context,
            3,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.getTimeInMillis(),
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        );
    }

    // 알림 취소
    public void cancelAllReminders() {
        Intent surveyIntent = new Intent(context, NotificationReceiver.class);
        surveyIntent.setAction("SURVEY_REMINDER");
        PendingIntent surveyPendingIntent = PendingIntent.getBroadcast(
            context, 1, surveyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
        alarmManager.cancel(surveyPendingIntent);

        Intent dailyIntent = new Intent(context, NotificationReceiver.class);
        dailyIntent.setAction("DAILY_SURVEY_REMINDER");
        PendingIntent dailyPendingIntent = PendingIntent.getBroadcast(
            context, 2, dailyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
        alarmManager.cancel(dailyPendingIntent);
    }
} 