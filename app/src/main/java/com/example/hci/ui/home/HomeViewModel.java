package com.example.hci.ui.home;

import android.app.Application;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.Context;
import android.os.Bundle;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.hci.api.WeatherService;
import com.example.hci.data.WeatherResponse;

public class HomeViewModel extends AndroidViewModel {
    private final MutableLiveData<String> weatherInfo;
    private final MutableLiveData<String> watchData;
    private static final String API_KEY = "c6b7b2583e8c0cdd3f702a42d54eb4c7";
    private final WeatherService weatherService;
    private final LocationManager locationManager;
    private final Context context;

    public HomeViewModel(Application application) {
        super(application);
        context = application.getApplicationContext();
        weatherInfo = new MutableLiveData<>();
        watchData = new MutableLiveData<>();
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        
        weatherService = retrofit.create(WeatherService.class);
    }

    public void getCurrentLocation() {
        try {
            weatherInfo.setValue("위치 정보를 가져오는 중...");
            
            // GPS가 켜져있는지 확인
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                weatherInfo.setValue("GPS를 켜주세요.");
                return;
            }

            // 마지막으로 알려진 위치 먼저 사용
            Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastLocation != null) {
                getWeatherForLocation(lastLocation.getLatitude(), lastLocation.getLongitude());
            }

            // 실시간 위치 업데이트 요청
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,  // 5초
                10,    // 10미터
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        getWeatherForLocation(location.getLatitude(), location.getLongitude());
                        locationManager.removeUpdates(this);
                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                        weatherInfo.setValue("GPS를 켜주세요.");
                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                        weatherInfo.setValue("위치 정보를 가져오는 중...");
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }
                }
            );

            // 네트워크 기반 위치도 함께 사용
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                5000,  // 5초
                10,    // 10미터
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        getWeatherForLocation(location.getLatitude(), location.getLongitude());
                        locationManager.removeUpdates(this);
                    }

                    @Override
                    public void onProviderDisabled(String provider) {}

                    @Override
                    public void onProviderEnabled(String provider) {}

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {}
                }
            );

        } catch (SecurityException e) {
            weatherInfo.setValue("위치 권한이 필요합니다.");
        }
    }

    private void getWeatherForLocation(double latitude, double longitude) {
        weatherInfo.setValue("날씨 정보를 가져오는 중...");
        
        weatherService.getCurrentWeatherByLocation(latitude, longitude, API_KEY, "metric")
            .enqueue(new Callback<WeatherResponse>() {
                @Override
                public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        WeatherResponse data = response.body();
                        String weatherText = String.format("현재 위치의 날씨\n\n온도: %.1f°C\n습도: %d%%\n날씨: %s",
                            data.main.temp,
                            data.main.humidity,
                            data.weather[0].description);
                        weatherInfo.setValue(weatherText);
                    } else {
                        weatherInfo.setValue("날씨 정보를 가져오는데 실패했습니다.\n응답 코드: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<WeatherResponse> call, Throwable t) {
                    weatherInfo.setValue("네트워크 오류가 발생했습니다.\n" + t.getMessage());
                }
            });
    }

    public LiveData<String> getWeatherInfo() {
        return weatherInfo;
    }

    public LiveData<String> getWatchData() {
        return watchData;
    }
}