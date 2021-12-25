package com.example.myquiztest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEdit, passwordEdit;
    private Button btnLogin, btnRegister, btnForget;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        emailEdit = findViewById(R.id.text_edit_email_login);
        passwordEdit = findViewById(R.id.text_edit_password_login);
        btnLogin = findViewById(R.id.button_login);
        btnRegister = findViewById(R.id.button_register);

        btnForget = findViewById(R.id.button_forget_password);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginApp();
            }
        });
        
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerApp();
            }
        });

        btnForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgetPassword.class));
            }
        });
    }

    private void registerApp() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void loginApp() {
        String email, password;
        email = emailEdit.getText().toString();
        password = passwordEdit.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter email!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEdit.setError("Please provide valid email!");
            emailEdit.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter password!", Toast.LENGTH_SHORT).show();
            return;
        }



        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Login successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "Login failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}