package com.example.hci.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class BluetoothManager {
    private static final UUID BT_MODULE_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // HC-05 기본 UUID
    private final BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket bluetoothSocket;
    private OutputStream outputStream;
    private InputStream inputStream;

    public BluetoothManager() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public boolean isBluetoothEnabled() {
        return bluetoothAdapter != null && bluetoothAdapter.isEnabled();
    }

    public void connect(String address) throws IOException {
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);
        bluetoothSocket = device.createRfcommSocketToServiceRecord(BT_MODULE_UUID);
        bluetoothSocket.connect();
        outputStream = bluetoothSocket.getOutputStream();
        inputStream = bluetoothSocket.getInputStream();
    }

    public void sendData(String data) throws IOException {
        if (outputStream != null) {
            outputStream.write(data.getBytes());
        }
    }

    public void disconnect() throws IOException {
        if (outputStream != null) outputStream.close();
        if (inputStream != null) inputStream.close();
        if (bluetoothSocket != null) bluetoothSocket.close();
    }
} 