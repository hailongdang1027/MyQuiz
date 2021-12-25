package com.example.myquiztest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class ForgetPassword extends AppCompatActivity {
    private EditText emailEdit;
    private Button btnResetPassword;

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        emailEdit = findViewById(R.id.text_edit_email_reset);
        btnResetPassword = findViewById(R.id.button_reset_password);

        auth = FirebaseAuth.getInstance();

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword(){
        String email = emailEdit.getText().toString();
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email is required!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEdit.setError("Please provide valid email!");
            emailEdit.requestFocus();
            return;
        }

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ForgetPassword.this, "Check email to reset your password", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ForgetPassword.this, "Try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}