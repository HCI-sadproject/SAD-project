package com.example.hci;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth_Da;
    private DatabaseReference mDatabaseReference_Da;
    private FirebaseFirestore firestore;
    private EditText register_name, register_email, register_pw, register_pwCheck, register_birth, register_phone;
    private RadioGroup register_gender, register_marital_status;
    private String selectedGender, selectedMaritalStatus;

    private Button mRegisterBtn;
    private ImageButton backBtn_Da;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        mFirebaseAuth_Da = FirebaseAuth.getInstance();
        mDatabaseReference_Da = FirebaseDatabase.getInstance().getReference();
        firestore = FirebaseFirestore.getInstance(); // Firestore 초기화

        register_name = (EditText) findViewById(R.id.register_name);
        register_email = (EditText) findViewById(R.id.register_email);
        register_pw = (EditText) findViewById(R.id.register_password);
        register_pwCheck = (EditText) findViewById(R.id.register_pwCheck);
        register_birth = (EditText) findViewById(R.id.register_birth);
        register_phone = (EditText) findViewById(R.id.register_phone);

        register_gender = findViewById(R.id.register_gender);
        register_marital_status = findViewById(R.id.register_marital_status);

        RadioButton register_man = findViewById(R.id.register_man);
        RadioButton register_woman = findViewById(R.id.register_woman);
        register_man.setChecked(true);
        selectedGender = "남성";

        RadioButton register_single = findViewById(R.id.register_single);
        register_single.setChecked(true);
        selectedMaritalStatus = "미혼";

        mRegisterBtn = findViewById(R.id.registerButton);

        register_gender.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.register_man) {
                selectedGender = "남성";
            } else if (checkedId == R.id.register_woman) {
                selectedGender = "여성";
            }
        });

        register_marital_status.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.register_single) {
                selectedMaritalStatus = "미혼";
            } else if (checkedId == R.id.register_married) {
                selectedMaritalStatus = "결혼";
            } else if (checkedId == R.id.register_divorced) {
                selectedMaritalStatus = "이혼";
            } else if (checkedId == R.id.register_widowed) {
                selectedMaritalStatus = "사혼";
            }
        });

        mRegisterBtn.setOnClickListener(v -> {
            String strName = register_name.getText().toString();
            String strEmail = register_email.getText().toString();
            String strPW = register_pw.getText().toString();
            String strPWCheck = register_pwCheck.getText().toString();
            String strBirth = register_birth.getText().toString();
            String strPhone = register_phone.getText().toString();

            // 비밀번호 확인
            if (!strPW.equals(strPWCheck)) {
                Toast.makeText(RegisterActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                return;
            }

            // 생일 형식 검증
            String birthRegex = "^\\d{8}$";
            if (!strBirth.matches(birthRegex)) {
                Toast.makeText(RegisterActivity.this, "잘못된 생일 형식입니다.", Toast.LENGTH_SHORT).show();
                return;
            }

            // 전화번호 형식 검증
            String phoneRegex = "^\\d{11}$";
            if (!strPhone.matches(phoneRegex)) {
                Toast.makeText(RegisterActivity.this, "잘못된 전화번호 형식입니다.", Toast.LENGTH_SHORT).show();
                return;
            }

            mFirebaseAuth_Da.createUserWithEmailAndPassword(strEmail, strPW).addOnCompleteListener(RegisterActivity.this, task -> {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = mFirebaseAuth_Da.getCurrentUser();
                    UserAccount account = new UserAccount();
                    account.setIdToken(firebaseUser.getUid());
                    account.setName(strName);
                    account.setEmailId(firebaseUser.getEmail());
                    account.setBirthday(strBirth);
                    account.setPhoneNumber(strPhone);
                    account.setPassword(strPW);
                    account.setGender(selectedGender);

                    // 실시간 데이터베이스 저장
                    mDatabaseReference_Da.child("UserAccount").child(firebaseUser.getUid()).setValue(account)
                            .addOnCompleteListener(databasetask -> {
                                if (databasetask.isSuccessful()) {
                                    // Firestore Database에 추가
                                    Map<String, Object> profile = new HashMap<>();
                                    profile.put("name", strName);
                                    profile.put("birth", strBirth);
                                    profile.put("age", calculateAge(strBirth)); // 나이 계산
                                    profile.put("gender", selectedGender);
                                    profile.put("marital_status", selectedMaritalStatus);

                                    firestore.collection("users").document(firebaseUser.getUid())
                                            .collection("profile")
                                            .document("profileInfo")
                                            .set(profile)
                                            .addOnCompleteListener(firestoreTask -> {
                                                if (firestoreTask.isSuccessful()) {
                                                    Toast.makeText(RegisterActivity.this, "회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show();
                                                    Intent registerIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                    startActivity(registerIntent);
                                                } else {
                                                    Toast.makeText(RegisterActivity.this, "Firestore 업로드 실패", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                } else {
                                    Toast.makeText(RegisterActivity.this, "데이터베이스 업로드 실패", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    String errorMessage = task.getException().getMessage();
                    Toast.makeText(RegisterActivity.this, "회원가입 실패: \n" + errorMessage, Toast.LENGTH_LONG).show();
                }
            });
        });

        backBtn_Da = findViewById(R.id.backButton);
        backBtn_Da.setOnClickListener(v -> {
            Intent backIntent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(backIntent);
        });
    }

    // 생년월일로 나이 계산
    private int calculateAge(String birth) {
        int birthYear = Integer.parseInt(birth.substring(0, 4));
        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        return currentYear - birthYear;
    }
}