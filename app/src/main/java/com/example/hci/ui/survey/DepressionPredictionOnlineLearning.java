import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.IOException;

public class PredictActivity extends AppCompatActivity {

    private static final String FLASK_URL = "http://192.168.219.104:5000"; // Flask 서버 URL
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict);

        mAuth = FirebaseAuth.getInstance();

        Button predictButton = findViewById(R.id.submitButton); // 예측 버튼
        predictButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPredictionRequest();
            }
        });
    }

    private void sendPredictionRequest() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid(); // 현재 사용자 UID 가져오기

            // Flask로 요청 보내기
            try {
                JSONObject json = new JSONObject();
                json.put("uid", uid); // UID를 JSON 형식으로 추가

                sendPostRequest(FLASK_URL, json.toString());
            } catch (Exception e) {
                Log.e("PredictActivity", "Error creating JSON: " + e.getMessage());
            }
        } else {
            Log.e("PredictActivity", "User not logged in");
        }
    }

    private void sendPostRequest(String url, String jsonBody) {
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(
                jsonBody,
                MediaType.get("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("PredictActivity", "Flask request failed: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    Log.d("PredictActivity", "Prediction response: " + responseData);

                    // 필요한 경우 응답 처리
                    // 예: 결과값을 UI에 표시
                } else {
                    Log.e("PredictActivity", "Flask response error: " + response.code());
                }
            }
        });
    }
}
