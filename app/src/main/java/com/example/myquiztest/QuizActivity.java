package com.example.myquiztest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity {
    public static final String EXTRA_SCORE = "exScore";

    private static final long COUNTDOWN_IN_MILLIS = 30000;
    private static final String  KEY_SCORE = "keyScore";
    private static final String  KEY_QUESTION_COUNT = "keyQuestionCount";
    private static final String  KEY_MILLIS_LEFT= "keyMillisLeft";
    private static final String  KEY_ANSWER = "keyAnswer";
    private static final String  KEY_QUESTION_LIST = "keyQuestionList";


    private TextView textQuestion;
    private TextView textScore;
    private TextView textQuestionCount;
    private TextView textDifficult;
    private TextView textName;
    private TextView textSubject;

    private TextView textCountDown;
    private RadioGroup radioGroup;
    private RadioButton radioButtonA;
    private RadioButton radioButtonB;
    private RadioButton radioButtonC;
    private RadioButton radioButtonD;
    private Button buttonConfirmNext;

    private ColorStateList textColorDefaultRadioButton;
    private ColorStateList textColorDefaultCountDown;
    private CountDownTimer countDownTimer;
    private long timeLeftMillis;


    private ArrayList<Question> questionList;
    private int questionCount;
    private int questionCountTotal;
    private Question currentQt;

    private int score;
    private boolean answerQues;

    private long backPressTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        textQuestion = findViewById(R.id.text_question);
        textScore = findViewById(R.id.text_score);
        textCountDown = findViewById(R.id.text_countdown);
        textQuestionCount = findViewById(R.id.text_question_count);
        textDifficult = findViewById(R.id.text_difficult);
        textName = findViewById(R.id.text_name);
        textSubject = findViewById(R.id.text_subject);

        radioGroup = findViewById(R.id.radio_group);
        radioButtonA = findViewById(R.id.radio_buttonA);
        radioButtonB = findViewById(R.id.radio_buttonB);
        radioButtonC = findViewById(R.id.radio_buttonC);
        radioButtonD = findViewById(R.id.radio_buttonD);
        buttonConfirmNext = findViewById(R.id.button_confirm_next);

        textColorDefaultRadioButton = radioButtonA.getTextColors();
        textColorDefaultCountDown = textCountDown.getTextColors();

        Intent intent = getIntent();
        int idConnect = intent.getIntExtra(MainActivity.EXTRA_CONNECT_ID, 0);
        String nameSubject = intent.getStringExtra(MainActivity.EXTRA_SUBJECT);
        String difficult = intent.getStringExtra(MainActivity.EXTRA_DIFFICULT);
        String person = intent.getStringExtra(MainActivity.NAME_PERSON);
        textSubject.setText("Subject: " + nameSubject);

        textDifficult.setText("Level: " + difficult);
        textName.setText("Name: " + person);

        if (savedInstanceState == null){
            DatabaseQuiz databaseQuiz = DatabaseQuiz.getInstance(this);
            questionList = databaseQuiz.getQuestions(idConnect, difficult);
            questionCountTotal = questionList.size();
            Collections.shuffle(questionList);
            nextQuestion();
        }else {
            questionList = savedInstanceState.getParcelableArrayList(KEY_QUESTION_LIST);
            questionCountTotal = questionList.size();
            questionCount = savedInstanceState.getInt(KEY_QUESTION_COUNT);
            currentQt = questionList.get(questionCount - 1);
            score = savedInstanceState.getInt(KEY_MILLIS_LEFT);
            timeLeftMillis = savedInstanceState.getLong(KEY_MILLIS_LEFT);
            answerQues = savedInstanceState.getBoolean(KEY_ANSWER);

            if (!answerQues){
                startCount();
            }else {
                updateCount();
                Solution();
            }
        }


        buttonConfirmNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!answerQues){
                    if(radioButtonA.isChecked() || radioButtonB.isChecked() || radioButtonC.isChecked() || radioButtonD.isChecked()){
                        checkAnswer();
                    }else {
                        Toast.makeText(QuizActivity.this,"Please select answer" ,Toast.LENGTH_SHORT).show();
                    }
                }else {
                    nextQuestion();
                }
            }
        });
    }


    private void nextQuestion(){
        radioButtonA.setTextColor(textColorDefaultRadioButton);
        radioButtonB.setTextColor(textColorDefaultRadioButton);
        radioButtonC.setTextColor(textColorDefaultRadioButton);
        radioButtonD.setTextColor(textColorDefaultRadioButton);
        radioGroup.clearCheck();

        if(questionCount < questionCountTotal){
            currentQt = questionList.get(questionCount);
            textQuestion.setText(currentQt.getQuestion());
            radioButtonA.setText(currentQt.getOptionA());
            radioButtonB.setText(currentQt.getOptionB());
            radioButtonC.setText(currentQt.getOptionC());
            radioButtonD.setText(currentQt.getOptionD());

            questionCount++;
            textQuestionCount.setText("Question: " + questionCount + "/" + questionCountTotal);
            answerQues = false;
            buttonConfirmNext.setText("Confirm");
            timeLeftMillis = COUNTDOWN_IN_MILLIS;
            startCount();
        }
        else {
            finishQuizTest();
        }
    }

    private void startCount(){
        countDownTimer = new CountDownTimer(timeLeftMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftMillis = millisUntilFinished;
                updateCount();
            }

            @Override
            public void onFinish() { //when finish time
                timeLeftMillis = 0;
                updateCount();
                checkAnswer();
            }
        }.start();
    }

    private void updateCount(){
        int minute = (int)(timeLeftMillis / 1000) / 60;
        int second = (int)(timeLeftMillis /1000) % 60;
        String formatTime = String.format(Locale.getDefault(), "%02d:%02d", minute, second);

        textCountDown.setText(formatTime);
        if (timeLeftMillis < 5000){
            textCountDown.setTextColor(Color.RED);
        }else {
            textCountDown.setTextColor(textColorDefaultCountDown);
        }
    }

    private void checkAnswer(){
        answerQues = true;
        countDownTimer.cancel();
        RadioButton radioButtonSelect = findViewById(radioGroup.getCheckedRadioButtonId());
        int answerNumber = radioGroup.indexOfChild(radioButtonSelect) + 1;
        if (answerNumber == currentQt.getAnswerNumber()){
            score++;
            textScore.setText("Your score: " + score);
        }

        Solution();
    }

    private void Solution(){
        radioButtonA.setTextColor(Color.RED);
        radioButtonB.setTextColor(Color.RED);
        radioButtonC.setTextColor(Color.RED);
        radioButtonD.setTextColor(Color.RED);
        switch (currentQt.getAnswerNumber()){
            case 1:
                radioButtonA.setTextColor(Color.YELLOW);
                textQuestion.setText("A is correct");
                break;
            case 2:
                radioButtonB.setTextColor(Color.YELLOW);
                textQuestion.setText("B is correct");
                break;
            case 3:
                radioButtonC.setTextColor(Color.YELLOW);
                textQuestion.setText("C is correct");
                break;
            case 4:
                radioButtonD.setTextColor(Color.YELLOW);
                textQuestion.setText("D is correct");
                break;
        }

        if (questionCount < questionCountTotal){
            buttonConfirmNext.setText("Next");
        }else {
            buttonConfirmNext.setText("Finish");
        }
    }

    private void finishQuizTest(){
        Intent intentResult = new Intent();
        intentResult.putExtra(EXTRA_SCORE, score);
        setResult(RESULT_OK, intentResult);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (backPressTime + 2000 > System.currentTimeMillis()){
            finishQuizTest();
        }else {
            Toast.makeText(this, "Press again to finish", Toast.LENGTH_SHORT).show();
        }
        backPressTime = System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null){
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SCORE, score);
        outState.putInt(KEY_QUESTION_COUNT, questionCount);
        outState.putLong(KEY_MILLIS_LEFT, timeLeftMillis);
        outState.putBoolean(KEY_ANSWER, answerQues);
        outState.putParcelableArrayList(KEY_QUESTION_LIST, questionList);
    }
}