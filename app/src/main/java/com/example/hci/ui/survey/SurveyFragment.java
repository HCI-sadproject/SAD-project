package com.example.hci.ui.survey;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hci.databinding.FragmentSurveyBinding;

public class SurveyFragment extends Fragment {

    private FragmentSurveyBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // ViewModel 연결
        SurveyViewModel surveyViewModel =
                new ViewModelProvider(this).get(SurveyViewModel.class);

        // Binding 초기화
        binding = FragmentSurveyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // TextView에 ViewModel 데이터 설정
        final TextView textView = binding.textSurvey;
        surveyViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
