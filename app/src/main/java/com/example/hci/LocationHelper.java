package com.example.hci;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class LocationHelper {

    private final FusedLocationProviderClient fusedLocationClient;

    public LocationHelper(Context context) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    @SuppressLint("MissingPermission")
    public void getCurrentLocation(LocationCallback callback) {
        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                callback.onLocationReceived(location);
            } else {
                callback.onLocationError("현재 위치를 가져올 수 없습니다.");
            }
        }).addOnFailureListener(e -> {
            callback.onLocationError("위치 서비스 실패: " + e.getMessage());
        });
    }

    public interface LocationCallback {
        void onLocationReceived(Location location);
        void onLocationError(String errorMessage);
    }
}
