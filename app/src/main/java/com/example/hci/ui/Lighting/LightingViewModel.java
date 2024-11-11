package com.example.hci.ui.Lighting;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.hci.bluetooth.BluetoothManager;

public class LightingViewModel extends ViewModel {
    private final BluetoothManager bluetoothManager;
    private final MutableLiveData<Boolean> isConnected;
    private final MutableLiveData<String> connectionStatus;

    public LightingViewModel() {
        bluetoothManager = new BluetoothManager();
        isConnected = new MutableLiveData<>(false);
        connectionStatus = new MutableLiveData<>("연결되지 않음");
    }

    public BluetoothManager getBluetoothManager() {
        return bluetoothManager;
    }

    public LiveData<Boolean> getIsConnected() {
        return isConnected;
    }

    public void setIsConnected(boolean connected) {
        isConnected.setValue(connected);
    }

    public LiveData<String> getConnectionStatus() {
        return connectionStatus;
    }

    public void setConnectionStatus(String status) {
        connectionStatus.setValue(status);
    }
}