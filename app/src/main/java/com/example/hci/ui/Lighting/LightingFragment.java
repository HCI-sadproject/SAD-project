package com.example.hci.ui.Lighting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hci.databinding.FragmentLightingBinding;

public class LightingFragment extends Fragment {

    private FragmentLightingBinding binding;
    private boolean isLightOn = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // ViewBinding 초기화
        binding = FragmentLightingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // 조명 On/Off 버튼 클릭 리스너
        binding.btnLightToggle.setOnClickListener(v -> {
            isLightOn = !isLightOn;
            binding.btnLightToggle.setText(isLightOn ? "조명 Off" : "조명 On");
        });

        // 색상 버튼 클릭 리스너들
        binding.btnBlue.setOnClickListener(v -> {
            Toast.makeText(getContext(), "블루 색상이 선택되었습니다", Toast.LENGTH_SHORT).show();
        });

        binding.btnGreen.setOnClickListener(v -> {
            Toast.makeText(getContext(), "그린 색상이 선택되었습니다", Toast.LENGTH_SHORT).show();
        });

        binding.btnBrown.setOnClickListener(v -> {
            Toast.makeText(getContext(), "브라운 색상이 선택되었습니다", Toast.LENGTH_SHORT).show();
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}