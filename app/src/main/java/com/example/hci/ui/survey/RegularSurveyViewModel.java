package com.example.hci.ui.survey;

import androidx.lifecycle.ViewModel;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

public class RegularSurveyViewModel extends ViewModel {
    private String wakeupDifficulty;
    private int sleepHours;
    private String socialAnxiety;
    private int socialDays;
    private String depressionLevel;
    private String weightChange;
    private String appetiteChange;
    private String energyLevel;

    // Setters
    public void setWakeupDifficulty(String difficulty) {
        this.wakeupDifficulty = difficulty;
    }

    public void setSleepHours(int hours) {
        this.sleepHours = hours;
    }

    public void setSocialAnxiety(String anxiety) {
        this.socialAnxiety = anxiety;
    }

    public void setSocialDays(int days) {
        this.socialDays = days;
    }

    public void setDepressionLevel(String level) {
        this.depressionLevel = level;
    }

    public void setWeightChange(String weight) {
        this.weightChange = weight;
    }

    public void setAppetiteChange(String appetite) {
        this.appetiteChange = appetite;
    }

    public void setEnergyLevel(String energy) {
        this.energyLevel = energy;
    }

    public RegularSurveyData submitSurvey() {
        RegularSurveyData surveyData = new RegularSurveyData(
            wakeupDifficulty,
            sleepHours,
            socialAnxiety,
            socialDays,
            depressionLevel,
            weightChange,
            appetiteChange,
            energyLevel
        );
        
        // TODO: 데이터 저장 로직 구현
        return surveyData;
    }

    public boolean validateInputs() {
        return wakeupDifficulty != null &&
               sleepHours >= 0 && sleepHours <= 24 &&
               socialAnxiety != null &&
               socialDays >= 0 && socialDays <= 7 &&
               depressionLevel != null &&
               weightChange != null &&
               appetiteChange != null &&
               energyLevel != null;
    }
} 