package com.example.hci.ui.Diary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hci.databinding.FragmentDiaryBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DiaryFragment extends Fragment {

    private FragmentDiaryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDiaryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // 날짜 설정
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        binding.dateText.setText(sdf.format(new Date()));

        // 기분 버튼 클릭 리스너 설정
        binding.moodHappy.setOnClickListener(v -> updateMoodSelection("행복"));
        binding.moodSad.setOnClickListener(v -> updateMoodSelection("우울"));
        binding.moodNormal.setOnClickListener(v -> updateMoodSelection("평온"));
        binding.moodAngry.setOnClickListener(v -> updateMoodSelection("분노"));

        return root;
    }

    private void updateMoodSelection(String mood) {
        // 선택된 기분 버튼 스타일 변경 로직
        binding.moodHappy.setSelected(mood.equals("행복"));
        binding.moodSad.setSelected(mood.equals("우울"));
        binding.moodNormal.setSelected(mood.equals("평온"));
        binding.moodAngry.setSelected(mood.equals("분노"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}