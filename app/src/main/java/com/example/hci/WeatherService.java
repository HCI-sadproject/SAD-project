package com.example.hci;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import okhttp3.ResponseBody;

public interface WeatherService {
    @GET("url/kma_sfcdd3.php") // 실제 엔드포인트로 변경하세요.
    Call<ResponseBody> getWeatherData(
            @Query("tm1") String startDt,
            @Query("tm2") String endDt,
            @Query("obs") String obs,
            @Query("stn") String stationId,
            @Query("authKey") String apiKey,
            @Query("help") String help      // 도움말 추가 (1 또는 0)
    );
}
