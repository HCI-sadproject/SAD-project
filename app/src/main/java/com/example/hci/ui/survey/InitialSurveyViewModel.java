package com.example.hci.ui.survey;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InitialSurveyViewModel extends ViewModel {
    // 설문 제목
    private final String SURVEY_TITLE = "SAD 초기 설문";
    
    // 섹션 1 제목
    private final String SECTION_1_TITLE = "지난 10년 간 아래 상황을 겪으신 적이 있나요? " +
            "해당 상황을 겪으셨으면 yes를 반대의 경우 no를 선택해주세요.";
    
    // 섹션 1 질문들
    private final String[] section1Questions = {
        "하루 중 많은 시간에 우울감과 무기력감을 느낀 적이 있다.",
        "평소 나를 즐겁게 하던 것들이 더이상 행복감을 주지 못한 적이 있다.",
        "평소 나를 즐겁게 하던 것들에 흥미를 잃은 적이 있다.",
        "활동을 많이 하지 않았음에도 아주 피곤하거나 마모된 것 같은 기분을 느낀 적이 있다.",
        "평소보다 낮은 에너지 레벨을 가진 적이 있다."
    };

    // 각 질문에 대한 응답을 저장할 LiveData 배열
    private final MutableLiveData<Boolean>[] answers;

    // 점수 계산을 위한 상수
    private static final int SCORE_YES = 1;
    private static final int SCORE_NO = 0;
    
    // SAD 위험도 레벨 상수
    private static final int HIGH_RISK_THRESHOLD = 4;
    private static final int MEDIUM_RISK_THRESHOLD = 2;

    @SuppressWarnings("unchecked")
    public InitialSurveyViewModel() {
        // MutableLiveData 배열 초기화
        answers = new MutableLiveData[section1Questions.length];
        for (int i = 0; i < answers.length; i++) {
            answers[i] = new MutableLiveData<>();
        }
    }

    // Getters
    public String getSurveyTitle() {
        return SURVEY_TITLE;
    }

    public String getSection1Title() {
        return SECTION_1_TITLE;
    }

    public String[] getSection1Questions() {
        return section1Questions;
    }

    public void setAnswer(int index, boolean answer) {
        if (index >= 0 && index < answers.length) {
            answers[index].setValue(answer);
        }
    }

    public MutableLiveData<Boolean> getAnswer(int index) {
        if (index >= 0 && index < answers.length) {
            return answers[index];
        }
        return null;
    }

    public int getQuestionCount() {
        return section1Questions.length;
    }

    // 총점 계산
    public int calculateTotalScore() {
        int totalScore = 0;
        for (MutableLiveData<Boolean> answer : answers) {
            if (Boolean.TRUE.equals(answer.getValue())) {
                totalScore += SCORE_YES;
            }
        }
        return totalScore;
    }

    // SAD 위험도 평가
    public String evaluateRiskLevel() {
        int totalScore = calculateTotalScore();
        
        if (totalScore >= HIGH_RISK_THRESHOLD) {
            return "높은 위험";
        } else if (totalScore >= MEDIUM_RISK_THRESHOLD) {
            return "중간 위험";
        } else {
            return "낮은 위험";
        }
    }

    // 상세 피드백 제공
    public String getDetailedFeedback() {
        int totalScore = calculateTotalScore();
        String riskLevel = evaluateRiskLevel();
        
        StringBuilder feedback = new StringBuilder();
        feedback.append("설문 결과: ").append(riskLevel).append("\n");
        feedback.append("총점: ").append(totalScore).append("/").append(answers.length).append("\n\n");

        switch (riskLevel) {
            case "높은 위험":
                feedback.append("SAD 증상이 의심됩니다. 전문가와 상담하시는 것을 권장드립니다.");
                break;
            case "중간 위험":
                feedback.append("SAD 초기 증상이 있을 수 있습니다. 지속적인 모니터링이 필요합니다.");
                break;
            case "낮은 위험":
                feedback.append("현재는 SAD 증상이 감지되지 않았습니다.\n" +
                              "하지만 계절성 우울증은 누구나 겪을 수 있으니 예방하는 것이 중요합니다.");
                break;
        }

        return feedback.toString();
    }

    public String getPreviousYesAnswers() {
        StringBuilder yesAnswers = new StringBuilder();
        for (int i = 0; i < section1Questions.length; i++) {
            if (Boolean.TRUE.equals(answers[i].getValue())) {
                yesAnswers.append("• ").append(section1Questions[i]).append("\n");
            }
        }
        return yesAnswers.toString();
    }

    public boolean hasAnyYesAnswers() {
        for (MutableLiveData<Boolean> answer : answers) {
            if (Boolean.TRUE.equals(answer.getValue())) {
                return true;
            }
        }
        return false;
    }
//
//    @Override
//    public String getDetailedFeedback() {
//        // 모든 답변이 'No'인 경우
//        if (!hasAnyYesAnswers()) {
//            return "현재는 SAD 증상이 감지되지 않았습니다.\n" +
//                   "하지만 계절성 우울증은 누구나 겪을 수 있으니 예방하는 것이 중요합니다.";
//        }
//
//        // 기존 피드백 로직 유지...
//        return feedback.toString();
//    }
} 