package com.example.hci.ui.health;


public class DepressionPredictionOnlineLearning {
    String url = "http://<flask-server-ip>:5000/predict";

    // JSON 데이터를 생성하고 요청 보내기
    /*JSONObject jsonData = new JSONObject();
    jsonData.put("sunshine_hours", 5.0);
    jsonData.put("activity_level", 3.0);

    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonData,
            response -> {
                double predictedScore = response.getDouble("predicted_score");
                // 예측 결과 활용
            },
            error -> {
                // 에러 처리
            });
    requestQueue.add(request);*/
}
// Android Retrofit 인터페이스 정의
