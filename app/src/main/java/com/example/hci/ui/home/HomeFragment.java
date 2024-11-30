package com.example.hci.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hci.HomeViewModelFactory;
import com.example.hci.databinding.FragmentHomeBinding;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class HomeFragment extends Fragment {

<<<<<<< HEAD
    private FragmentHomeBinding binding; // View Binding 객체 선언
=======
    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
>>>>>>> origin/main

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
<<<<<<< HEAD
        HomeViewModelFactory factory = new HomeViewModelFactory(requireActivity().getApplication());
        HomeViewModel homeViewModel =
                new ViewModelProvider(this, factory).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        homeViewModel.getWeatherInfo().observe(getViewLifecycleOwner(), info -> {
            binding.weatherInfo.setText(info);
        });

        /*
        // 추가 데이터 업데이트 (필요시 활성화)
        homeViewModel.getWatchData().observe(getViewLifecycleOwner(), data -> {
            binding.watchData.setText(data);
        });
        */
=======
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // 날씨 정보 업데이트 관찰
        homeViewModel.getWeatherInfo().observe(getViewLifecycleOwner(), weatherText -> {
            binding.weatherInfo.setText(weatherText);
        });

        // 위치 권한 확인
        checkLocationPermission();
>>>>>>> origin/main

        return root;
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), 
            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // 이미 권한이 있는 경우 날씨 정보 가져오기
            homeViewModel.getCurrentLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한이 승인된 경우 날씨 정보 가져오기
                homeViewModel.getCurrentLocation();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
