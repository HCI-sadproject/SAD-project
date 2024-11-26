package com.example.hci;

import com.google.gson.annotations.SerializedName;

public class WeatherResponse {
    @SerializedName("SS") // JSON 필드 이름
    private String sunshine;

    @SerializedName("TA")
    private String temperature;

    @SerializedName("RN")
    private String rainfall;

    @SerializedName("WW")
    private String weatherCode;

    // Getter methods
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
