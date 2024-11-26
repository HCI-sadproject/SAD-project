package com.example.hci;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth_Da;
    private DatabaseReference mDatabaseReference_Da;
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

        register_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.register_man) {
                    selectedGender = "남성";
                } else if (checkedId == R.id.register_woman) {
                    selectedGender = "여성";
                }
            }
        });

        register_marital_status.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.register_single) {
                    selectedMaritalStatus = "미혼";
                } else if (checkedId == R.id.register_married) {
                    selectedMaritalStatus = "결혼";
                } else if (checkedId == R.id.register_divorced) {
                    selectedMaritalStatus = "이혼";
                } else if (checkedId == R.id.register_widowed) {
                    selectedMaritalStatus = "사혼";
                }
            }
        });

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                mFirebaseAuth_Da.createUserWithEmailAndPassword(strEmail,strPW).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
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

                            mDatabaseReference_Da.child("UserAccount").child(firebaseUser.getUid()).setValue(account)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> databasetask) {
                                            if (databasetask.isSuccessful()) {
                                                Toast.makeText(RegisterActivity.this, "회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show();
                                                Intent registerIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                startActivity(registerIntent);
                                            } else {
                                                Toast.makeText(RegisterActivity.this, "데이터베이스 업로드 실패", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            String errorMessage = task.getException().getMessage();
                            Toast.makeText(RegisterActivity.this, "회원가입 실패: \n" + errorMessage, Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        backBtn_Da = findViewById(R.id.backButton);
        backBtn_Da.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(backIntent);
            }
        });
    }
}