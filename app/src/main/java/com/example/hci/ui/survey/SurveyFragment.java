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
import androidx.navigation.Navigation;

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
        binding = FragmentSurveyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setupButtons();

        return root;
    }

    private void setupButtons() {
        // 초기 설문은 로그인 체크
        binding.initialSurveyButton.setOnClickListener(v -> {
            if (isLoggedIn()) {
                Navigation.findNavController(v)
                    .navigate(R.id.action_navigation_survey_to_initialSurveyFragment);
            } else {
                showLoginRequiredDialog();
            }
        });

        // 상시 설문은 로그인 체크 없이 바로 이동
        binding.regularSurveyButton.setOnClickListener(v -> 
            Navigation.findNavController(v)
                .navigate(R.id.action_navigation_survey_to_regularSurveyFragment));
    }

    private void showLoginRequiredDialog() {
        new AlertDialog.Builder(requireContext())
            .setTitle("로그인 필요")
            .setMessage("초기 설문에 참여하려면 로그인이 필요합니다.")
            .setPositiveButton("확인", null)
            .show();
    }

    private boolean isLoggedIn() {
        // 실제 로그인 상태 체크 로직 구현
        return true; // 임시로 항상 true 반환
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
