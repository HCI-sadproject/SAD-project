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

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding; // View Binding 객체 선언

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
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

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
