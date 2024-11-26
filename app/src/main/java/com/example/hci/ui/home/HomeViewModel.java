package com.example.hci.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hci.RetrofitClient;
import com.example.hci.WeatherResponse;
import com.example.hci.WeatherService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<String> weatherInfo = new MutableLiveData<>();
    private final String API_BASE_URL = "http://apis.data.go.kr/1360000/"; // API 기본 URL
    private final String API_KEY = "YOUR_API_KEY"; // 발급받은 인증키

    public HomeViewModel() {
        fetchWeatherData();
    }

    private void fetchWeatherData() {
        WeatherService service = RetrofitClient.getClient(API_BASE_URL).create(WeatherService.class);

        // 현재 날짜 계산 (형식: yyyyMMdd)
        String today = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());

        Call<WeatherResponse> call = service.getWeatherData(
                API_KEY,
                "JSON", // 데이터 타입
                today,  // 시작 날짜
                today,  // 끝 날짜
                "108"   // 관측소 ID (서울)
        );

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse data = response.body();
                    String info = "일조: " + data.getSunshine() + " hr\n" +
                            "기온: " + data.getTemperature() + " °C\n" +
                            "강수량: " + data.getRainfall() + " mm\n" +
                            "일기: " + data.getWeatherCode();
                    weatherInfo.setValue(info);
                } else {
                    weatherInfo.setValue("날씨 정보를 가져올 수 없습니다.");
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                weatherInfo.setValue("날씨 정보 요청 실패: " + t.getMessage());
            }
        });
    }

    public LiveData<String> getWeatherInfo() {
        return weatherInfo;
    }
}
