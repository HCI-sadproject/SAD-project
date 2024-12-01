package com.example.hci.ui.home;
import com.example.hci.R;

import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.hci.LocationHelper;
import com.example.hci.RetrofitClient;
import com.example.hci.Station;
import com.example.hci.WeatherService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;

public class HomeViewModel extends AndroidViewModel {
    private final MutableLiveData<String> weatherInfo = new MutableLiveData<>();
    private final MutableLiveData<Integer> weatherIcon = new MutableLiveData<>();
    private final String API_BASE_URL = "https://apihub.kma.go.kr/api/typ01/";
    private final String API_KEY = "vZ4WEsMGSBKeFhLDBqgS_Q";
    private final LocationHelper locationHelper;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        this.locationHelper = new LocationHelper(application); // 초기화
        fetchLocationAndWeatherData(application);
    }

    private void fetchLocationAndWeatherData(Context context) {
        locationHelper.getCurrentLocation(new LocationHelper.LocationCallback() {
            @Override
            public void onLocationReceived(Location location) {
                String nearestStationId = getNearestStationId(context, location.getLatitude(), location.getLongitude());
                fetchWeatherData(nearestStationId);
            }

            @Override
            public void onLocationError(String errorMessage) {
                weatherInfo.setValue("위치를 가져올 수 없습니다: " + errorMessage);
            }
        });
    }

    private String getNearestStationId(Context context, double latitude, double longitude) {
        List<Station> stations = loadStationsFromJson(context);
        double minDistance = Double.MAX_VALUE;
        String nearestStationId = null;

        for (Station station : stations) {
            double distance = calculateDistance(latitude, longitude, station.getLatitude(), station.getLongitude());
            if (distance < minDistance) {
                minDistance = distance;
                nearestStationId = station.getId();
            }
        }

        return nearestStationId;
    }

    private List<Station> loadStationsFromJson(Context context) {
        List<Station> stations = new ArrayList<>();
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.stations);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();

            String json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject stationJson = jsonArray.getJSONObject(i);
                stations.add(new Station(
                        stationJson.getString("id"),
                        stationJson.getString("name"),
                        stationJson.getDouble("latitude"),
                        stationJson.getDouble("longitude")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stations;
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // 지구 반지름 (km)
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // 결과: 거리 (km)
    }

    private void fetchWeatherData(String stationId) {
        WeatherService service = RetrofitClient.getClient(API_BASE_URL).create(WeatherService.class);

        String today = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());
        String obsParams = "SS:TA:RN:WTH"; // 일조, 기온, 강수량, 일기


        Call<ResponseBody> call = service.getWeatherData(
                today,        // 시작 날짜
                today,        // 종료 날짜
                obsParams,    // 관측 항목
                stationId,    // 관측소 ID
                API_KEY,      // 인증키
                "1"           // 도움 요청 여부
        );

        Log.d("API Debug", "Request URL: " + call.request().url());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        // 응답 데이터 읽기
                        String responseData = response.body().string();
                        Log.d("Weather API", "Response Data: " + responseData);

                        // 데이터 파싱
                        String[] lines = responseData.split("\n");
                        for (String line : lines) {
                            // 필요한 데이터 추출
                            if (line.startsWith("2024")) { // 예: 날짜로 시작하는 데이터 라인
                                String[] data = line.split("\\s+"); // 공백 기준 분리
                                String date = data[0];   // 날짜
                                String sunshine = data[33]; // 일조합
                                String temperature = data[11]; // 평균 기온
                                String rainfall = data[39]; // 강수량

                                // 파싱된 데이터 설정
                                String parsedData = String.format(
                                        "날짜: %s, 일조합: %s시간, 평균 기온: %s°C, 강수량: %smm",
                                        date, sunshine, temperature, rainfall
                                );
                                weatherInfo.setValue(parsedData);
                                break; // 필요한 데이터만 처리 후 종료
                            }
                        }
                    } catch (IOException e) {
                        Log.e("Weather API", "Error reading response body", e);
                        weatherInfo.setValue("데이터 읽기 실패: " + e.getMessage());
                    }
                } else {
                    try {
                        Log.e("Weather API", "Error Response: " + response.errorBody().string());
                    } catch (IOException e) {
                        Log.e("Weather API", "Error reading error body", e);
                    }
                    weatherInfo.setValue("날씨 데이터를 가져오는 데 실패했습니다.");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Weather API", "Request failed: " + t.getMessage());
                weatherInfo.setValue("날씨 정보 요청 실패: " + t.getMessage());
            }
        });
    }

    private int getWeatherIconResource(String weatherCode) {
        switch (weatherCode) {
            case "01": return R.drawable.sunny;
            case "02": return R.drawable.cloudy;
            case "03": return R.drawable.rainy;
            default: return R.drawable.sad;
        }
    }

    public LiveData<String> getWeatherInfo() {
                return weatherInfo;
    }

    public LiveData<Integer> getWeatherIcon() {
                return weatherIcon;
    }
}