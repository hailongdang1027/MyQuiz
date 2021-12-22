package com.example.myquiztest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        TextView textViewNotification = findViewById(R.id.text_view_notification);

        String message = getIntent().getStringExtra("message");
        textViewNotification.setText(message);
    }
}