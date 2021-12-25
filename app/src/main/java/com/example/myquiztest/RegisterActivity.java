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
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private EditText emailEdit, passwordEdit, nameEdit, passAgainEdit;
    private Button btnRegister2;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        emailEdit = findViewById(R.id.text_edit_email_register);
        passwordEdit = findViewById(R.id.text_edit_password_register);

        btnRegister2 = findViewById(R.id.button_register_2);
        nameEdit = findViewById(R.id.text_edit_name_register);
        passAgainEdit = findViewById(R.id.text_edit_password_again_register);

        
        btnRegister2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerApp2();
            }
        });
    }

    private void registerApp2() {
        String email, password, fullName, passAgain;
        email = emailEdit.getText().toString();
        password = passwordEdit.getText().toString();
        passAgain = passAgainEdit.getText().toString();
        fullName = nameEdit.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please provide email!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEdit.setError("Please provide valid email!");
            emailEdit.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please provide password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(passAgain)){
            Toast.makeText(this, "Please provide password again!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(fullName)){
            Toast.makeText(this, "Please provide name!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6){
            passwordEdit.setError("Password length should be more 6 characters");
            passwordEdit.requestFocus();
            return;
        }

        if (passAgain.length() != password.length()){
            passAgainEdit.setError("Try again pass");
            passAgainEdit.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    User user = new User(fullName, email);
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "User has been successfully", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(RegisterActivity.this, "Failed to register! Try again!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Failed to register! Try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}