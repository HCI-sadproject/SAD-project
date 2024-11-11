package com.example.hci.ui.Lighting;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hci.databinding.FragmentLightingBinding;
import com.example.hci.bluetooth.BluetoothManager;

import java.io.IOException;

public class LightingFragment extends Fragment {

    private FragmentLightingBinding binding;
    private LightingViewModel lightingViewModel;
    private static final String DEVICE_ADDRESS = "98:D3:31:F5:B9:E7"; // HC-06 MAC 주소를 실제 주소로 변경하세요
    private boolean isLightOn = false;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int ENABLE_BT_REQUEST_CODE = 2;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                            ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLightingBinding.inflate(inflater, container, false);
        lightingViewModel = new ViewModelProvider(this).get(LightingViewModel.class);

        checkBluetoothPermissions();
        setupButtons();
        observeViewModel();

        return binding.getRoot();
    }

    private void checkBluetoothPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ActivityCompat.checkSelfPermission(requireContext(), 
                    Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(requireContext(), 
                    Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                
                requestPermissions(new String[]{
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT
                }, PERMISSION_REQUEST_CODE);
                return;
            }
        }
        initializeBluetooth();
    }

    private void initializeBluetooth() {
        try {
            if (!lightingViewModel.getBluetoothManager().isBluetoothEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, ENABLE_BT_REQUEST_CODE);
            } else {
                connectToDevice();
            }
        } catch (Exception e) {
            showToast("블루투스 초기화 실패: " + e.getMessage());
        }
    }

    private void connectToDevice() {
        if (ActivityCompat.checkSelfPermission(requireContext(), 
                Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        new Thread(() -> {
            try {
                lightingViewModel.getBluetoothManager().connect(DEVICE_ADDRESS);
                requireActivity().runOnUiThread(() -> {
                    lightingViewModel.setIsConnected(true);
                    lightingViewModel.setConnectionStatus("연결됨");
                    showToast("블루투스 연결 성공");
                });
            } catch (IOException e) {
                requireActivity().runOnUiThread(() -> {
                    lightingViewModel.setIsConnected(false);
                    lightingViewModel.setConnectionStatus("연결 실패");
                    showToast("블루투스 연결 실패: " + e.getMessage());
                });
            }
        }).start();
    }

    private void setupButtons() {
        binding.btnLightToggle.setOnClickListener(v -> {
            if (lightingViewModel.getIsConnected().getValue() != null && 
                lightingViewModel.getIsConnected().getValue()) {
                isLightOn = !isLightOn;
                sendCommand(isLightOn ? "1" : "0");
                binding.btnLightToggle.setText(isLightOn ? "조명 Off" : "조명 On");
            } else {
                showToast("블루투스가 연결되지 않았습니다");
            }
        });

        binding.btnBlue.setOnClickListener(v -> {
            if (lightingViewModel.getIsConnected().getValue() != null && 
                lightingViewModel.getIsConnected().getValue()) {
                sendColorCommand(0, 0, 255);
                showToast("블루 색상이 선택되었습니다");
            } else {
                showToast("블루투스가 연결되지 않았습니다");
            }
        });

        // 다른 버튼들도 같은 방식으로 처리
    }

    private void sendCommand(String command) {
        if (lightingViewModel.getIsConnected().getValue() == null || 
            !lightingViewModel.getIsConnected().getValue()) {
            showToast("블루투스가 연결되지 않았습니다");
            return;
        }

        new Thread(() -> {
            try {
                lightingViewModel.getBluetoothManager().sendData(command);
            } catch (IOException e) {
                requireActivity().runOnUiThread(() -> 
                    showToast("명령 전송 실패: " + e.getMessage()));
            }
        }).start();
    }

    private void sendColorCommand(int red, int green, int blue) {
        String command = String.format("C%d,%d,%d", red, green, blue);
        sendCommand(command);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                         @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && 
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initializeBluetooth();
            } else {
                showToast("블루투스 권한이 필요합니다");
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ENABLE_BT_REQUEST_CODE) {
            if (resultCode == -1) { // RESULT_OK
                connectToDevice();
            } else {
                showToast("블루투스를 활성화해야 합니다");
            }
        }
    }

    private void showToast(String message) {
        if (getActivity() != null) {
            requireActivity().runOnUiThread(() -> 
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            if (lightingViewModel.getBluetoothManager() != null) {
                lightingViewModel.getBluetoothManager().disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        binding = null;
    }

    private void observeViewModel() {
        // 연결 상태 관찰
        lightingViewModel.getConnectionStatus().observe(getViewLifecycleOwner(), status -> {
            binding.connectionStatus.setText("연결 상태: " + status);
        });

        // 연결 여부 관찰
        lightingViewModel.getIsConnected().observe(getViewLifecycleOwner(), isConnected -> {
            // 연결 상태에 따른 UI 업데이트
            binding.btnLightToggle.setEnabled(isConnected);
            binding.btnBlue.setEnabled(isConnected);
            binding.btnGreen.setEnabled(isConnected);
            binding.btnBrown.setEnabled(isConnected);
        });
    }
}