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
import android.widget.Button;
import androidx.annotation.Nullable;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hci.databinding.FragmentLightingBinding;
import com.example.hci.bluetooth.BluetoothManager;

import java.io.IOException;
import java.util.Set;
import android.graphics.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LightingFragment extends Fragment {

    private final List<Map<String, Object>> colors = new ArrayList<>();
    public static double publicValue = 15.27; // 조건에 사용할 public 변수

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

        // 색상 데이터 초기화
        initializeColors();

        // 조건에 따라 색상 필터링
        List<Map<String, Object>> filteredColors = filterColors(publicValue);

        // 랜덤으로 중복되지 않는 색상 3개 선택
        List<Map<String, Object>> randomColors = getRandomColors(filteredColors, 3);

        // 버튼에 랜덤 색상 설정 및 이벤트 연결
        setupButtons(randomColors);

        //setupButtons();
        observeViewModel();

        return binding.getRoot();
    }

    // 색상 데이터 초기화
    private void initializeColors() {
        colors.clear();
        colors.add(createColor("IndiRed", 205, 92, 92));
        colors.add(createColor("FireBrick", 178, 34, 34));
        colors.add(createColor("Salmon", 250, 128, 114));
        colors.add(createColor("DarkOrange", 255, 140, 0));
        colors.add(createColor("Wheat", 245, 222, 179));
        colors.add(createColor("Gold", 255, 215, 0));
        colors.add(createColor("Olive", 128, 128, 0));
        colors.add(createColor("Forest", 34, 139, 34));
        colors.add(createColor("Aqua", 127, 256, 212));
        colors.add(createColor("Violet", 102, 51, 153));
        colors.add(createColor("DeepSky", 0, 191, 255));
        colors.add(createColor("Lavender", 230, 230, 250));
        colors.add(createColor("Pink", 255, 182, 193));

    }

    // 특정 조건에 따라 색상 필터링
    private List<Map<String, Object>> filterColors(double value) {
        List<Map<String, Object>> filteredColors = new ArrayList<>();
        if(value > 11){
            filteredColors.add(createColor("IndiRed", 205, 92, 92));
            filteredColors.add(createColor("FireBrick", 178, 34, 34));
            filteredColors.add(createColor("Salmon", 250, 128, 114));
            filteredColors.add(createColor("DarkOrange", 255, 140, 0));
            filteredColors.add(createColor("Wheat", 245, 222, 179));
            filteredColors.add(createColor("Gold", 255, 215, 0));
            filteredColors.add(createColor("Pink", 255, 182, 193));
        } else {
            filteredColors = colors;
        }
        return filteredColors;
    }

    // 색상 정보 생성
    private Map<String, Object> createColor(String name, int r, int g, int b) {
        Map<String, Object> color = new HashMap<>();
        color.put("name", name); // 색상 이름
        color.put("r", r);
        color.put("g", g);
        color.put("b", b);
        return color;
    }

    private List<Map<String, Object>> getRandomColors(List<Map<String, Object>> availableColors, int count) {
        List<Map<String, Object>> shuffledColors = new ArrayList<>(availableColors);
        Collections.shuffle(shuffledColors); // 랜덤으로 섞기
        return shuffledColors.subList(0, Math.min(count, shuffledColors.size())); // 원하는 개수만큼 반환
    }


    private void setupButtons(List<Map<String, Object>> colors) {
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

        Map<String, Object> color1 = colors.get(0);
        String colorName1 = (String) color1.get("name");
        int r1 = (int)color1.get("r");
        int g1 = (int)color1.get("g");
        int b1 = (int)color1.get("b");
        binding.btnOne.setText(colorName1);
        binding.btnOne.setBackgroundColor(Color.rgb(r1,g1,b1)); // 배경색 설정

        binding.btnOne.setOnClickListener(v -> {
            if (lightingViewModel.getIsConnected().getValue() != null && 
                lightingViewModel.getIsConnected().getValue()) {
                sendColorCommand(r1,g1,b1);
                showToast(colorName1+" 색상이 선택되었습니다");
            } else {
                showToast("블루투스가 연결되지 않았습니다");
            }
        });

        Map<String, Object> color2 = colors.get(1);
        String colorName2 = (String) color2.get("name");
        int r2 = (int)color2.get("r");
        int g2 = (int)color2.get("g");
        int b2 = (int)color2.get("b");
        binding.btnTwo.setText(colorName2);
        binding.btnTwo.setBackgroundColor(Color.rgb(r2,g2,b2)); // 배경색 설정

        binding.btnTwo.setOnClickListener(v -> {
            if (lightingViewModel.getIsConnected().getValue() != null && 
                lightingViewModel.getIsConnected().getValue()) {
                sendColorCommand(r2,g2,b2);
                showToast(colorName2+" 색상이 선택되었습니다");
            } else {
                showToast("블루투스가 연결되지 않았습니다");
            }
        });

        Map<String, Object> color3 = colors.get(2);
        String colorName3 = (String) color3.get("name");
        int r3 = (int)color3.get("r");
        int g3 = (int)color3.get("g");
        int b3 = (int)color3.get("b");
        binding.btnThree.setText(colorName3);
        binding.btnThree.setBackgroundColor(Color.rgb(r3,g3,b3)); // 배경색 설정


        binding.btnThree.setOnClickListener(v -> {
            if (lightingViewModel.getIsConnected().getValue() != null && 
                lightingViewModel.getIsConnected().getValue()) {
                sendColorCommand(r3,g3,b3);
                showToast(colorName2+" 색상이 선택되었습니다");
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
            binding.btnOne.setEnabled(isConnected);
            binding.btnTwo.setEnabled(isConnected);
            binding.btnThree.setEnabled(isConnected);
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