package com.genius1237.whowantstobeamillionaire;

import android.content.Context;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by Anirudh on 15-Nov-15.
 */
public class Question {
    private String Question;
    private String OptionA;
    private String OptionB;
    private String OptionC;
    private String OptionD;
    private String Answer;

    public Question() {
        Question = "";
        OptionA = "";
        OptionB = "";
        OptionC = "";
        OptionD = "";
        Answer = "";
    }

    public Question(String Q, String OA, String OB, String OC, String OD, String A) {
        Question = Q;
        OptionA = OA;
        OptionB = OB;
        OptionC = OC;
        OptionD = OD;
        Answer = A;
    }

    public long write(Context context) //Writes the question to theh SQL database
    {
        QuizGameAdapter quizGameAdapter = new QuizGameAdapter(context);
        long no = quizGameAdapter.insertQuestion(Question, OptionA, OptionB, OptionC, OptionD, Answer);
        return no;
    }

    public void display() //Displays the question in the layout activity_question.xml
    {

    }


    public String displayasString() {
        String s = (Question + "\n" +
                "A: " + OptionA + "\n" +
                "B: " + OptionB + "\n" +
                "C: " + OptionC + "\n" +
                "D: " + OptionD + "\n" +
                "Answer: " + Answer);
        return s;
    }

    public String getQuestion() {
        return Question;
    }

    public String getOptionA() {
        return OptionA;
    }
    public String getOptionB() {
        return OptionB;
    }
    public String getOptionC() {
        return OptionC;
    }
    public String getOptionD() {
        return OptionD;
    }

    public boolean checkAnswer(String option) {
        if(option.equals(Answer))
            return true;
        return false;
    }
}
