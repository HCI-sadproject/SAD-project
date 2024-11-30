package com.example.hci.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import com.example.hci.data.WeatherResponse;

public interface WeatherService {
    @GET("weather")
    Call<WeatherResponse> getCurrentWeatherByLocation(
        @Query("lat") double latitude,
        @Query("lon") double longitude,
        @Query("appid") String apiKey,
        @Query("units") String units
    );
} 