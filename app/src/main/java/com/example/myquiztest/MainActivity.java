package com.example.myquiztest;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_QUIZ = 1;
    public static final String EXTRA_DIFFICULT = "extraDifficult";
    public static final String EXTRA_CONNECT_ID = "extraConnectID";
    public static final String EXTRA_SUBJECT = "extraSubject";
    public static final String KEY_HIGHSCORE = "keyHighscore";
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String NAME_PERSON = "namePerson";
    private static final String NOTIFICATION_CHANNEL_ID = "1";


    private TextView textHighScore;
    private Spinner spinnerSubject;
    private Spinner spinnerDifficult;

    private int highscore;

    private EditText namePerson;
    private EditText idPerson;


    Button buttonNotification;
    Button buttonConnectCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textHighScore = findViewById(R.id.text_highscore);
        spinnerDifficult = findViewById(R.id.spinner_difficult);
        spinnerSubject = findViewById(R.id.spinner_subject);
        namePerson = findViewById(R.id.text_edit_name);
        idPerson = findViewById(R.id.text_edit_id);

        buttonNotification = findViewById(R.id.button_notification);
        buttonConnectCamera = findViewById(R.id.button_connect_camera);

        int permissionStatus = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS);
        createNotificationChannel();



        buttonNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotification();
            }
        });

        buttonConnectCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPageCamera();
            }
        });


        loadSubjects();
        loadDifficultLevels();

        loadHighScores();


        Button buttonStart = findViewById(R.id.button_start);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTest();
            }
        });


    }

    private void showNotification(){
        String message = "Welcome to my test";
        NotificationCompat.Builder builder = new
                NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Test")
                .setContentText("Hello")
                .setStyle(new NotificationCompat.BigTextStyle());
        Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("message", message);

        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);
        Random random = new Random();
        notificationManager.notify(random.nextInt(), builder.build());
    }

    private void showPageCamera(){
        Intent intent = new Intent(MainActivity.this, CameraActivity.class);
        startActivity(intent);
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        CharSequence name = "Notifications";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }


    private void startTest(){
        SubjectActivity selectedSubject = (SubjectActivity) spinnerSubject.getSelectedItem();
        int idConnect = selectedSubject.getId();
        String nameSubject = selectedSubject.getNameSubject();
        String nameEdit;
        String idEdit;
        nameEdit = namePerson.getText().toString();
        idEdit = idPerson.getText().toString();



        if (TextUtils.isEmpty(nameEdit)){
            Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(idEdit)){
            Toast.makeText(this, "Please enter id", Toast.LENGTH_SHORT).show();
            return;
        }

        String person = namePerson.getText().toString();
        String difficult = spinnerDifficult.getSelectedItem().toString();
        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
        intent.putExtra(EXTRA_CONNECT_ID, idConnect);
        intent.putExtra(EXTRA_SUBJECT, nameSubject);
        intent.putExtra(EXTRA_DIFFICULT, difficult);
        intent.putExtra(NAME_PERSON, person);
        startActivityForResult(intent, REQUEST_CODE_QUIZ);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_QUIZ){
            if (resultCode == RESULT_OK){
                int score = data.getIntExtra(QuizActivity.EXTRA_SCORE, 0);
                if (score > highscore){
                    updateHighScores(score);
                }
            }
        }
    }

    private void loadSubjects(){
        DatabaseQuiz databaseQuiz = DatabaseQuiz.getInstance(this);
        List<SubjectActivity> subjectActivities = databaseQuiz.getAllSubjects();
        ArrayAdapter<SubjectActivity> adapterSubjects = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subjectActivities);
        adapterSubjects.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubject.setAdapter(adapterSubjects);
    }

    private void loadDifficultLevels(){
        String[] difficultLevels = Question.getAllLevels();
        ArrayAdapter<String> adapterDifficult = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, difficultLevels);
        adapterDifficult.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDifficult.setAdapter(adapterDifficult);
    }

    private void loadHighScores(){
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        highscore = prefs.getInt(KEY_HIGHSCORE, 0);
        textHighScore.setText("Highscore: " + highscore);
    }

    private void updateHighScores(int newScore){
        highscore = newScore;
        textHighScore.setText("Your score: " + highscore);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_HIGHSCORE, highscore);
        editor.apply();
    }
}