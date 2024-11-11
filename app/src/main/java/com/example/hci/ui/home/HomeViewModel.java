package com.example.hci.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> weatherInfo;
    private final MutableLiveData<String> watchData;

    public HomeViewModel() {
        weatherInfo = new MutableLiveData<>();
        watchData = new MutableLiveData<>();
        
        // 임시 데이터
        weatherInfo.setValue("날씨 정보가 여기에 표시됩니다.");
        watchData.setValue("워치 데이터가 여기에 표시됩니다.");
    }

    public LiveData<String> getWeatherInfo() {
        return weatherInfo;
    }

    public LiveData<String> getWatchData() {
        return watchData;
    }
}