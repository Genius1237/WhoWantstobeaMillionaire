package com.genius1237.whowantstobeamillionaire;

import android.app.VoiceInteractor;
import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AdminModeActivity extends AppCompatActivity {

    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_mode_login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_viewquestions:
                viewAllQuestions();
                return true;
            case R.id.menu_randomquestion:
                viewRandomQuestion();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
//Hello
    private void viewRandomQuestion() {
        QuizGameAdapter quizGameAdapter = new QuizGameAdapter(this);
        Question question = quizGameAdapter.getRandomQuestion();
        while (question != null) {
            String s = question.displayasString();
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        }
    }

    private void viewAllQuestions() {
        QuizGameAdapter quizGameAdapter = new QuizGameAdapter(this);
        quizGameAdapter.viewAllQuestions();
    }

    public void checkPass(View view) {
        EditText eTPass = (EditText) findViewById(R.id.eTPass);
        String p = eTPass.getText().toString();
        if (p.equals("")) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            Toast.makeText(this, "Successfully logged in", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_admin_mode);
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_admin_activity, menu);

        }
    }

    public void clearAllFields() {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rGOptions);
        EditText eTQuestion = (EditText) findViewById(R.id.eTQuestion);
        EditText eTOptionA = (EditText) findViewById(R.id.eTOptionA);
        EditText eTOptionB = (EditText) findViewById(R.id.eTOptionB);
        EditText eTOptionC = (EditText) findViewById(R.id.eTOptionC);
        EditText eTOptionD = (EditText) findViewById(R.id.eTOptionD);
        radioGroup.clearCheck();
        eTQuestion.setText(null);
        eTOptionA.setText(null);
        eTOptionB.setText(null);
        eTOptionC.setText(null);
        eTOptionD.setText(null);
    }

    public void SubmitQuestion(View view) {
        //Toast.makeText(this,"Button Clicked",Toast.LENGTH_SHORT).show();
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rGOptions);
        EditText eTQuestion = (EditText) findViewById(R.id.eTQuestion);
        EditText eTOptionA = (EditText) findViewById(R.id.eTOptionA);
        EditText eTOptionB = (EditText) findViewById(R.id.eTOptionB);
        EditText eTOptionC = (EditText) findViewById(R.id.eTOptionC);
        EditText eTOptionD = (EditText) findViewById(R.id.eTOptionD);
        String Question = eTQuestion.getText().toString();
        String OptionA = eTOptionA.getText().toString();
        String OptionB = eTOptionB.getText().toString();
        String OptionC = eTOptionC.getText().toString();
        String OptionD = eTOptionD.getText().toString();
        int id = radioGroup.getCheckedRadioButtonId();
        if (!(Question.equals("") || OptionA.equals("") || OptionB.equals("") || OptionC.equals("") || OptionD.equals("") || (id == -1))) {
            String Answer;
            switch (id) {
                case R.id.rBA:
                    Answer = "A";
                    break;
                case R.id.rBB:
                    Answer = "B";
                    break;
                case R.id.rBC:
                    Answer = "C";
                    break;
                case R.id.rBD:
                    Answer = "D";
                    break;
                default:
                    Answer = "";
            }
            Question question = new Question(Question, OptionA, OptionB, OptionC, OptionD, Answer);
            long no = question.write(this);
            Toast.makeText(this, "Successfully Inserted at position " + no, Toast.LENGTH_SHORT).show();
            clearAllFields();
        }
    }

    public void clearAllFields(View view) {
        clearAllFields();
    }

    public void clearAllHighScores(View view) {
        QuizGameAdapter quizGameAdapter = new QuizGameAdapter(this);
        long id = quizGameAdapter.clearAllHighScores();
        Toast.makeText(this, "Deleted " + id + " High Scores", Toast.LENGTH_SHORT).show();
    }
}
