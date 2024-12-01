package com.example.hci.ui.health;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hci.databinding.FragmentHealthBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Calendar;

public class HealthFragment extends Fragment {

    private FragmentHealthBinding binding;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;

    private LineChart sleepChart;
    private LineChart stepsChart;
    private LineChart depressionPredictionChart;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HealthViewModel healthViewModel =
                new ViewModelProvider(this).get(HealthViewModel.class);

        binding = FragmentHealthBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Firebase 초기화
        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // 오늘 날짜 가져오기
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        // Firestore에서 데이터 가져오기
        if (currentUser != null) {
            fetchUserData(currentUser.getUid(), currentDate);
        }



        // View More 버튼 클릭 리스너
        binding.btnViewMore.setOnClickListener(v -> {
            // 상세 보기 화면으로 이동하는 로직 구현
        });





        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void fetchUserData(String uid, String currentDate) {
        // Firestore에서 데이터 가져오기
        fetchScoreData(uid, currentDate, 0);

        // 현재 날짜를 시작점으로 설정
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();

        // 수면, 걸음수, 우울 데이터 저장 리스트
        List<Entry> sleepEntries = new ArrayList<>();
        List<Entry> stepsEntries = new ArrayList<>();
        List<Entry> depressionEntries = new ArrayList<>();
        List<String> xLabels = new ArrayList<>(); // x축 레이블용

        // 각 데이터에 대해 8개 유효 데이터 가져오기
        fetchDataForChart(db, uid, "daily_sleep", calendar, sleepEntries, xLabels, 8, true);
        fetchDataForChart(db, uid, "daily_steps", calendar, stepsEntries, xLabels, 8, false);
        fetchDataForChart(db, uid, "depression_score", calendar, depressionEntries, xLabels, 8, true);

        // 그래프 업데이트
        updateCharts(sleepEntries, stepsEntries, depressionEntries, xLabels);


    }

    private void fetchScoreData(String uid, String date, int retries) {
        if (retries > 7) { // 최대 7일 전까지 검색
            Log.d("Firebase", "No data found within the last 7 days.");
            return;
        }

        db.collection("dummy_user")
                .document(uid) // 사용자 UID로 문서 참조
                .collection("time_series")
                .document(date) // 현재 날짜로 문서 참조
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists() && document.getData() != null) {
                            // 데이터가 존재하면
                            Double depressionScore = document.getDouble("depression_score");
                            Double predictionScore = document.getDouble("prediction_score");

                            if (depressionScore != null && predictionScore != null) {
                                // 데이터 UI에 표시
                                displayData(depressionScore, predictionScore);
                            } else {
                                // 값이 null인 경우 전날 데이터 검색
                                fetchScoreData(uid, getPreviousDate(date), retries + 1);
                            }
                        } else {
                            // 문서가 없으면 전날 데이터 검색
                            fetchScoreData(uid, getPreviousDate(date), retries + 1);
                        }
                    } else {
                        Log.d("Firebase", "Error getting document: ", task.getException());
                    }
                });


    }

    private String getPreviousDate(String date) {
        // 날짜 문자열을 "yyyy-MM-dd" 형식으로 파싱하고 하루 전 날짜 반환
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date currentDate = sdf.parse(date);
            if (currentDate != null) {
                long previousTime = currentDate.getTime() - (24 * 60 * 60 * 1000); // 하루 전
                return sdf.format(new Date(previousTime));
            }
        } catch (Exception e) {
            Log.d("DateError", "Error parsing date: " + date, e);
        }
        return date; // 오류 시 동일한 날짜 반환
    }





    private void displayData(double depressionScore, double predictionScore) {
        // 텍스트 뷰에 데이터 설정
        binding.currentScoreValue.setText(String.format("Depression Score: %.1f", depressionScore));
        binding.predictScoreValue.setText(String.format("Prediction Score: %.1f", predictionScore));
    }

    private void fetchDataForChart(FirebaseFirestore db, String userId, String field,
                                   Calendar calendar, List<Entry> entries, List<String> xLabels,
                                   int maxEntries, boolean resetCalendar) {
        if (resetCalendar) {
            calendar.setTime(new Date()); // 초기화
        }

        // 초기 값 및 변수 설정
        int index = 0;
        double previousValue = -1; // 이전 값 저장 (비교용)

        while (entries.size() < maxEntries && index < 100) { // 최대 100회 탐색 제한
            String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.getTime());

            db.collection("dummy_user")
                    .document(userId)
                    .collection("time_series")
                    .document(currentDate)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists() && document.contains(field)) {
                                double value = document.getDouble(field);
                                if (value != previousValue) { // 값이 없거나 이전 값과 다를 경우
                                    previousValue = value;
                                    entries.add(new Entry(entries.size(), (float) value));
                                    xLabels.add(currentDate.substring(5)); // MM-dd 형식 추가
                                }
                            }
                        }
                    });

            // 다음 날짜로 이동
            calendar.add(Calendar.DATE, -1); // 하루 이전
            index++;
        }
    }

    private void updateCharts(List<Entry> sleepEntries, List<Entry> stepsEntries,
                              List<Entry> depressionEntries, List<String> xLabels) {
        // LineDataSet 생성
        LineDataSet sleepDataSet = new LineDataSet(sleepEntries, "Sleep Hours");
        sleepDataSet.setColor(getResources().getColor(R.colors.blue1));
        sleepDataSet.setValueTextColor(getResources().getColor(R.colors.blue1));

        LineDataSet stepsDataSet = new LineDataSet(stepsEntries, "Steps");
        stepsDataSet.setColor(getResources().getColor(R.colors.yellow));
        stepsDataSet.setValueTextColor(getResources().getColor(R.colors.yellow));

        LineDataSet depressionDataSet = new LineDataSet(depressionEntries, "Depression Score");
        depressionDataSet.setColor(getResources().getColor(R.colors.purple1));
        depressionDataSet.setValueTextColor(getResources().getColor(R.colors.purple1));

        // LineData 설정
        LineData sleepData = new LineData(sleepDataSet);
        LineData stepsData = new LineData(stepsDataSet);
        LineData depressionData = new LineData(depressionDataSet);

        // 그래프 연결 및 x축 설정
        setupChart(binding.sleepChart, sleepData, xLabels);
        setupChart(binding.stepsChart, stepsData, xLabels);
        setupChart(binding.depressionPredictionChart, depressionData, xLabels);
    }

    private void setupChart(LineChart chart, LineData data, List<String> xLabels) {
        chart.setData(data);

        // x축 레이블 설정
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xLabels));

        chart.invalidate(); // 그래프 업데이트
    }


}
