package com.example.hci.ui.survey;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hci.R;
import com.example.hci.databinding.FragmentInitialSurveyBinding;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

/**
 * SAD 초기 설문을 처리하는 Fragment
 * 사용자의 계절성 우울증(SAD) 증상을 평가하고 프로그램 필요 여부를 판단
 */
public class InitialSurveyFragment extends Fragment {
    private static final String TAG = "InitialSurveyFragment";
    private FragmentInitialSurveyBinding binding;
    private InitialSurveyViewModel viewModel;
    private Map<String, Button> selectedButtons = new HashMap<>();

    /**
     * Fragment 생명주기 메서드들
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                            ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInitialSurveyBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(InitialSurveyViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 모든 월별 그리드 처리
        setupMonthGrid("best_mood", view);
        setupMonthGrid("highest_weight", view);
        setupMonthGrid("most_social", view);
        setupMonthGrid("least_sleep", view);
        setupMonthGrid("most_eating", view);
        setupMonthGrid("most_weight_loss", view);
        setupMonthGrid("least_social", view);
        setupMonthGrid("worst_mood", view);
        setupMonthGrid("least_eating", view);
        setupMonthGrid("most_sleep", view);

        setupSubmitButton(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setupMonthGrid(String gridId, View rootView) {
        int gridLayoutId = getResources().getIdentifier(gridId + "_month_grid", "id", 
                requireContext().getPackageName());
        View gridLayout = rootView.findViewById(gridLayoutId);

        // 선택된 버튼들을 추적하기 위한 Set
        Set<Button> selectedButtons = new HashSet<>();
        
        // 모든 월 버튼에 대해 클릭 리스너 설정
        for (int i = 1; i <= 12; i++) {
            int buttonId = getResources().getIdentifier("month_" + i, "id", requireContext().getPackageName());
            Button monthButton = gridLayout.findViewById(buttonId);
            
            monthButton.setOnClickListener(v -> {
                // 월 버튼이 클릭되면 '특정 월 없음' 버튼은 선택 해제
                Button noSpecificMonth = gridLayout.findViewById(R.id.no_specific_month);
                noSpecificMonth.setSelected(false);
                
                Button button = (Button) v;
                // 버튼이 이미 선택되어 있는지 확인
                if (selectedButtons.contains(button)) {
                    // 선택 해제
                    button.setSelected(false);
                    selectedButtons.remove(button);
                } else {
                    // 선택
                    button.setSelected(true);
                    selectedButtons.add(button);
                }
            });
        }

        // '특정 월 없음' 버튼 처리
        Button noSpecificMonth = gridLayout.findViewById(R.id.no_specific_month);
        noSpecificMonth.setOnClickListener(v -> {
            // 모든 월 버튼 선택 해제
            for (Button button : selectedButtons) {
                button.setSelected(false);
            }
            selectedButtons.clear();
            // '특정 월 없음' 버튼 선택
            noSpecificMonth.setSelected(true);
        });
    }

    private Map<String, String> getSelectedMonths() {
        Map<String, String> results = new HashMap<>();
        for (Map.Entry<String, Button> entry : selectedButtons.entrySet()) {
            Button button = entry.getValue();
            if (button != null) {
                if (button.getId() == R.id.no_specific_month) {
                    results.put(entry.getKey(), "특정 월 없음");
                } else {
                    results.put(entry.getKey(), button.getText().toString());
                }
            } else {
                results.put(entry.getKey(), "");
            }
        }
        return results;
    }

    private void setupSubmitButton(View view) {
        Button submitButton = view.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(v -> {
            // 모든 문항이 답변되었는지 확인
            if (!checkAllQuestionsAnswered()) {
                showIncompleteAnswersDialog();
                return;
            }

            // SAD 판단 로직
            boolean isSAD = checkIfSAD();
            
            // 결과에 따른 다이얼로그 표시
            if (isSAD) {
                showSADPositiveDialog();
            } else {
                showSADNegativeDialog();
            }

            // 데이터 수집 및 전송
            collectAndSendData();
        });
    }

    private boolean checkAllQuestionsAnswered() {
        // 섹션 1 검사
        if (!checkSection1Answered()) return false;
        
        // 섹션 2 검사
        if (!isAnyRadioGroupAnswered(binding.durationQuestionGroup.getRoot())) return false;
        
        // 섹션 3 검사
        if (!checkSection3Answered()) return false;
        
        // 섹션 4 검사 (빈도)
        if (binding.frequencyQuestionGroup.getCheckedRadioButtonId() == -1) return false;
        
        // 나머지 섹션들 검사
        return checkRemainingQuestionsAnswered();
    }

    private boolean checkSection1Answered() {
        return isAnyRadioGroupAnswered(binding.question1Group.getRoot()) &&
               isAnyRadioGroupAnswered(binding.question2Group.getRoot()) &&
               isAnyRadioGroupAnswered(binding.question3Group.getRoot()) &&
               isAnyRadioGroupAnswered(binding.question4Group.getRoot()) &&
               isAnyRadioGroupAnswered(binding.question5Group.getRoot());
    }

    private boolean checkSection3Answered() {
        return isAnyRadioGroupAnswered(binding.section3Question1Group.getRoot()) &&
               isAnyRadioGroupAnswered(binding.section3Question2Group.getRoot()) &&
               isAnyRadioGroupAnswered(binding.section3Question3Group.getRoot()) &&
               isAnyRadioGroupAnswered(binding.section3Question4Group.getRoot()) &&
               isAnyRadioGroupAnswered(binding.section3Question5Group.getRoot()) &&
               isAnyRadioGroupAnswered(binding.section3Question6Group.getRoot()) &&
               isAnyRadioGroupAnswered(binding.section3Question7Group.getRoot()) &&
               isAnyRadioGroupAnswered(binding.section3Question8Group.getRoot()) &&
               isAnyRadioGroupAnswered(binding.section3Question9Group.getRoot()) &&
               isAnyRadioGroupAnswered(binding.section3Question10Group.getRoot()) &&
               isAnyRadioGroupAnswered(binding.section3Question11Group.getRoot());
    }

    private boolean checkIfSAD() {
        // 섹션 1: 하나라도 No(1)이면 SAD 아님
        boolean section1Result = !(getNoAnswerCount(binding.question1Group.getRoot()) > 0 ||
                                 getNoAnswerCount(binding.question2Group.getRoot()) > 0 ||
                                 getNoAnswerCount(binding.question3Group.getRoot()) > 0 ||
                                 getNoAnswerCount(binding.question4Group.getRoot()) > 0 ||
                                 getNoAnswerCount(binding.question5Group.getRoot()) > 0);

        // 섹션 2
        boolean section2Result = !isNoSelected(binding.durationQuestionGroup.getRoot());

        // 섹션 3: 하나라도 No(1)이면 SAD 아님
        boolean section3Result = !hasAnyNoAnswer();

        // 모든 조건을 만족해야 SAD로 판단
        return section1Result && section2Result && section3Result;
    }

    private boolean hasAnyNoAnswer() {
        return isNoSelected(binding.section3Question1Group.getRoot()) ||
               isNoSelected(binding.section3Question2Group.getRoot()) ||
               isNoSelected(binding.section3Question3Group.getRoot()) ||
               isNoSelected(binding.section3Question4Group.getRoot()) ||
               isNoSelected(binding.section3Question5Group.getRoot()) ||
               isNoSelected(binding.section3Question6Group.getRoot()) ||
               isNoSelected(binding.section3Question7Group.getRoot()) ||
               isNoSelected(binding.section3Question8Group.getRoot()) ||
               isNoSelected(binding.section3Question9Group.getRoot()) ||
               isNoSelected(binding.section3Question10Group.getRoot()) ||
               isNoSelected(binding.section3Question11Group.getRoot());
    }

    private void showIncompleteAnswersDialog() {
        new AlertDialog.Builder(requireContext())
            .setTitle("설문 미완료")
            .setMessage("모든 문항에 답변해 주세요.")
            .setPositiveButton("확인", null)
            .show();
    }

    private void showSADPositiveDialog() {
        new AlertDialog.Builder(requireContext())
            .setTitle("설문 결과")
            .setMessage("SAD 증상이 의심됩니다.\n함께 SAD를 극복해 나가보아요!")
            .setPositiveButton("확인", (dialog, which) -> {
                // TODO: SAD 프로그램으로 이동하는 로직 추가
            })
            .show();
    }

    private void showSADNegativeDialog() {
        new AlertDialog.Builder(requireContext())
            .setTitle("설문 결과")
            .setMessage("현재는 SAD 증상이 감지되지 않았습니다.\nSAD를 예방해 보아요!")
            .setPositiveButton("확인", (dialog, which) -> {
                // TODO: 예방 프로그램으로 이동하는 로직 추가
            })
            .show();
    }

    private boolean isNoSelected(RadioGroup group) {
        return group.getCheckedRadioButtonId() == R.id.radio_no;
    }

    private int getNoAnswerCount(RadioGroup group) {
        return isNoSelected(group) ? 1 : 0;
    }
//데이터 수집해 데이터 객체로 만들고 저장하고 서버로 전송한다.
    private void collectAndSendData() {
        // 설문 데이터를 담을 Map 생성
        Map<String, Object> surveyData = new HashMap<>();
        
        // 섹션 1 데이터 수집
        Map<String, Integer> section1Data = new HashMap<>();
        section1Data.put("question1", getYesNoValue(binding.question1Group.getRoot()));
        section1Data.put("question2", getYesNoValue(binding.question2Group.getRoot()));
        section1Data.put("question3", getYesNoValue(binding.question3Group.getRoot()));
        section1Data.put("question4", getYesNoValue(binding.question4Group.getRoot()));
        section1Data.put("question5", getYesNoValue(binding.question5Group.getRoot()));
        surveyData.put("section1", section1Data);
        
        // 섹션 2 데이터 수집
        surveyData.put("section2", getYesNoValue(binding.durationQuestionGroup.getRoot()));
        
        // 섹션 3 데이터 수집
        Map<String, Integer> section3Data = new HashMap<>();
        section3Data.put("question1", getYesNoValue(binding.section3Question1Group.getRoot()));
        section3Data.put("question2", getYesNoValue(binding.section3Question2Group.getRoot()));
        section3Data.put("question3", getYesNoValue(binding.section3Question3Group.getRoot()));
        section3Data.put("question4", getYesNoValue(binding.section3Question4Group.getRoot()));
        section3Data.put("question5", getYesNoValue(binding.section3Question5Group.getRoot()));
        section3Data.put("question6", getYesNoValue(binding.section3Question6Group.getRoot()));
        section3Data.put("question7", getYesNoValue(binding.section3Question7Group.getRoot()));
        section3Data.put("question8", getYesNoValue(binding.section3Question8Group.getRoot()));
        section3Data.put("question9", getYesNoValue(binding.section3Question9Group.getRoot()));
        section3Data.put("question10", getYesNoValue(binding.section3Question10Group.getRoot()));
        section3Data.put("question11", getYesNoValue(binding.section3Question11Group.getRoot()));
        surveyData.put("section3", section3Data);
        
        // 섹션 4 (빈도) 데이터 수집
        surveyData.put("frequency", getFrequencyValue());
        
        // 계절 선택 데이터 수집
        surveyData.put("selectedSeason", getSelectedSeason());
        
        // GSS 점수 데이터 수집
        Map<String, Integer> gssScores = new HashMap<>();
        gssScores.put("sleep", getGroupScore(binding.gssSleepGroup));
        gssScores.put("social", getGroupScore(binding.gssSocialGroup));
        gssScores.put("mood", getGroupScore(binding.gssMoodGroup));
        gssScores.put("weight", getGroupScore(binding.gssWeightGroup));
        gssScores.put("appetite", getGroupScore(binding.gssAppetiteGroup));
        gssScores.put("energy", getGroupScore(binding.gssEnergyGroup));
        surveyData.put("gssScores", gssScores);
        
        // 계절별 수면 시간 데이터 수집
        Map<String, String> sleepHours = new HashMap<>();
        sleepHours.put("winter", binding.winterSleepHours.getText().toString());
        sleepHours.put("spring", binding.springSleepHours.getText().toString());
        sleepHours.put("summer", binding.summerSleepHours.getText().toString());
        sleepHours.put("fall", binding.fallSleepHours.getText().toString());
        surveyData.put("sleepHours", sleepHours);
        
        // 월별 패턴 데이터 수집
        surveyData.put("monthlyPatterns", getSelectedMonths());
        
        // SAD 판정 결과 저장
        surveyData.put("isSAD", checkIfSAD());
        
        // 데이터 저장 또는 전송
        saveData(surveyData);
    }

    // Yes/No 값을 0/1로 변환
    private int getYesNoValue(RadioGroup group) {
        return isNoSelected(group) ? 1 : 0;
    }

    // 빈도 값 가져오기
    private int getFrequencyValue() {
        int checkedId = binding.frequencyQuestionGroup.getCheckedRadioButtonId();
        if (checkedId == R.id.frequency_1) return 1;
        if (checkedId == R.id.frequency_2) return 2;
        if (checkedId == R.id.frequency_3) return 3;
        if (checkedId == R.id.frequency_4) return 4;
        if (checkedId == R.id.frequency_5) return 5;
        if (checkedId == R.id.frequency_more_than_5) return 6;
        return 0;
    }

    // 선택된 계절 가져오기
    private String getSelectedSeason() {
        int checkedId = binding.seasonQuestionGroup.getCheckedRadioButtonId();
        if (checkedId == R.id.season_spring) return "봄";
        if (checkedId == R.id.season_summer) return "여름";
        if (checkedId == R.id.season_fall) return "가을";
        if (checkedId == R.id.season_winter) return "겨울";
        return "";
    }

    // 데이터 저장
    private void saveData(Map<String, Object> surveyData) {
        // TODO: 실제 데이터 저장 구현
        // 예: SharedPreferences, Room Database, 또는 서버로 전송
        
        // SharedPreferences 사용 예시
        SharedPreferences prefs = requireContext().getSharedPreferences("survey_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("latest_survey", new Gson().toJson(surveyData));
        editor.apply();
        
        // 로그로 확인
        Log.d(TAG, "설문 데이터 저장: " + new Gson().toJson(surveyData));
    }
//----------여기까지 데이터 보내기



    // 기존 메서드들 유지
    private boolean isAnyRadioGroupAnswered(RadioGroup group) {
        return group.getCheckedRadioButtonId() != -1;
    }

    private boolean isYesSelected(RadioGroup group) {
        return group.getCheckedRadioButtonId() == R.id.radio_yes;
    }

    private int getGroupScore(RadioGroup group) {
        int checkedId = group.getCheckedRadioButtonId();
        if (checkedId == -1) return 0;
        
        String idName = getResources().getResourceEntryName(checkedId);
        char lastChar = idName.charAt(idName.length() - 1);
        return Character.isDigit(lastChar) ? Character.getNumericValue(lastChar) : 0;
    }

    private void logGSSScores(RadioGroup[] groups) {
        Log.d(TAG, "각 GSS 항목 점수:");
        String[] labels = {"수면 길이", "사회적 활동", "기분", "체중", "식욕", "에너지 레벨"};
        for (int i = 0; i < groups.length; i++) {
            Log.d(TAG, labels[i] + ": " + getGroupScore(groups[i]));
        }
    }

    // 다이얼로그 표시 메서드들
    private void showIncompleteDialog() {
        new AlertDialog.Builder(requireContext())
            .setTitle("설문 미완료")
            .setMessage("설문조사를 하지 않은 부분이 있습니다. 모든 항목에 응답해주세요.")
            .setPositiveButton("확인", null)
            .show();
    }

    private void showSADProgramNeededDialog() {
        new AlertDialog.Builder(requireContext())
            .setTitle("검사 결과")
            .setMessage("SAD 프로그램이 필요할 수 있습니다.")
            .setPositiveButton("확인", null)
            .show();
    }

    private void showSADNotNeededDialog() {
        new AlertDialog.Builder(requireContext())
            .setTitle("검사 결과")
            .setMessage("SAD 프로그램이 필요하지 않습니다.")
            .setPositiveButton("확인", null)
            .show();
    }

    private void showErrorDialog() {
        new AlertDialog.Builder(requireContext())
            .setTitle("오류 발생")
            .setMessage("설문 처리 중 오류가 발생했습니다. 다시 시도해주세요.")
            .setPositiveButton("확인", null)
            .show();
    }
///데이터 0,1검사 페이지
    // 증상 체크 메서드들
    private boolean checkInitialSymptoms() {
        // 5개의 초기 증상 중 하나라도 Yes인지 확인
        return isYesSelected(binding.question1Group.getRoot()) ||
               isYesSelected(binding.question2Group.getRoot()) ||
               isYesSelected(binding.question3Group.getRoot()) ||
               isYesSelected(binding.question4Group.getRoot()) ||
               isYesSelected(binding.question5Group.getRoot());
    }

    private boolean checkTwoWeekSymptom() {
        return isYesSelected(binding.durationQuestionGroup.getRoot());
    }

    private boolean checkAdditionalSymptoms() {
        // 11개의 추가 증상 중 하나라도 Yes인지 확인
        return isYesSelected(binding.section3Question1Group.getRoot()) ||
               isYesSelected(binding.section3Question2Group.getRoot()) ||
               isYesSelected(binding.section3Question3Group.getRoot()) ||
               isYesSelected(binding.section3Question4Group.getRoot()) ||
               isYesSelected(binding.section3Question5Group.getRoot()) ||
               isYesSelected(binding.section3Question6Group.getRoot()) ||
               isYesSelected(binding.section3Question7Group.getRoot()) ||
               isYesSelected(binding.section3Question8Group.getRoot()) ||
               isYesSelected(binding.section3Question9Group.getRoot()) ||
               isYesSelected(binding.section3Question10Group.getRoot()) ||
               isYesSelected(binding.section3Question11Group.getRoot());
    }

    private boolean checkSeasonalPattern() {
        return isYesSelected(binding.seasonalQuestionGroup.getRoot());
    }

    // 응답 체크 메서드들
    private boolean checkTwoWeekQuestionAnswered() {
        return isAnyRadioGroupAnswered(binding.durationQuestionGroup.getRoot());
    }

    private boolean checkAdditionalQuestionsAnswered() {
        return isAnyRadioGroupAnswered(binding.section3Question1Group.getRoot()) &&
               isAnyRadioGroupAnswered(binding.section3Question2Group.getRoot()) &&
               isAnyRadioGroupAnswered(binding.section3Question3Group.getRoot()) &&
               isAnyRadioGroupAnswered(binding.section3Question4Group.getRoot()) &&
               isAnyRadioGroupAnswered(binding.section3Question5Group.getRoot()) &&
               isAnyRadioGroupAnswered(binding.section3Question6Group.getRoot()) &&
               isAnyRadioGroupAnswered(binding.section3Question7Group.getRoot()) &&
               isAnyRadioGroupAnswered(binding.section3Question8Group.getRoot()) &&
               isAnyRadioGroupAnswered(binding.section3Question9Group.getRoot()) &&
               isAnyRadioGroupAnswered(binding.section3Question10Group.getRoot()) &&
               isAnyRadioGroupAnswered(binding.section3Question11Group.getRoot());
    }

    private boolean checkSeasonalQuestionAnswered() {
        return isAnyRadioGroupAnswered(binding.seasonalQuestionGroup.getRoot());
    }

    private boolean checkRemainingQuestionsAnswered() {
        // 계절 선택 확인
        if (binding.seasonQuestionGroup.getCheckedRadioButtonId() == -1) return false;
        
        // GSS 점수 확인
        if (binding.gssSleepGroup.getCheckedRadioButtonId() == -1 ||
            binding.gssSocialGroup.getCheckedRadioButtonId() == -1 ||
            binding.gssMoodGroup.getCheckedRadioButtonId() == -1 ||
            binding.gssWeightGroup.getCheckedRadioButtonId() == -1 ||
            binding.gssAppetiteGroup.getCheckedRadioButtonId() == -1 ||
            binding.gssEnergyGroup.getCheckedRadioButtonId() == -1) return false;
        
        // 수면 시간 입력 확인
        if (binding.winterSleepHours.getText().toString().isEmpty() ||
            binding.springSleepHours.getText().toString().isEmpty() ||
            binding.summerSleepHours.getText().toString().isEmpty() ||
            binding.fallSleepHours.getText().toString().isEmpty()) return false;
        
        // 계절성 변화 문제 인식 확인
        if (binding.seasonalProblemGroup.getRoot().getCheckedRadioButtonId() == -1) return false;
        
        return true;
    }
} 