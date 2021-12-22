package com.example.myquiztest;

import android.provider.BaseColumns;

public final class ContractQuiz {
    private ContractQuiz(){}
    public static class SubjectTable implements BaseColumns{
        public static final String TABLE_SUBJECT = "quiz_subject";
        public static final String COLUMN_SUBJECT = "subject";

    }

    public static class QuestionTable implements BaseColumns{
        public static final String TABLE_NAME = "test_questions";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTIONA = "optionA";
        public static final String COLUMN_OPTIONB = "optionB";
        public static final String COLUMN_OPTIONC = "optionC";
        public static final String COLUMN_OPTIOND = "optionD";
        public static final String COLUMN_ANSWERNUMBER = "answerNumber";
        public static final String COLUMN_DIFFICULT = "difficult";
        public static final String COLUMN_CONNECT_ID = "id_connect";
    }
}
