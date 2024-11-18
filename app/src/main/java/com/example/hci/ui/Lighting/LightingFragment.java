package com.example.hci.ui.Lighting;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
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
import java.util.Set;

public class LightingFragment extends Fragment {

    private FragmentLightingBinding binding;
    private LightingViewModel lightingViewModel;
    private boolean isLightOn = false;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int ENABLE_BT_REQUEST_CODE = 2;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                            ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLightingBinding.inflate(inflater, container, false);
        lightingViewModel = new ViewModelProvider(this).get(LightingViewModel.class);

        setupButtons();
        observeViewModel();

        return binding.getRoot();
    }

    private void setupButtons() {
        // 블루투스 연결 버튼
        binding.btnConnect.setOnClickListener(v -> {
            checkBluetoothPermissions();
        });

        // 조명 토글 버튼
        binding.btnLightToggle.setOnClickListener(v -> {
            if (lightingViewModel.getIsConnected().getValue() != null && 
                lightingViewModel.getIsConnected().getValue()) {
                isLightOn = !isLightOn;
                sendCommand(isLightOn ? "ON" : "OFF");
                binding.btnLightToggle.setText(isLightOn ? "조명 Off" : "조명 On");
            } else {
                showToast("먼저 블루투스 연결 버튼을 눌러 연결해주세요");
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

        // 컬러 피커 설정
        binding.colorPicker.subscribe((color, fromUser, shouldPropagate) -> {
            if (fromUser && lightingViewModel.getIsConnected().getValue() != null && 
                lightingViewModel.getIsConnected().getValue()) {
                int red = (color >> 16) & 0xFF;
                int green = (color >> 8) & 0xFF;
                int blue = color & 0xFF;
                sendColorCommand(red, green, blue);
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
                findBluetoothDevices();
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

    private void findBluetoothDevices() {
        if (ActivityCompat.checkSelfPermission(requireContext(), 
                Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                // HC-06 기기를 찾으면 자동으로 연 시도
                if (deviceName != null && deviceName.equals("HC-06")) {
                    connectToDevice(device.getAddress());
                    return;
                }
            }
            showToast("HC-06을 찾을 수 없습니다. 페어링이 되어있는지 확인해주세요.");
        }
    }

    private void connectToDevice(String deviceAddress) {
        if (ActivityCompat.checkSelfPermission(requireContext(), 
                Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        new Thread(() -> {
            try {
                lightingViewModel.getBluetoothManager().connect(deviceAddress);
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
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (bluetoothAdapter == null) {
                showToast("이 기기는 블루투스를 지원하지 않습니다");
                return;
            }

            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, ENABLE_BT_REQUEST_CODE);
            } else {
                findBluetoothDevices();
            }
        } catch (Exception e) {
            showToast("블루투스 초기화 실패: " + e.getMessage());
        }
    }
}