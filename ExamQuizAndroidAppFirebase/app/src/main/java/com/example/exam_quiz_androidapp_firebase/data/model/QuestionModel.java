package com.example.exam_quiz_androidapp_firebase.data.model;

import com.google.firebase.firestore.DocumentId;

public class QuestionModel {
    @DocumentId
    String questionid;
    String question, answer, optionA, optionB, optionC;
    Long timer;


    // for firebase
    public QuestionModel() {

    }

    public QuestionModel(String questionid, String question, String answer, String optiona, String optionb, String optionc, Long timer) {
        this.questionid = questionid;
        this.question = question;
        this.answer = answer;
        this.optionA = optiona;
        this.optionB = optionb;
        this.optionC = optionc;
        this.timer = timer;
    }

    public String getQuestionid() {
        return questionid;
    }

    public void setQuestionid(String questionid) {
        this.questionid = questionid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getOptiona() {
        return optionA;
    }

    public void setOptiona(String optiona) {
        this.optionA = optiona;
    }

    public String getOptionb() {
        return optionB;
    }

    public void setOptionb(String optionb) {
        this.optionB = optionb;
    }

    public String getOptionc() {
        return optionC;
    }

    public void setOptionc(String optionc) {
        this.optionC = optionc;
    }

    public Long getTimer() {
        return timer;
    }

    public void setTimer(Long timer) {
        this.timer = timer;
    }
}

