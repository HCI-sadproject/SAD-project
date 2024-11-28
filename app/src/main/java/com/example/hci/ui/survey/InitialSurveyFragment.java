package com.example.hci.ui.survey;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.example.hci.R;
import com.example.hci.databinding.FragmentInitialSurveyBinding;

public class InitialSurveyFragment extends Fragment {
    private FragmentInitialSurveyBinding binding;
    private InitialSurveyViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                            ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInitialSurveyBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(InitialSurveyViewModel.class);

        setupSubmitButton();
        return binding.getRoot();
    }

    private void setupSubmitButton() {
        binding.submitButton.setOnClickListener(v -> {
            if (validateAnswers()) {
                if (viewModel.shouldStopSurvey()) {
                    showHealthyMindDialog();
                } else {
                    submitSurvey();
                }
            }
        });
    }

    private boolean validateAnswers() {
        // 필수 응답 체크
        if (!allQuestionsAnswered()) {
            Toast.makeText(requireContext(), "모든 질문에 답해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean allQuestionsAnswered() {
        // 모든 RadioGroup이 선택되었는지 확인
        return true; // 실제 구현 필요
    }

    private void submitSurvey() {
        // 설문 제출 처리
        Toast.makeText(requireContext(), "설문이 제출되었습니다.", Toast.LENGTH_SHORT).show();
        Navigation.findNavController(requireView())
            .navigate(R.id.action_initialSurveyFragment_to_navigation_home);
    }

    private void showHealthyMindDialog() {
        new AlertDialog.Builder(requireContext())
            .setTitle("설문 결과")
            .setMessage("현재 건강한 멘탈을 가지고 계십니다.\n" +
                      "SAD 증상이 발견되지 않아 프로그램 이용이 불필요합니다.\n" +
                      "앞으로도 건강하고 행복한 하루 보내시기 바랍니다.")
            .setPositiveButton("확인", (dialog, which) -> {
                requireActivity().finish();
            })
            .setCancelable(false)
            .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
} 