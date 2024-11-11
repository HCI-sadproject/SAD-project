package com.example.hci.ui.survey;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hci.databinding.FragmentSurveyBinding;

public class SurveyFragment extends Fragment {

    private FragmentSurveyBinding binding;
    private SurveyViewModel surveyViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        surveyViewModel = new ViewModelProvider(this).get(SurveyViewModel.class);
        binding = FragmentSurveyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // 각 RadioGroup에 리스너 설정
        setupRadioGroupListener(binding.question1Group, 1);
        setupRadioGroupListener(binding.question2Group, 2);
        setupRadioGroupListener(binding.question3Group, 3);

        // 제출 버튼 리스너
        binding.submitButton.setOnClickListener(v -> {
            if (surveyViewModel.isAllQuestionsAnswered()) {
                surveyViewModel.submitSurvey();
                // 제출 완료 메시지 표시
                Toast.makeText(getContext(), "설문이 제출되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                // 미답변 항목 있음을 알림
                Toast.makeText(getContext(), "모든 질문에 답해주세요.", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    private void setupRadioGroupListener(RadioGroup group, int questionId) {
        group.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            int answer = -1;
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                if (radioGroup.getChildAt(i).getId() == checkedId) {
                    answer = i;
                    break;
                }
            }
            if (answer >= 0) {
                surveyViewModel.setAnswer(questionId, answer);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
