package com.example.hci.ui.survey;

import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.hci.MainActivity;
import com.example.hci.R;
import com.example.hci.databinding.FragmentRegularSurveyBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Locale;


/**
 * 상시 설문을 처리하는 Fragment
 * 사용자의 현재 상태를 평가하고 데이터를 수집
 */
public class RegularSurveyFragment extends Fragment {

    private static final String FLASK_URL = "http://192.168.219.102:5000/predict"; // Flask 서버 URL (IP 주소는 변경 필요)
    private FragmentRegularSurveyBinding binding;
    private RegularSurveyViewModel viewModel;
    private static final String SURVEY_DATA_KEY = "regular_survey_data";

    // 모든 설문 데이터를 저장하는 정적 리스트
    // 백엔드 연동 시 이 데이터를 서버로 전송하면 됨
    private static List<RegularSurveyData> surveyDataList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegularSurveyBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(RegularSurveyViewModel.class);

        setupInputFilters();
        setupSubmitButton();

        super.onCreate(savedInstanceState);


        // "Predict" 버튼을 클릭했을 때의 동작 설정
        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Firebase에서 현재 로그인한 사용자 가져오기
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    String uid = user.getUid(); // Firebase 사용자 UID를 가져옴
                    sendPredictionRequest(uid); // UID를 Flask 서버로 전송
                } else {
                    Log.e("RegularSurveyFragment", "uid 읽기 실패");
                }
            }
        });


        return binding.getRoot();
    }

    /**
     * 입력값 필터 설정
     * - 수면 시간: 0-24시간
     * - 사회활동 일수: 0-7일
     */
    private void setupInputFilters() {
        binding.sleepHours.setFilters(new InputFilter[]{
                new InputFilterMinMax(0, 24)
        });

        binding.socialDays.setFilters(new InputFilter[]{
                new InputFilterMinMax(0, 7)
        });
    }

    /**
     * 제출 버튼 설정
     * 입력값 검증 후 데이터 저장
     */
    private void setupSubmitButton() {
        binding.submitButton.setOnClickListener(v -> {
            if (validateInputs()) {
                submitSurvey();
                collectAndSaveData();// 설문 완료 시 7일 후 알림 설정
            }
        });
    }

    /**
     * 모든 입력값 검증
     * 필수 입력 항목이 모두 채워졌는지 확인
     */
    private boolean validateInputs() {
        // 수면 상태 검증
        if (binding.wakeupDifficultyGroup.getCheckedRadioButtonId() == -1) {
            showError("1-1. 기상 어려움 정도를 선택해주세요.");
            return false;
        }

        String sleepHours = binding.sleepHours.getText().toString();
        if (sleepHours.isEmpty()) {
            showError("1-2. 수면 시간을 입력해주세요.");
            return false;
        }

        // 사회적 활동 검증
        if (binding.socialAnxietyGroup.getCheckedRadioButtonId() == -1) {
            showError("2-1. 사회적 활동 거부감 정도를 선택해주세요.");
            return false;
        }

        String socialDays = binding.socialDays.getText().toString();
        if (socialDays.isEmpty()) {
            showError("2-2. 사회적 활동 일수를 입력해주세요.");
            return false;
        }

        // 기분 상태 검증
        if (binding.depressionGroup.getCheckedRadioButtonId() == -1) {
            showError("3-1. 우울감 정도를 선택해주세요.");
            return false;
        }

        // 체중 변화 검증
        if (binding.weightGroup.getCheckedRadioButtonId() == -1) {
            showError("4. 체중 변화를 선택해주세요.");
            return false;
        }

        // 식욕 변화 검증
        if (binding.appetiteGroup.getCheckedRadioButtonId() == -1) {
            showError("5. 식욕 변화를 선택해주세요.");
            return false;
        }

        // 에너지 레벨 검증
        if (binding.energyGroup.getCheckedRadioButtonId() == -1) {
            showError("6. 에너지 레벨을 선택해주세요.");
            return false;
        }

        return true;
    }

    /**
     * 설문 데이터 수집 및 저장
     * <p>
     * 데이터 흐름:
     * 1. UI에서 사용자 입력 수집
     * 2. RegularSurveyData 객체 생성
     * 3. 정적 리스트에 저장
     * <p>
     * 백엔드 연동 시 고려사항:
     * - REST API 엔드포인트 구현 (/api/surveys/regular)
     * - 데이터 형식: JSON
     * - HTTP 메서드: POST
     * <p>
     * 예시 JSON 형식:
     * {
     * "wakeupDifficulty": "약간 어려움",
     * "sleepHours": 7,
     * "socialAnxiety": "없음",
     * "socialDays": 5,
     * "depressionLevel": "약간 우울",
     * "weightChange": "평소와 비슷하다",
     * "appetiteChange": "그대로다",
     * "energyLevel": "보통",
     * "submissionDate": "2024-03-21T14:30:00Z"
     * }
     */
    private void collectAndSaveData() {
        // 데이터 수집
        String wakeupDifficulty = getSelectedRadioText(binding.wakeupDifficultyGroup);
        int sleepHours = Integer.parseInt(binding.sleepHours.getText().toString());
        String socialAnxiety = getSelectedRadioText(binding.socialAnxietyGroup);
        int socialDays = Integer.parseInt(binding.socialDays.getText().toString());
        String depressionLevel = getSelectedRadioText(binding.depressionGroup);
        String weightChange = getSelectedRadioText(binding.weightGroup);
        String appetiteChange = getSelectedRadioText(binding.appetiteGroup);
        String energyLevel = getSelectedRadioText(binding.energyGroup);

        // RegularSurveyData 객체 생성
        RegularSurveyData surveyData = new RegularSurveyData(
                wakeupDifficulty, sleepHours, socialAnxiety, socialDays,
                depressionLevel, weightChange, appetiteChange, energyLevel
        );

        // 데이터 저장
        surveyDataList.add(surveyData);

        // TODO: 백엔드 연동 시 추가할 예시코드
        // ApiService.submitRegularSurvey(surveyData)
        //     .enqueue(new Callback<Response>() {
        //         @Override
        //         public void onSuccess(Response response) {
        //             showSuccessDialog();
        //         }
        //
        //         @Override
        //         public void onError(Error error) {
        //             showError("서버 전송 실패: " + error.getMessage());
        //         }
        //     });
    }

    // 제출 버튼 리스너를 사용하고 있어서 이 리스너를 통해 데이터 저장도 해야한다면 같이 넣기
    public class PredictActivity extends AppCompatActivity {



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // ViewBinding을 사용하여 레이아웃을 설정
            binding = FragmentRegularSurveyBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot()); // ViewBinding을 사용하여 root view 설정

            // "Predict" 버튼을 클릭했을 때의 동작 설정
            binding.submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Firebase에서 현재 로그인한 사용자 가져오기
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        String uid = user.getUid(); // Firebase 사용자 UID를 가져옴
                        sendPredictionRequest(uid); // UID를 Flask 서버로 전송
                    } else {
                        Toast.makeText(PredictActivity.this, "로그인된 사용자가 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        /**
         * Flask 서버로 사용자 UID를 전송하고 예측 요청 수행
         * @param uid 현재 사용자의 Firebase UID
         */
        private void sendPredictionRequest(String uid) {
            new Thread(() -> {
                try {
                    // Flask 서버의 예측 요청 URL에 연결
                    URL url = new URL(FLASK_URL);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setDoOutput(true);

                    // UID를 JSON 형식으로 생성
                    JSONObject jsonRequest = new JSONObject();
                    jsonRequest.put("uid", uid); // 요청 JSON에 UID 포함

                    // 서버로 JSON 데이터를 전송
                    OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                    writer.write(jsonRequest.toString());
                    writer.flush();
                    writer.close();

                    // 서버의 응답 코드 확인
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        Log.d("PredictActivity", "예측 요청 성공");
                        runOnUiThread(() -> Toast.makeText(PredictActivity.this, "예측 요청이 성공적으로 전송되었습니다.", Toast.LENGTH_SHORT).show());
                    } else {
                        Log.e("PredictActivity", "예측 요청 실패: " + responseCode);
                        runOnUiThread(() -> Toast.makeText(PredictActivity.this, "예측 요청 실패: " + responseCode, Toast.LENGTH_SHORT).show());
                    }

                    connection.disconnect();
                } catch (Exception e) {
                    // 예외 처리 (네트워크 오류, JSON 오류 등)
                    Log.e("PredictActivity", "예측 요청 중 오류 발생", e);
                    runOnUiThread(() -> Toast.makeText(PredictActivity.this, "오류 발생: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                }
            }).start();
        }
    }
    private void sendPredictionRequest(String uid) {
        new Thread(() -> {
            try {
                // Flask 서버의 예측 요청 URL에 연결
                URL url = new URL(FLASK_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                // UID를 JSON 형식으로 생성
                JSONObject jsonRequest = new JSONObject();
                jsonRequest.put("uid", uid); // 요청 JSON에 UID 포함

                // 서버로 JSON 데이터를 전송
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(jsonRequest.toString());
                writer.flush();
                writer.close();

                // 서버의 응답 코드 확인
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Log.d("PredictActivity", "예측 요청 성공");
                } else {
                    Log.e("PredictActivity", "예측 요청 실패: " + responseCode);

                }

                connection.disconnect();
            } catch (Exception e) {
                // 예외 처리 (네트워크 오류, JSON 오류 등)
                Log.e("PredictActivity", "예측 요청 중 오류 발생", e);

            }
        }).start();
    }

    /**
     * RadioGroup에서 선택된 텍스트 가져오기
     */
    private String getSelectedRadioText(RadioGroup group) {
        int selectedId = group.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton radioButton = group.findViewById(selectedId);
            return radioButton.getText().toString();
        }
        return "";
    }

    /**
     * 성공 다이얼로그 표시
     * 설문 제출 완료 후 홈 화면으로 이동
     */
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

    /**
     * 에러 메시지 표시
     */
    private void showError(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 저장된 설문 데이터 가져오기
     * 다른 Fragment나 Activity에서 접근 가능
     */
    public static List<RegularSurveyData> getSurveyDataList() {
        return surveyDataList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void submitSurvey() {
        try {
            // 설문 데이터를 수집하고 저장
            collectAndSaveData();

            // 설문 데이터를 Firestore에 저장
            saveSurveyToFirestore();

            // MainActivity의 onSurveyCompleted 호출
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).onSurveyCompleted();
            }

            // 성공 다이얼로그 표시
            showSuccessDialog();
        } catch (Exception e) {
            Log.e("RegularSurveyFragment", "submitSurvey Error: ", e);
            showError("설문 제출 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    private void saveSurveyToFirestore() {
        // Firestore 인스턴스
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        // UID 가져오기 (로그인 상태 확인)
        String uid = FirebaseAuth.getInstance().getUid();
        if (uid == null) {
            showError("사용자 인증 정보가 없습니다. 다시 로그인해주세요.");
            return;
        }

        // 제출한 날짜+시간 형식으로 문서 이름 생성
        String timestamp = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        // 설문 데이터 수집
        HashMap<String, Object> surveyData = new HashMap<>();
        try {
            surveyData.put("wakeupDifficulty", getSelectedRadioText(binding.wakeupDifficultyGroup));
            surveyData.put("sleepHours", Integer.parseInt(binding.sleepHours.getText().toString()));
            surveyData.put("socialAnxiety", getSelectedRadioText(binding.socialAnxietyGroup));
            surveyData.put("socialDays", Integer.parseInt(binding.socialDays.getText().toString()));
            surveyData.put("depressionLevel", getSelectedRadioText(binding.depressionGroup));
            surveyData.put("weightChange", getSelectedRadioText(binding.weightGroup));
            surveyData.put("appetiteChange", getSelectedRadioText(binding.appetiteGroup));
            surveyData.put("energyLevel", getSelectedRadioText(binding.energyGroup));
        } catch (NumberFormatException e) {
            showError("숫자 입력 항목에 올바르지 않은 값이 있습니다.");
            return;
        }

        // Firestore에 데이터 저장
        firestore.collection("users")
                .document(uid)
                .collection("surveyResult")
                .document(timestamp)
                .set(surveyData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(requireContext(), "설문 결과가 저장되었습니다.", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    showError("데이터 저장 중 오류 발생: " + e.getMessage());
                });
    }
}