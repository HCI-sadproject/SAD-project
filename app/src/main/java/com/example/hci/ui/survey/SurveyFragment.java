package com.example.hci.ui.survey;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hci.databinding.FragmentSurveyBinding;
import java.util.List;
import java.util.ArrayList;
import com.example.hci.R;

public class SurveyFragment extends Fragment {

    private FragmentSurveyBinding binding;
    private SurveyViewModel surveyViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        surveyViewModel = new ViewModelProvider(this).get(SurveyViewModel.class);
        binding = FragmentSurveyBinding.inflate(inflater, container, false);
        
        setupAllQuestions();
        setupSubmitButton();
        
        return binding.getRoot();
    }

    private void setupAllQuestions() {
        // 1. 초기 스크리닝 질문
        addSectionTitle("지난 10년간의 경험");
        String[] initialQuestions = {
            "하루 중 많은 시간에 우울감과 무기력감을 느낀 적이 있다.",
            "평소 나를 즐겁게 하던 것들이 더이상 행복감을 주지 못한 적이 있다.",
            "평소 나를 즐겁게 하던 것들에 흥미를 잃은 적이 있다.",
            "활동을 많이 하지 않았음에도 아주 피곤하거나 마모된 것 같은 기분을 느낀 적이 있다.",
            "평소보다 낮은 에너지 레벨을 가진 적이 있다."
        };
        addYesNoQuestions("initial", initialQuestions);

        // 2. 지속 기간 확인
        addSectionTitle("증상 지속 기간");
        addYesNoQuestion("duration", "앞에서 '예'라고 대답했던 항목 중 2주 이상 지속된 항목이 하나 이상 존재하나요?");

        // 3. 동반 증상
        addSectionTitle("동반 증상");
        String[] symptoms = {
            "자신감 또는 자존감이 약화됨을 느꼈다",
            "죄책감 또는 수치심을 느꼈다.",
            "집중력 감퇴를 느꼈다.",
            "의사결정 혹은 명확한 생각이 필요한 상황에 어려움을 느꼈다.",
            "심하게 긴장하거나 안절부절 못하는 상태라고 느꼈다.",
            "움직임이 심하게 느려짐을 느꼈다.",
            "사는 것에 의미가 없다고 느꼈다.",
            "죽음을 생각했다.",
            "식욕의 변화함을 느꼈다.",
            "수면 패턴이 변화함을 느꼈다.",
            "체중이 3kg이상 줄거나 늘었다."
        };
        addYesNoQuestions("symptoms", symptoms);

        // 4. 발생 빈도
        addSectionTitle("발생 빈도");
        String[] frequencies = {"1번", "2번", "3번", "4번", "5번", "5번 이상"};
        addRadioButtonGroup("frequency", 
            "이런 불편함을 겪은 기간이 지난 10년간 몇 차례나 있었다고 생각하시나요?", 
            frequencies);

        // 5. 계절성 여부
        addSectionTitle("계절성 여부");
        addYesNoQuestion("seasonal", 
            "이 기간이 특정 계절에 나타나는 것 같다고 느끼시나요?");

        // 6. 우울 계절
        addSectionTitle("우울 에피소드와 계절의 연관성");
        String[] seasons = {"봄", "여름", "가을", "겨울"};
        addRadioButtonGroup("depression_season", 
            "어떤 계절에 우울 기간이 가장 흔하게 발생했나요?", 
            seasons);

        // 7. 우울 기간 횟수
        addSectionTitle("우울 기간 발생 횟수");
        String[] counts = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        addGridRadioGroup("depression_count", 
            "지난 10년간 얼마나 많은 우울 기간(2주 이상)이 그 계절에 나타났나요?", 
            counts, 5);

        // 8. 최근 2년간 우울 기간
        addSectionTitle("최근 2년간 우울 기간");
        String[] recentCounts = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        addRadioButtonGroup("recent_depression", 
            "지난 2년 동안 우울기간(2주 이상)이 몇번 나타났나요?", 
            recentCounts);

        // 9. 2년 연속 발생 여부
        addYesNoQuestion("consecutive", 
            "이 계절에 발생한 우울 기간이 2년 연속 발생한 적이 있나요?");

        // 10. GSS 평가
        addSectionTitle("계절에 따른 변화 정도 (GSS)");
        String[] gssItems = {
            "수면 길이",
            "사회적 활동",
            "기분(전반적인 정신 건강 상태 혹은 행복감)",
            "체중",
            "식욕",
            "에너지 레벨"
        };
        String[] gssLevels = {
            "변화 없음",
            "약간 변화함",
            "보통 수준의 변화",
            "눈에 띄는 변화",
            "극적인 변화"
        };
        for (String item : gssItems) {
            addRadioButtonGroup("gss_" + item.replaceAll("\\s+", "_"), 
                item, gssLevels);
        }

        // 11. 월별 변화
        addSectionTitle("월별 변화");
        String[] monthlyItems = {
            "가장 기분 좋은 달",
            "가장 체중이 높은 달",
            "가장 사회활동에 적극적인 달",
            "가장 적게 자는 달",
            "가장 많이 먹는 달",
            "가장 체중이 많이 주는 달",
            "가장 사회활동에 소극적인 달",
            "가장 기분 안 좋은 달",
            "가장 적게 먹는 달",
            "가장 많이 자는 달"
        };
        for (String item : monthlyItems) {
            addMonthSelector(item);
        }

        // 12. 연간 체중 변화
        addSectionTitle("연간 체중 변화");
        String[] weightChanges = {
            "2kg 미만",
            "2kg 이상 3kg 미만",
            "3kg 이상 4kg 미만",
            "4kg 이상 6kg 미만",
            "6kg 이상 7kg 미만",
            "7kg 이상"
        };
        addRadioButtonGroup("weight_change", 
            "일 년 동안 체중이 얼만큼 변화하나요?", 
            weightChanges);

        // 13. 계절별 수면 시간
        addSectionTitle("계절별 수면 시간");
        String[] sleepHours = new String[19];
        for (int i = 0; i <= 18; i++) {
            sleepHours[i] = i == 18 ? "18시간 이상" : i + "시간";
        }
        String[] seasonNames = {"겨울", "봄", "여름", "가을"};
        for (String season : seasonNames) {
            addRadioButtonGroup("sleep_" + season, 
                season + "철에 하루 평균 몇 시간을 주무시나요? (낮잠 포함)", 
                sleepHours);
        }

        // 14. 식성 변화
        addSectionTitle("식성 변화");
        addYesNoQuestionWithFollowUp("diet_change", 
            "식성이 계절에 따라 바뀌는 경향이 있나요?",
            "구체적으로 어떻게 바뀌나요?");

        // 15. 문제 인식
        addSectionTitle("변화에 대한 인식");
        addYesNoQuestionWithSeverity("problem_recognition", 
            "계절에 따른 변화가 문제로 느껴지시나요?",
            new String[]{"작은 문제", "보통 수준의 문제", "눈에 띄는 문제", 
                        "극심한 문제", "장애 수준의 문제"});
    }

    private void setupSubmitButton() {
        binding.submitButton.setOnClickListener(v -> {
            if (validateAllAnswers()) {
                processResults();
            } else {
                showValidationError();
            }
        });
    }

    private boolean validateAllAnswers() {
        // 필수 응답 확인 로직 구현
        return true;
    }

    private void processResults() {
        if (surveyViewModel.shouldStopSurvey()) {
            showStopSurveyDialog();
        } else {
            showSADProgramDialog();
        }
    }

    private void showStopSurveyDialog() {
        new AlertDialog.Builder(requireContext())
            .setTitle("설문 결과")
            .setMessage("SAD 증상이 심각하지 않습니다.\nSAD 방지를 위해 걷거나 일기를 작성해 보아요.")
            .setPositiveButton("일기 작성하기", (dialog, which) -> {
                navigateToDiary();
            })
            .setCancelable(false)
            .show();
    }

    private void showSADProgramDialog() {
        new AlertDialog.Builder(requireContext())
            .setTitle("설문 결과")
            .setMessage("SAD 증상이 있는 것으로 보입니다.\n함께 SAD를 해결해나가보아요.")
            .setPositiveButton("시작하기", (dialog, which) -> {
                navigateToSADProgram();
            })
            .setCancelable(false)
            .show();
    }

    private void navigateToDiary() {
        // 일기 작성 화면으로 이동
    }

    private void navigateToSADProgram() {
        // SAD 프로그램 화면으로 이동
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void addSectionTitle(String title) {
        TextView titleView = new TextView(getContext());
        titleView.setText(title);
        titleView.setTextSize(18);
        titleView.setTextColor(getResources().getColor(android.R.color.black));
        titleView.setTypeface(null, Typeface.BOLD);
        titleView.setPadding(0, 32, 0, 16);
        binding.questionContainer.addView(titleView);
    }

    private void addYesNoQuestions(String idPrefix, String[] questions) {
        for (int i = 0; i < questions.length; i++) {
            addYesNoQuestion(idPrefix + "_" + i, questions[i]);
        }
    }

    private void addYesNoQuestion(String id, String question) {
        TextView questionView = new TextView(getContext());
        questionView.setText(question);
        binding.questionContainer.addView(questionView);

        RadioGroup group = new RadioGroup(getContext());
        group.setOrientation(RadioGroup.HORIZONTAL);

        RadioButton yesButton = new RadioButton(getContext());
        yesButton.setText("예");
        group.addView(yesButton);

        RadioButton noButton = new RadioButton(getContext());
        noButton.setText("아니오");
        group.addView(noButton);

        binding.questionContainer.addView(group);
        addSpacing();
    }

    private void addRadioButtonGroup(String id, String question, String[] options) {
        TextView questionView = new TextView(getContext());
        questionView.setText(question);
        binding.questionContainer.addView(questionView);

        RadioGroup group = new RadioGroup(getContext());
        for (String option : options) {
            RadioButton button = new RadioButton(getContext());
            button.setText(option);
            group.addView(button);
        }

        binding.questionContainer.addView(group);
        addSpacing();
    }

    private void addGridRadioGroup(String id, String question, String[] options, int columnsCount) {
        TextView questionView = new TextView(getContext());
        questionView.setText(question);
        binding.questionContainer.addView(questionView);

        GridLayout grid = new GridLayout(getContext());
        grid.setColumnCount(columnsCount);
        grid.setRowCount((options.length + columnsCount - 1) / columnsCount);

        for (String option : options) {
            RadioButton button = new RadioButton(getContext());
            button.setText(option);
            grid.addView(button);
        }

        binding.questionContainer.addView(grid);
        addSpacing();
    }

    private void addSpacing() {
        View spacing = new View(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, 
            (int) (16 * getResources().getDisplayMetrics().density)
        );
        spacing.setLayoutParams(params);
        binding.questionContainer.addView(spacing);
    }

    private void showValidationError() {
        new AlertDialog.Builder(requireContext())
            .setTitle("알림")
            .setMessage("모든 질문에 답변해 주세요.")
            .setPositiveButton("확인", null)
            .show();
    }

    private void addMonthSelector(String title) {
        TextView titleView = new TextView(getContext());
        titleView.setText(title);
        styleQuestionText(titleView);
        binding.questionContainer.addView(titleView);

        GridLayout grid = new GridLayout(getContext());
        grid.setColumnCount(3);
        grid.setRowCount(5);

        String[] months = {"1월", "2월", "3월", "4월", "5월", "6월", 
                          "7월", "8월", "9월", "10월", "11월", "12월"};
        
        // 선택된 버튼을 추적하기 위한 리스트
        List<Button> monthButtons = new ArrayList<>();
        
        for (String month : months) {
            Button btn = new Button(getContext());
            btn.setText(month);
            btn.setTextSize(14);
            btn.setBackgroundResource(android.R.drawable.btn_default);
            
            // 버튼 크기와 마진 설정
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = (int) (100 * getResources().getDisplayMetrics().density);
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.setMargins(8, 8, 8, 8);
            btn.setLayoutParams(params);

            // 선택 상태 추적을 위한 태그
            btn.setTag(false);

            // 클릭 리스너 설정
            btn.setOnClickListener(v -> {
                boolean isSelected = (boolean) btn.getTag();
                if (isSelected) {
                    // 선택 해제
                    btn.setBackgroundResource(android.R.drawable.btn_default);
                    btn.setTextColor(Color.BLACK);
                    btn.setTag(false);
                } else {
                    // 선택
                    btn.setBackgroundResource(com.example.hci.R.color.purple_500);
                    btn.setTextColor(Color.WHITE);
                    btn.setTag(true);
                }
            });

            monthButtons.add(btn);
            grid.addView(btn);
        }

        Button noneBtn = new Button(getContext());
        noneBtn.setText("특정 월 없음");
        noneBtn.setTextSize(14);
        noneBtn.setBackgroundResource(android.R.drawable.btn_default);

        // 특정 월 없음 버튼 크기와 마진 설정
        GridLayout.LayoutParams noneParams = new GridLayout.LayoutParams();
        noneParams.width = GridLayout.LayoutParams.MATCH_PARENT;
        noneParams.height = GridLayout.LayoutParams.WRAP_CONTENT;
        noneParams.setMargins(8, 16, 8, 8);
        noneParams.columnSpec = GridLayout.spec(0, 3);
        noneBtn.setLayoutParams(noneParams);

        // 선택 상태 추적을 위한 태그
        noneBtn.setTag(false);

        // 특정 월 없음 버튼 클릭 리스너
        noneBtn.setOnClickListener(v -> {
            boolean isSelected = (boolean) noneBtn.getTag();
            if (isSelected) {
                // 선택 해제
                noneBtn.setBackgroundResource(android.R.drawable.btn_default);
                noneBtn.setTextColor(Color.BLACK);
                noneBtn.setTag(false);
            } else {
                // 선택
                noneBtn.setBackgroundResource(com.example.hci.R.color.purple_500);
                noneBtn.setTextColor(Color.WHITE);
                noneBtn.setTag(true);
                
                // 다른 모든 월 버튼 선택 해제
                for (Button monthBtn : monthButtons) {
                    monthBtn.setBackgroundResource(android.R.drawable.btn_default);
                    monthBtn.setTextColor(Color.BLACK);
                    monthBtn.setTag(false);
                }
            }
        });

        grid.addView(noneBtn);
        binding.questionContainer.addView(grid);
        addSpacing();
    }

    private void addYesNoQuestionWithFollowUp(String id, String question, String followUpPrompt) {
        addYesNoQuestion(id, question);
        EditText followUp = new EditText(getContext());
        followUp.setHint(followUpPrompt);
        followUp.setVisibility(View.GONE);
        binding.questionContainer.addView(followUp);
        addSpacing();
    }

    private void addYesNoQuestionWithSeverity(String id, String question, String[] severityLevels) {
        addYesNoQuestion(id, question);
        RadioGroup severityGroup = new RadioGroup(getContext());
        severityGroup.setVisibility(View.GONE);
        
        for (String level : severityLevels) {
            RadioButton button = new RadioButton(getContext());
            button.setText(level);
            severityGroup.addView(button);
        }
        
        binding.questionContainer.addView(severityGroup);
        addSpacing();
    }

    private void styleQuestionText(TextView textView) {
        textView.setTextSize(16);
        textView.setTextColor(getResources().getColor(android.R.color.darker_gray));
        textView.setPadding(0, 16, 0, 8);
    }

    private void styleRadioButton(RadioButton button) {
        button.setTextSize(15);
        button.setPadding(8, 8, 8, 8);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 4, 0, 4);
        button.setLayoutParams(params);
    }

    private void styleButton(Button button) {
        button.setBackgroundResource(android.R.color.holo_blue_light);
        button.setTextColor(Color.WHITE);
        button.setTextSize(14);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.setMargins(8, 8, 8, 8);
        button.setLayoutParams(params);
    }
}
