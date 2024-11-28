package com.example.hci.ui.survey;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RegularSurveyViewModel extends ViewModel {
    private final MutableLiveData<String> wakeupDifficulty = new MutableLiveData<>();
    private final MutableLiveData<Integer> sleepHours = new MutableLiveData<>();
    private final MutableLiveData<String> moodLevel = new MutableLiveData<>();

    public void setWakeupDifficulty(String difficulty) {
        wakeupDifficulty.setValue(difficulty);
    }

    public void setSleepHours(int hours) {
        sleepHours.setValue(hours);
    }

    public void setMoodLevel(String mood) {
        moodLevel.setValue(mood);
    }

    public void submitSurvey() {
        // 설문 데이터를 저장하거나 서버로 전송하는 로직
        RegularSurveyData surveyData = new RegularSurveyData(
            wakeupDifficulty.getValue(),
            sleepHours.getValue(),
            moodLevel.getValue()
        );

        // TODO: 데이터 저장 또는 전송 로직 구현
    }
} 