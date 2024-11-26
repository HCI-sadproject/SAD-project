package com.example.hci;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth_Da;
    private FirebaseUser mFirebaseUser_Da;
    private EditText idText_Da, pwText_Da;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mFirebaseAuth_Da = FirebaseAuth.getInstance();
        mFirebaseUser_Da = FirebaseAuth.getInstance().getCurrentUser();

        idText_Da = findViewById(R.id.idText);
        pwText_Da = findViewById(R.id.pwText);

        // 로그인
        Button loginButton_Da = findViewById(R.id.loginButton);
        loginButton_Da.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strEmail = idText_Da.getText().toString();
                String strPw = pwText_Da.getText().toString();

                if (TextUtils.isEmpty(strEmail) || TextUtils.isEmpty(strPw)) {
                    Toast.makeText(LoginActivity.this, "아이디와 비밀번호 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    mFirebaseAuth_Da.signInWithEmailAndPassword(strEmail, strPw).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(loginIntent);
                                finish();
                            } else {
                                String errorMessage = task.getException().getMessage();
                                Toast.makeText(LoginActivity.this, "로그인 실패\n" + errorMessage, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        // 회원가입
        TextView registerButton_Da = findViewById(R.id.registerButton);
        registerButton_Da.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });
    }
}