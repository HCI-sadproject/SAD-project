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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

        // 오늘 날짜 가져오기
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        // Firestore에서 데이터 가져오기
        if (currentUser != null) {
            fetchUserData(currentUser.getUid(), currentDate);
        }

        // Firebase 초기화
        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

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
        db.collection("dummy_user")
                .document(uid) // 사용자 UID로 문서 참조
                .collection("time_series")
                .document(currentDate) // 오늘 날짜로 문서 참조
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // 데이터 추출
                            double depressionScore = document.getDouble("depression_score");
                            double predictionScore = document.getDouble("prediction_score");

                            // 데이터 UI에 표시
                            displayData(depressionScore, predictionScore);
                        } else {
                            Log.d("Firebase", "Document does not exist for date: " + currentDate);
                        }
                    } else {
                        Log.d("Firebase", "Error getting document: ", task.getException());
                    }
                });


    }
    private void displayData(double depressionScore, double predictionScore) {
        // 텍스트 뷰에 데이터 설정
        binding.current_score_value.setText(String.format("Depression Score: %.1f", depressionScore));
        binding.predict_score_value.setText(String.format("Prediction Score: %.1f", predictionScore));
    }


}
