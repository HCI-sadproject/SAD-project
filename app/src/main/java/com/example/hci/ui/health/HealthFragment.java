package com.example.hci.ui.health;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.core.content.ContextCompat;

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
import android.graphics.Color;

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

        // 데이터 준비
        List<Entry> depressionEntries = new ArrayList<>();
        depressionEntries.add(new Entry(0, 23.52f));
        depressionEntries.add(new Entry(1, 9.32f));
        depressionEntries.add(new Entry(2, 14.97f));
        depressionEntries.add(new Entry(3, 7.76f));
        depressionEntries.add(new Entry(4, 17.07f));
        depressionEntries.add(new Entry(5, 23.67f));
        depressionEntries.add(new Entry(6, 15.27f));



        // LineDataSet 생성
        LineDataSet dataSet1 = new LineDataSet(depressionEntries, "Depression Data");
        dataSet1.setColor(Color.parseColor("#615687")); // 보라색
        dataSet1.setValueTextColor(Color.parseColor("#615687")); // 보라색


 // 처음 값을 추출
        List<Entry> predictionEntries = new ArrayList<>();
        predictionEntries.add(new Entry(7, 13.76f)); // 리스트에 추가

        LineDataSet predictionDataSet = new LineDataSet(predictionEntries, "Tomorrow");
        predictionDataSet.setColor(Color.parseColor("#FF6347")); // 빨간색 (내일 점수)
        predictionDataSet.setValueTextColor(Color.parseColor("#FF6347")); // 빨간색

            // 데이터를 병합해 LineData에 추가
        LineData depressionData = new LineData(dataSet1, predictionDataSet);


        binding.depressionPredictionChart.setData(depressionData);

        // X축 라벨 설정
        String[] dates1 = {"10-27", "11-03", "11-10", "11-17", "11-24", "12-01", "12-03", "내일"};
        XAxis xAxis1 = binding.depressionPredictionChart.getXAxis();
        //xAxis.setValueFormatter((value, axis) -> dates[(int) value]); // 날짜 형식으로 표시
        xAxis1.setGranularity(1f);  // X축 값 간격 설정
        xAxis1.setGranularityEnabled(true);
        xAxis1.setValueFormatter(new IndexAxisValueFormatter(dates1));

        binding.depressionPredictionChart.invalidate(); // 차트 새로고침


        // 데이터 준비
        List<Entry> stepEntries = new ArrayList<>();
        stepEntries.add(new Entry(1, 5559));
        stepEntries.add(new Entry(2, 8052));
        stepEntries.add(new Entry(3, 3439));
        stepEntries.add(new Entry(4, 2199));
        stepEntries.add(new Entry(5, 1623));
        stepEntries.add(new Entry(6, 8355));
        stepEntries.add(new Entry(7, 3706));
        stepEntries.add(new Entry(8, 6624));



        // LineDataSet 생성
        LineDataSet dataSet2 = new LineDataSet(stepEntries, "Depression Data");
        dataSet2.setColor(Color.parseColor("#F2BE5B")); // 노란색
        dataSet2.setValueTextColor(Color.parseColor("#F2BE5B")); // 노란색

        // LineData 객체 생성 및 차트에 설정
        LineData lineData2 = new LineData(dataSet2);
        binding.stepsChart.setData(lineData2);

        // X축 라벨 설정
        String[] dates2 = {"11-27", "11-28", "11-29", "11-30", "11-31", "12-1", "12-2","12-3"};
        XAxis xAxis2 = binding.stepsChart.getXAxis();
        //xAxis.setValueFormatter((value, axis) -> dates[(int) value]); // 날짜 형식으로 표시
        xAxis2.setGranularity(1f);  // X축 값 간격 설정
        xAxis2.setGranularityEnabled(true);
        xAxis2.setValueFormatter(new IndexAxisValueFormatter(dates2));

        binding.stepsChart.invalidate(); // 차트 새로고침



        // 데이터 준비
        List<Entry> sleepEntries = new ArrayList<>();
        sleepEntries.add(new Entry(1, 5.07f));
        sleepEntries.add(new Entry(2, 9.13f));
        sleepEntries.add(new Entry(3, 5.58f));
        sleepEntries.add(new Entry(4, 7.54f));
        sleepEntries.add(new Entry(5, 11.91f));
        sleepEntries.add(new Entry(6, 6.50f));
        sleepEntries.add(new Entry(7, 5.34f));
        sleepEntries.add(new Entry(8, 5.95f));



        // LineDataSet 생성
        LineDataSet dataSet = new LineDataSet(sleepEntries, "Sleep Data");

        // LineData 객체 생성 및 차트에 설정
        LineData lineData = new LineData(dataSet);
        binding.sleepChart.setData(lineData);

        // X축 라벨 설정
        String[] dates = {"11-27", "11-28", "11-29", "11-30", "11-31", "12-1", "12-2","12-3"};
        XAxis xAxis = binding.sleepChart.getXAxis();
        //xAxis.setValueFormatter((value, axis) -> dates[(int) value]); // 날짜 형식으로 표시
        xAxis.setGranularity(1f);  // X축 값 간격 설정
        xAxis.setGranularityEnabled(true);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dates));

        binding.sleepChart.invalidate(); // 차트 새로고침



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
        fetchDataForChart(db, uid, "depression_score", calendar, depressionEntries, xLabels, 7, true);

        // 그래프 업데이트
        updateCharts(sleepEntries, stepsEntries, depressionEntries, xLabels);


    }

    private void fetchScoreData(String uid, String date, int retries) {
        if (retries < 7) { // 최대 7일 전까지 검색

            db.collection("dummy_users")
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
            Log.d("Firebase", "No data found within the last 7 days.");
            return;
        }



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
        double[] previousValue = {-1}; // 이전 값을 저장하기 위한 배열 사용 (람다 내에서 변경 가능)
        int[] index = {0}; // 탐색 횟수를 저장하기 위한 배열 사용

        while (entries.size() < maxEntries && index[0] < 100) { // 최대 100회 탐색 제한
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
                                if (value != previousValue[0]) { // 값이 없거나 이전 값과 다를 경우
                                    previousValue[0] = value;
                                    entries.add(new Entry(entries.size(), (float) value));
                                    xLabels.add(currentDate.substring(5)); // MM-dd 형식 추가
                                }
                            }
                        }
                    });

            // 다음 날짜로 이동
            calendar.add(Calendar.DATE, -1); // 하루 이전
            index[0]++;
        }
    }

    private void updateCharts(List<Entry> sleepEntries, List<Entry> stepsEntries,
                              List<Entry> depressionEntries, List<String> xLabels) {
        // LineDataSet 생성 (수면)
        LineDataSet sleepDataSet = new LineDataSet(sleepEntries, "Sleep Hours");
        sleepDataSet.setColor(Color.parseColor("#3F7098")); // 파란색
        sleepDataSet.setValueTextColor(Color.parseColor("#3F7098")); // 파란색

        // LineDataSet 생성 (걸음수)
        LineDataSet stepsDataSet = new LineDataSet(stepsEntries, "Steps");
        stepsDataSet.setColor(Color.parseColor("#F2BE5B")); // 노란색
        stepsDataSet.setValueTextColor(Color.parseColor("#F2BE5B")); // 노란색

        // LineDataSet 생성 (우울 점수)
        LineDataSet depressionDataSet = new LineDataSet(depressionEntries, "Depression Score");
        depressionDataSet.setColor(Color.parseColor("#615687")); // 보라색
        depressionDataSet.setValueTextColor(Color.parseColor("#615687")); // 보라색

        // 마지막 값(내일 예측 점수) 추가
        if (!depressionEntries.isEmpty()) {
            Entry predictionEntry = depressionEntries.remove(depressionEntries.size() - 1); // 마지막 값을 추출
            List<Entry> predictionEntries = new ArrayList<>();
            predictionEntries.add(predictionEntry); // 리스트에 추가

            LineDataSet predictionDataSet = new LineDataSet(predictionEntries, "Tomorrow");
            predictionDataSet.setColor(Color.parseColor("#FF6347")); // 빨간색 (내일 점수)
            predictionDataSet.setValueTextColor(Color.parseColor("#FF6347")); // 빨간색

            // 데이터를 병합해 LineData에 추가
            LineData depressionData = new LineData(depressionDataSet, predictionDataSet);
            setupChart(binding.depressionPredictionChart, depressionData, xLabels);
        }

        // 수면 및 걸음수 그래프 업데이트
        setupChart(binding.sleepChart, new LineData(sleepDataSet), xLabels);
        setupChart(binding.stepsChart, new LineData(stepsDataSet), xLabels);
    }

    private void setupChart(LineChart chart, LineData data, List<String> xLabels) {
        chart.setData(data);

        // x축 설정
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xLabels));

        chart.invalidate(); // 그래프 새로고침
    }


}
