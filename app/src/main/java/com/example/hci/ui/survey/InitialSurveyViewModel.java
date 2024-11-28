package com.example.hci.ui.survey;

import androidx.lifecycle.ViewModel;

public class InitialSurveyViewModel extends ViewModel {
    private boolean[] answers;

    public InitialSurveyViewModel() {
        answers = new boolean[10]; // 설문 문항 수에 맞게 조정
    }

    public void setAnswer(int index, boolean answer) {
        answers[index] = answer;
    }

    public boolean getAnswer(int index) {
        return answers[index];
    }

    public boolean shouldStopSurvey() {
        // 설문 중단 조건 체크 로직
        int yesCount = 0;
        for (boolean answer : answers) {
            if (answer) yesCount++;
        }
        return yesCount < 2; // 예시: 'Yes' 응답이 2개 미만이면 중단
    }
} 