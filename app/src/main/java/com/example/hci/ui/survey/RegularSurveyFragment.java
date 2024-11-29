package com.example.hci.ui.survey;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.hci.R;
import com.example.hci.databinding.FragmentRegularSurveyBinding;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

/**
 * 상시 설문을 처리하는 Fragment
 * 사용자의 현재 상태를 평가하고 데이터를 수집
 */
public class RegularSurveyFragment extends Fragment {
    private FragmentRegularSurveyBinding binding;
    private RegularSurveyViewModel viewModel;
    private static final String SURVEY_DATA_KEY = "regular_survey_data";
    
    // 모든 설문 데이터를 저장하는 정적 리스트
    // 백엔드 연동 시 이 데이터를 서버로 전송하면 됨
    private static List<RegularSurveyData> surveyDataList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                            ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegularSurveyBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(RegularSurveyViewModel.class);

        setupInputFilters();
        setupSubmitButton();
        return binding.getRoot();
    }

    /**
     * 입력값 필터 설정
     * - 수면 시간: 0-24시간
     * - 사회활동 일수: 0-7일
     */
    private void setupInputFilters() {
        binding.sleepHours.setFilters(new InputFilter[]{
            new InputFilterMinMax(0, 24)
        });
        
        binding.socialDays.setFilters(new InputFilter[]{
            new InputFilterMinMax(0, 7)
        });
    }

    /**
     * 제출 버튼 설정
     * 입력값 검증 후 데이터 저장
     */
    private void setupSubmitButton() {
        binding.submitButton.setOnClickListener(v -> {
            if (validateInputs()) {
                collectAndSaveData();
            }
        });
    }

    /**
     * 모든 입력값 검증
     * 필수 입력 항목이 모두 채워졌는지 확인
     */
    private boolean validateInputs() {
        // 수면 상태 검증
        if (binding.wakeupDifficultyGroup.getCheckedRadioButtonId() == -1) {
            showError("1-1. 기상 어려움 정도를 선택해주세요.");
            return false;
        }

        String sleepHours = binding.sleepHours.getText().toString();
        if (sleepHours.isEmpty()) {
            showError("1-2. 수면 시간을 입력해주세요.");
            return false;
        }

        // 사회적 활동 검증
        if (binding.socialAnxietyGroup.getCheckedRadioButtonId() == -1) {
            showError("2-1. 사회적 활동 거부감 정도를 선택해주세요.");
            return false;
        }

        String socialDays = binding.socialDays.getText().toString();
        if (socialDays.isEmpty()) {
            showError("2-2. 사회적 활동 일수를 입력해주세요.");
            return false;
        }

        // 기분 상태 검증
        if (binding.depressionGroup.getCheckedRadioButtonId() == -1) {
            showError("3-1. 우울감 정도를 선택해주세요.");
            return false;
        }

        // 체중 변화 검증
        if (binding.weightGroup.getCheckedRadioButtonId() == -1) {
            showError("4. 체중 변화를 선택해주세요.");
            return false;
        }

        // 식욕 변화 검증
        if (binding.appetiteGroup.getCheckedRadioButtonId() == -1) {
            showError("5. 식욕 변화를 선택해주세요.");
            return false;
        }

        // 에너지 레벨 검증
        if (binding.energyGroup.getCheckedRadioButtonId() == -1) {
            showError("6. 에너지 레벨을 선택해주세요.");
            return false;
        }

        return true;
    }

    /**
     * 설문 데이터 수집 및 저장
     * 
     * 데이터 흐름:
     * 1. UI에서 사용자 입력 수집
     * 2. RegularSurveyData 객체 생성
     * 3. 정적 리스트에 저장
     * 
     * 백엔드 연동 시 고려사항:
     * - REST API 엔드포인트 구현 (/api/surveys/regular)
     * - 데이터 형식: JSON
     * - HTTP 메서드: POST
     * 
     * 예시 JSON 형식:
     * {
     *   "wakeupDifficulty": "약간 어려움",
     *   "sleepHours": 7,
     *   "socialAnxiety": "없음",
     *   "socialDays": 5,
     *   "depressionLevel": "약간 우울",
     *   "weightChange": "평소와 비슷하다",
     *   "appetiteChange": "그대로다",
     *   "energyLevel": "보통",
     *   "submissionDate": "2024-03-21T14:30:00Z"
     * }
     */
    private void collectAndSaveData() {
        // 데이터 수집
        String wakeupDifficulty = getSelectedRadioText(binding.wakeupDifficultyGroup);
        int sleepHours = Integer.parseInt(binding.sleepHours.getText().toString());
        String socialAnxiety = getSelectedRadioText(binding.socialAnxietyGroup);
        int socialDays = Integer.parseInt(binding.socialDays.getText().toString());
        String depressionLevel = getSelectedRadioText(binding.depressionGroup);
        String weightChange = getSelectedRadioText(binding.weightGroup);
        String appetiteChange = getSelectedRadioText(binding.appetiteGroup);
        String energyLevel = getSelectedRadioText(binding.energyGroup);

        // RegularSurveyData 객체 생성
        RegularSurveyData surveyData = new RegularSurveyData(
            wakeupDifficulty, sleepHours, socialAnxiety, socialDays,
            depressionLevel, weightChange, appetiteChange, energyLevel
        );

        // 데이터 저장
        surveyDataList.add(surveyData);

        // TODO: 백엔드 연동 시 추가할 예시코드
        // ApiService.submitRegularSurvey(surveyData)
        //     .enqueue(new Callback<Response>() {
        //         @Override
        //         public void onSuccess(Response response) {
        //             showSuccessDialog();
        //         }
        //         
        //         @Override
        //         public void onError(Error error) {
        //             showError("서버 전송 실패: " + error.getMessage());
        //         }
        //     });

        showSuccessDialog();
    }

    /**
     * RadioGroup에서 선택된 텍스트 가져오기
     */
    private String getSelectedRadioText(RadioGroup group) {
        int selectedId = group.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton radioButton = group.findViewById(selectedId);
            return radioButton.getText().toString();
        }
        return "";
    }

    /**
     * 성공 다이얼로그 표시
     * 설문 제출 완료 후 홈 화면으로 이동
     */
    private void showSuccessDialog() {
        new AlertDialog.Builder(requireContext())
            .setTitle("제출 완료")
            .setMessage("상시 설문이 성공적으로 제출되었습니다.")
            .setPositiveButton("확인", (dialog, which) -> {
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_regularSurveyFragment_to_navigation_home);
            })
            .setCancelable(false)
            .show();
    }

    /**
     * 에러 메시지 표시
     */
    private void showError(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 저장된 설문 데이터 가져오기
     * 다른 Fragment나 Activity에서 접근 가능
     */
    public static List<RegularSurveyData> getSurveyDataList() {
        return surveyDataList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
} 