package com.example.exam_quiz_androidapp_firebase.data.model;

import com.google.firebase.firestore.DocumentId;

import java.io.Serializable;

public class QuizModel implements Serializable {
    String quizid;
    String quizname,description,image,level,visibility;
    int questions;

    public QuizModel() {
    }

    public QuizModel(String quizid, String quizname, String description, String image, String level, String visibility, int questions) {
        this.quizid = quizid;
        this.quizname = quizname;
        this.description = description;
        this.image = image;
        this.level = level;
        this.visibility = visibility;
        this.questions = questions;
    }

    public String getQuizid() {
        return quizid;
    }

    public void setQuizid(String quizid) {
        this.quizid = quizid;
    }

    public String getQuizname() {
        return quizname;
    }

    public void setQuizname(String quizname) {
        this.quizname = quizname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public int getQuestions() {
        return questions;
    }

    public void setQuestions(int questions) {
        this.questions = questions;
    }
}
