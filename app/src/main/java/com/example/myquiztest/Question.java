package com.example.myquiztest;

import android.os.Parcel;
import android.os.Parcelable;


public class Question implements Parcelable {
    public static final String DIFFICULT_EASY = "Easy";
    public static final String DIFFICULT_MEDIUM = "Medium";
    public static final String DIFFICULT_HARD = "Hard";

    private int id;
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private int answerNumber;
    private String difficult;
    private int idConnect;

    public Question(){}
    public Question(String question, String optionA, String optionB, String optionC, String optionD, int answerNumber, String difficult, int idConnect) {
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.answerNumber = answerNumber;
        this.difficult = difficult;
        this.idConnect = idConnect;
    }

    protected Question(Parcel in) {
        id = in.readInt();
        question = in.readString();
        optionA = in.readString();
        optionB = in.readString();
        optionC = in.readString();
        optionD = in.readString();
        answerNumber = in.readInt();
        difficult = in.readString();
        idConnect = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(question);
        dest.writeString(optionA);
        dest.writeString(optionB);
        dest.writeString(optionC);
        dest.writeString(optionD);
        dest.writeInt(answerNumber);
        dest.writeString(difficult);
        dest.writeInt(idConnect);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public int getAnswerNumber() {
        return answerNumber;
    }

    public void setAnswerNumber(int answerNumber) {
        this.answerNumber = answerNumber;
    }

    public String getDifficult() {
        return difficult;
    }

    public void setDifficult(String difficult) {
        this.difficult = difficult;
    }

    public int getIdConnect() {
        return idConnect;
    }

    public void setIdConnect(int idConnect) {
        this.idConnect = idConnect;
    }

    public static String[] getAllLevels(){
        return new String[]{
                DIFFICULT_EASY,
                DIFFICULT_MEDIUM,
                DIFFICULT_HARD
        };
    }
}
