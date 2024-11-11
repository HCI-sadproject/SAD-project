package com.example.hci.ui.survey;

import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Map;

public class SurveyViewModel extends ViewModel {

    private final Map<Integer, Integer> answers;

    public SurveyViewModel() {
        answers = new HashMap<>();
    }

    public void setAnswer(int questionId, int answer) {
        answers.put(questionId, answer);
    }

    public int getAnswer(int questionId) {
        return answers.getOrDefault(questionId, -1);
    }

    public boolean isAllQuestionsAnswered() {
        // 모든 질문에 답변했는지 확인
        return answers.size() == 3; // 총 3개의 질문
    }

    public void submitSurvey() {
        // 설문 제출 로직 구현
        // 예: 서버에 데이터 전송 또는 로컬 저장
    }
}
