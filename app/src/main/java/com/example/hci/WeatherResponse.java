package com.example.hci;

import com.google.gson.annotations.SerializedName;

public class WeatherResponse {
    @SerializedName("SS") // 일조
    private String sunshine;

    @SerializedName("TA") // 기온
    private String temperature;

    @SerializedName("RN") // 강수량
    private String rainfall;

    @SerializedName("WTH") // 일기
    private String weatherCode;

    // Getter 메서드들
    public String getSunshine() {
        return sunshine;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getRainfall() {
        return rainfall;
    }

    public String getWeatherCode() {
        return weatherCode;
    }
}
