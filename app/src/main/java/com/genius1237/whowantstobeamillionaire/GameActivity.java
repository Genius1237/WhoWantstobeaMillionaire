package com.genius1237.whowantstobeamillionaire;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class GameActivity extends AppCompatActivity {

    RadioButton rBOA, rBOB, rBOC, rBOD;
    long score;
    Question question;
    QuizGameAdapter quizGameAdapter;
    String option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Intent i = getIntent();
        score = i.getLongExtra("score", 0);
        option="";
        assignradioButtons();
        quizGameAdapter = new QuizGameAdapter(this);
        setQuestion();
    }

    public void SubmitAClicked(View view) {

        if (rBOA.isChecked()) {
            option = "A";
            rBOA.setChecked(false);
        }
        if (rBOB.isChecked()) {
            option = "B";
            rBOB.setChecked(false);
        }
        if (rBOC.isChecked()) {
            option = "C";
            rBOC.setChecked(false);
        }
        if (rBOD.isChecked()) {
            option = "D";
            rBOD.setChecked(false);
        }
        if (!option.equals("")) {
            if (question.checkAnswer(option)) {

                score += 10;
                Toast.makeText(this, "Answer is correct\n" + "Score is " + score + " points", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, GameActivity.class);
                intent.putExtra("score", score);
                startActivity(intent);
                finish();

            } else {
                Toast.makeText(this, "Answer is wrong\n" + "Game Over\n" + "Score is " + score + " points", Toast.LENGTH_LONG).show();
                setContentView(R.layout.name_enter_layout);

            }
        }
    }


    public void setQuestion() {
        question = quizGameAdapter.getRandomQuestion();

        TextView tVQuestion= (TextView) findViewById(R.id.tVQuestion);
        if (question != null) {
            tVQuestion.setText(question.getQuestion());
            rBOA.setText(question.getOptionA());
            rBOB.setText(question.getOptionB());
            rBOC.setText(question.getOptionC());
            rBOD.setText(question.getOptionD());
        } else
        {
            tVQuestion.setText("");
            rBOA.setText("");
            rBOB.setText("");
            rBOC.setText("");
            rBOD.setText("");
            Toast.makeText(this,"No questions found. Please add questions and try again",Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    public void assignradioButtons() {
        rBOA = (RadioButton) findViewById(R.id.rBOA);
        rBOB = (RadioButton) findViewById(R.id.rBOB);
        rBOC = (RadioButton) findViewById(R.id.rBOC);
        rBOD = (RadioButton) findViewById(R.id.rBOD);
    }

    public void rBOAClicked(View view) {
        rBOB.setChecked(false);
        rBOC.setChecked(false);
        rBOD.setChecked(false);
    }

    public void rBOBClicked(View view) {
        rBOA.setChecked(false);
        rBOC.setChecked(false);
        rBOD.setChecked(false);
    }

    public void rBOCClicked(View view) {
        rBOA.setChecked(false);
        rBOB.setChecked(false);
        rBOD.setChecked(false);
    }

    public void rBODClicked(View view) {
        rBOA.setChecked(false);
        rBOB.setChecked(false);
        rBOC.setChecked(false);
    }

    public void HSNameClicked(View view) {
        QuizGameAdapter quizGameAdapter=new QuizGameAdapter(this);
        EditText eTHSName= (EditText) findViewById(R.id.eTHSName);
        String name=eTHSName.getText().toString();
        quizGameAdapter.insertHighScore(name,score);
        finish();
    }
}
