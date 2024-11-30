package com.example.hci.ui.Diary;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hci.databinding.FragmentDiaryBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DiaryFragment extends Fragment {

    private FragmentDiaryBinding binding;
    private String selectedDate;
    private String selectedMood = "";
    // 임시 저장소로 HashMap 사용
    private static Map<String, DiaryEntry> diaryEntries = new HashMap<>();

    // 일기 데이터를 담을 클래스
    private static class DiaryEntry {
        String mood;
        String content;

        DiaryEntry(String mood, String content) {
            this.mood = mood;
            this.content = content;
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDiaryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // 현재 날짜로 초기화
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        selectedDate = sdf.format(new Date());
        binding.dateText.setText(selectedDate);

        // 날짜 선택 클릭 리스너 설정
        binding.dateText.setOnClickListener(v -> showDatePicker());

        // 기분 버튼 클릭 리스너 설정
        binding.moodHappy.setOnClickListener(v -> updateMoodSelection("행복 ☺️"));
        binding.moodExcited.setOnClickListener(v -> updateMoodSelection("신남 ⭐"));
        binding.moodPeaceful.setOnClickListener(v -> updateMoodSelection("평온 ✨"));
        binding.moodTired.setOnClickListener(v -> updateMoodSelection("피곤 💤"));
        binding.moodSad.setOnClickListener(v -> updateMoodSelection("우울 ☔"));
        binding.moodAngry.setOnClickListener(v -> updateMoodSelection("화남 ⚡"));
        binding.moodAnxious.setOnClickListener(v -> updateMoodSelection("불안 💭"));
        binding.moodSatisfied.setOnClickListener(v -> updateMoodSelection("뿌듯 ❤️"));

        // 저장 버튼 클릭 리스너
        binding.saveButton.setOnClickListener(v -> saveDiaryEntry());

        return root;
    }

    private void updateMoodSelection(String mood) {
        selectedMood = mood;
        
        // 모든 버튼 선택 해제
        binding.moodHappy.setSelected(false);
        binding.moodExcited.setSelected(false);
        binding.moodPeaceful.setSelected(false);
        binding.moodTired.setSelected(false);
        binding.moodSad.setSelected(false);
        binding.moodAngry.setSelected(false);
        binding.moodAnxious.setSelected(false);
        binding.moodSatisfied.setSelected(false);

        // 선택된 버튼만 선택 상태로 변경
        switch (mood) {
            case "행복 ☺️":
                binding.moodHappy.setSelected(true);
                break;
            case "신남 ⭐":
                binding.moodExcited.setSelected(true);
                break;
            case "평온 ✨":
                binding.moodPeaceful.setSelected(true);
                break;
            case "피곤 💤":
                binding.moodTired.setSelected(true);
                break;
            case "우울 ☔":
                binding.moodSad.setSelected(true);
                break;
            case "화남 ⚡":
                binding.moodAngry.setSelected(true);
                break;
            case "불안 💭":
                binding.moodAnxious.setSelected(true);
                break;
            case "뿌듯 ❤️":
                binding.moodSatisfied.setSelected(true);
                break;
        }
    }

    private void saveDiaryEntry() {
        String diaryContent = binding.diaryContent.getText().toString();
        
        // 기분이 선택되지 않았을 경우 처리
        if (selectedMood == null || selectedMood.isEmpty()) {
            Toast.makeText(requireContext(), "기분을 선택해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            DiaryEntry entry = new DiaryEntry(selectedMood, diaryContent);
            diaryEntries.put(selectedDate, entry);
            Toast.makeText(requireContext(), "저장되었습니다.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(requireContext(), "저장 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadDiaryEntry(String date) {
        try {
            DiaryEntry entry = diaryEntries.get(date);
            if (entry != null) {
                // 저장된 데이터가 있으면 불러오기
                updateMoodSelection(entry.mood);
                binding.diaryContent.setText(entry.content);
            } else {
                // 새로운 날짜는 초기화
                binding.diaryContent.setText("");
                selectedMood = "";
                updateMoodSelection("");
            }
        } catch (Exception e) {
            Toast.makeText(requireContext(), "데이터 로드 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, year1, monthOfYear, dayOfMonth) -> {
                    try {
                        // 선택된 날짜 포맷팅
                        Calendar selectedCal = Calendar.getInstance();
                        selectedCal.set(year1, monthOfYear, dayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        selectedDate = sdf.format(selectedCal.getTime());
                        binding.dateText.setText(selectedDate);
                        
                        // 선택된 날짜의 데이터 로드
                        loadDiaryEntry(selectedDate);
                    } catch (Exception e) {
                        Toast.makeText(requireContext(), "날짜 선택 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                    }
                }, year, month, day);

        datePickerDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
