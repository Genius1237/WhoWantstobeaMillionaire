package com.genius1237.whowantstobeamillionaire;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Anirudh on 15-Nov-15.
 */
public class QuizGameAdapter {

    private QuizGameHelper quizGameHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;

    public QuizGameAdapter(Context context) {
        this.context = context;
    }

    private void open() {
        quizGameHelper = new QuizGameHelper(context);
        sqLiteDatabase = quizGameHelper.getWritableDatabase();
    }

    private void close() {
        quizGameHelper.close();
        sqLiteDatabase.close();
    }

    public long insertQuestion(String Question, String OptionA, String OptionB, String OptionC, String OptionD, String Answer) {
        open();

        ContentValues cv = new ContentValues();
        cv.put(QuizGameHelper.QUESTION, Question);
        cv.put(QuizGameHelper.OPTIONA, OptionA);
        cv.put(QuizGameHelper.OPTIONB, OptionB);
        cv.put(QuizGameHelper.OPTIONC, OptionC);
        cv.put(QuizGameHelper.OPTIOND, OptionD);
        cv.put(QuizGameHelper.ANSWER, Answer);

        long id = sqLiteDatabase.insert(QuizGameHelper.TABLE1_NAME, null, cv);

        close();
        return id;
    }

    public Question getRandomQuestion() {
        int i = (int) getNoOfQuestions();
        if (i != 0) {
            Random rnd = new Random();
            int a = rnd.nextInt(i);
            Question question = getQuestion(a);
            return question;
        } else
            return null;
    }

    public int getNoOfQuestions() {
        open();
        int n = 0;
        String[] columns = {QuizGameHelper.ID1};
        Cursor cursor = sqLiteDatabase.query(QuizGameHelper.TABLE1_NAME, columns, null, null, null, null, null, null);
        if (cursor.moveToLast()) {
            n = cursor.getInt(cursor.getColumnIndex(QuizGameHelper.ID1));
        }
        close();
        return n;
    }

    public Question getQuestion(int id) {
        open();
        String[] columns = {QuizGameHelper.QUESTION, QuizGameHelper.OPTIONA, QuizGameHelper.OPTIONB, QuizGameHelper.OPTIONC, QuizGameHelper.OPTIOND, QuizGameHelper.ANSWER};
        Cursor cursor = sqLiteDatabase.query(QuizGameHelper.TABLE1_NAME, columns, null, null, null, null, null, null);
        cursor.move(id);
        if (cursor.moveToNext()) {
            String q = cursor.getString(cursor.getColumnIndex(QuizGameHelper.QUESTION));
            String a = cursor.getString(cursor.getColumnIndex(QuizGameHelper.OPTIONA));
            String b = cursor.getString(cursor.getColumnIndex(QuizGameHelper.OPTIONB));
            String c = cursor.getString(cursor.getColumnIndex(QuizGameHelper.OPTIONC));
            String d = cursor.getString(cursor.getColumnIndex(QuizGameHelper.OPTIOND));
            String ans = cursor.getString(cursor.getColumnIndex(QuizGameHelper.ANSWER));
            Question question = new Question(q, a, b, c, d, ans);
            close();
            return question;
        } else {
            close();
            return null;
        }

    }

    public void viewAllQuestions() {
        int i = 0;
        while (getQuestion(i) != null) {
            Question question = getQuestion(i);
            i++;
            Toast.makeText(context, question.displayasString(), Toast.LENGTH_SHORT).show();
        }
    }

    public long insertHighScore(String name, long score) {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(QuizGameHelper.NAME, name);
        contentValues.put(QuizGameHelper.SCORE, score);
        long id = sqLiteDatabase.insert(QuizGameHelper.TABLE2_NAME, null, contentValues);
        close();
        return id;
    }

    public String viewHighScores() {
        open();
        String[] columns = {QuizGameHelper.NAME, QuizGameHelper.SCORE};
        Cursor cursor = sqLiteDatabase.query(QuizGameHelper.TABLE2_NAME, columns, null, null, null, null, QuizGameHelper.SCORE + " DESC");
        StringBuffer sB = new StringBuffer();
        sB.append("");
        while (cursor.moveToNext()) {
            sB.append(cursor.getString(cursor.getColumnIndex(QuizGameHelper.NAME)) + " " + cursor.getString(cursor.getColumnIndex(QuizGameHelper.SCORE)) + "\n");
        }
        int l = sB.length();
        if (!sB.toString().equals("")) {
            sB.delete(l - 1, l);
        }
        close();
        return sB.toString();
    }

    public long clearAllHighScores() {
        open();
        long id = sqLiteDatabase.delete(QuizGameHelper.TABLE2_NAME, null, null);
        close();
        return id;

    }

    private static class QuizGameHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "QuizGame";
        private static final String TABLE1_NAME = "Questions";
        private static final String ID1 = "_id";
        private static final String QUESTION = "Question";
        private static final String OPTIONA = "OptionA";
        private static final String OPTIONB = "OptionB";
        private static final String OPTIONC = "OptionC";
        private static final String OPTIOND = "OptionD";
        private static final String ANSWER = "ANSWER";
        private static final String TABLE2_NAME = "HighScores";
        private static final String ID2 = "_id";
        private static final String NAME = "Name";
        private static final String SCORE = "Score";
        private static final int DATABASE_VERSION = 1;

        public QuizGameHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            createTable1(db);
            createTable2(db);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE " + TABLE1_NAME + " IF EXISTS;");
            db.execSQL("DROP TABLE " + TABLE2_NAME + " IF EXISTS;");
            onCreate(db);
        }

        public void createTable1(SQLiteDatabase db) {
            String CREATE_TABLE1 = "CREATE TABLE IF NOT EXISTS " + TABLE1_NAME + " ("
                    + ID1 + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + QUESTION + " TEXT ,"
                    + OPTIONA + " TEXT ,"
                    + OPTIONB + " TEXT ,"
                    + OPTIONC + " TEXT ,"
                    + OPTIOND + " TEXT ,"
                    + ANSWER + " CHAR(1)"
                    + " );";

            db.execSQL(CREATE_TABLE1);
        }

        public void createTable2(SQLiteDatabase db) {
            String CREATE_TABLE2 = "CREATE TABLE IF NOT EXISTS " + TABLE2_NAME + " ("
                    + ID2 + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + NAME + " TEXT ,"
                    + SCORE + " INTEGER"
                    + ");";
            db.execSQL(CREATE_TABLE2);
        }

    }
}
