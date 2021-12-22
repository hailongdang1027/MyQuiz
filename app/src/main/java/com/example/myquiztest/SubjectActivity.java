package com.example.myquiztest;

public class SubjectActivity {
    public static final int MATH = 1;
    public static final int ENGLISH = 2;
    public static final int GEOGRAPHY = 3;

    private int id;
    private String nameSubject;
    public SubjectActivity(){
    }

    public SubjectActivity(String nameSubject) {
        this.nameSubject = nameSubject;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameSubject() {
        return nameSubject;
    }

    public void setNameSubject(String nameSubject) {
        this.nameSubject = nameSubject;
    }

    @Override
    public String toString() {
        return getNameSubject();
    }
}
