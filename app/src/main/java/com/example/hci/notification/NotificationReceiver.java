package com.example.hci.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        
        String action = intent.getAction();
        if (action != null) {
            switch (action) {
                case "SURVEY_REMINDER":
                case "DAILY_SURVEY_REMINDER":
                    notificationHelper.showSurveyReminder();
                    break;
                case "SUNLIGHT_REMINDER":
                    notificationHelper.showSunlightReminder();
                    break;
            }
        }
    }
} 