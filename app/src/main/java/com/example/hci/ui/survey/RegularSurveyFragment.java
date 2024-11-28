package com.example.hci.ui.survey;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.example.hci.R;
import com.example.hci.databinding.FragmentRegularSurveyBinding;

public class RegularSurveyFragment extends Fragment {
    private FragmentRegularSurveyBinding binding;
    private RegularSurveyViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegularSurveyBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(RegularSurveyViewModel.class);

        setupInputFilters();
        setupSubmitButton();
        return binding.getRoot();
    }

    private void setupInputFilters() {
        binding.sleepHours.setFilters(new InputFilter[]{
            new InputFilterMinMax(0, 24)
        });
    }

    private void setupSubmitButton() {
        binding.submitButton.setOnClickListener(v -> {
            if (validateInputs()) {
                submitSurvey();
            }
        });
    }

    private boolean validateInputs() {
        String sleepHours = binding.sleepHours.getText().toString();
        if (sleepHours.isEmpty()) {
            showError("수면 시간을 입력해주세요.");
            return false;
        }
        // 다른 필수 입력 검증...
        return true;
    }

    private void submitSurvey() {
        try {
            collectAndSaveData();
            showSuccessDialog();
        } catch (Exception e) {
            showError("설문 제출 중 오류가 발생했습니다.");
        }
    }

    private void collectAndSaveData() {
        int sleepHours = Integer.parseInt(binding.sleepHours.getText().toString());
        viewModel.setSleepHours(sleepHours);
        // 다른 데이터 수집...
        viewModel.submitSurvey();
    }

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

    private void showError(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
} 