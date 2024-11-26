package com.example.hci;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("getWeatherData") // 실제 엔드포인트로 변경하세요.
    Call<WeatherResponse> getWeatherData(
            @Query("serviceKey") String apiKey,
            @Query("dataType") String dataType,
            @Query("startDt") String startDt,
            @Query("endDt") String endDt,
            @Query("stnIds") String stationId
    );
}
