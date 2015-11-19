package com.genius1237.whowantstobeamillionaire;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class StartScreenActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_start_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_adminmode:
                startActivity(new Intent(this, AdminModeActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
    }

    public void StartNewGame(View view) {
        startActivity(new Intent(this, GameActivity.class));
    }


    public void viewHighScores(View view) {
        QuizGameAdapter quizGameAdapter = new QuizGameAdapter(this);
        String hs = quizGameAdapter.viewHighScores();
        if (hs.equals(""))
        {
            Toast.makeText(this, "No Highscores found", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, hs, Toast.LENGTH_LONG).show();
        }
    }
}
