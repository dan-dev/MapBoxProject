package com.example.danny.mapboxproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DatabaseFindQ";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_QUESTION = "questions";
    private static final String COLUMN_QUESTION_ID = "id";
    private static final String COLUMN_QUESTION_PLACEID = "placeid";
    private static final String COLUMN_QUESTION_QUESTION = "question";
    private static final String COLUMN_QUESTION_ISLOCKED = "isLocked";
    private static final String COLUMN_QUESTION_ISANSWERED = "isAnswered";
    private static final String COLUMN_QUESTION_ANSWER_A = "answer_a";
    private static final String COLUMN_QUESTION_ANSWER_B = "answer_b";
    private static final String COLUMN_QUESTION_ANSWER_C = "answer_c";
    private static final String COLUMN_QUESTION_ANSWER_D = "answer_d";
    private static final String COLUMN_QUESTION_CORRECT= "correct";

    private static final String LOG = "DataBaseBuilder";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, SQLiteDatabase liteDatabase) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SERIES_TABLE = "CREATE TABLE " + TABLE_QUESTION
                + "( " + COLUMN_QUESTION_ID + " INTEGER PRIMARY KEY, "
                + COLUMN_QUESTION_PLACEID + " INTEGER, "
                + COLUMN_QUESTION_QUESTION + " TEXT, "
                + COLUMN_QUESTION_ISLOCKED + " INTEGER, "
                + COLUMN_QUESTION_ISANSWERED + " INTEGER, "
                + COLUMN_QUESTION_ANSWER_A + " TEXT, "
                + COLUMN_QUESTION_ANSWER_B + " TEXT, "
                + COLUMN_QUESTION_ANSWER_C + " TEXT, "
                + COLUMN_QUESTION_ANSWER_D + " TEXT, "
                + COLUMN_QUESTION_CORRECT + " INTEGER "
                + " )";
        db.execSQL(CREATE_SERIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
        onCreate(db);
    }

    public void addNewQuestion(Question question){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "INSERT INTO " + TABLE_QUESTION + "( " + COLUMN_QUESTION_ID + ", "
                + COLUMN_QUESTION_PLACEID + ", " + COLUMN_QUESTION_QUESTION + ", "
                + COLUMN_QUESTION_ISLOCKED + ", " + COLUMN_QUESTION_ISANSWERED + ", "
                + COLUMN_QUESTION_ANSWER_A + ", " + COLUMN_QUESTION_ANSWER_B + ", "
                + COLUMN_QUESTION_ANSWER_C + ", " + COLUMN_QUESTION_ANSWER_D + ", "
                + COLUMN_QUESTION_CORRECT
                + ") VALUES (" + question.getId() + ", '" + question.getPlaceID() + ", "
                + question.getQuestion() + "', '" + question.getLocked() + ", '"
                + question.getAnswered() + "', '" + question.getAnswerA() + ", '"
                + question.getAnswerB() + "', '" + question.getAnswerC() + ", '"
                + question.getAnswerD() + ", '" + question.getCorrect()
                + "');";
        database.execSQL(query);
        database.close();
    }

    public void setQuestionAnswered(int id){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_QUESTION + " SET " + COLUMN_QUESTION_ISANSWERED + " = 1 "
                + " WHERE " + COLUMN_QUESTION_ID + " = " + id;
        database.execSQL(query);
        database.close();
    }

    public void unlockQuestion(int id){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_QUESTION + " SET " + COLUMN_QUESTION_ISLOCKED + " = 0 "
                + " WHERE " + COLUMN_QUESTION_ID + " = " + id;
        database.execSQL(query);
        database.close();
    }

    public ArrayList<Question> getAnsweredQuestions(){
        ArrayList<Question> list = new ArrayList<Question>();
        String query = "SELECT * FROM " + TABLE_QUESTION + " WHERE " + COLUMN_QUESTION_ISANSWERED + " = 1";
        SQLiteDatabase sq = this.getWritableDatabase();
        Cursor cursor = sq.rawQuery(query, null);
        while (cursor.moveToNext()){
            Question question = new Question();
            question.setId(cursor.getInt(0));
            question.setPlaceID(cursor.getInt(1));
            question.setQuestion(cursor.getString(2));
            question.setLocked(cursor.getInt(3));
            question.setAnswered(cursor.getInt(4));
            question.setAnswerA(cursor.getString(5));
            question.setAnswerB(cursor.getString(6));
            question.setAnswerC(cursor.getString(7));
            question.setAnswerD(cursor.getString(8));
            question.setCorrect(cursor.getInt(9));
            list.add(question);
        }
        return list;
    }

    public ArrayList<Question> getOpenQuestions(){
        ArrayList<Question> list = new ArrayList<Question>();
        String query = "SELECT * FROM " + TABLE_QUESTION + " WHERE " + COLUMN_QUESTION_ISANSWERED
                + " = 0 AND " + COLUMN_QUESTION_ISLOCKED + " = 0";
        SQLiteDatabase sq = this.getWritableDatabase();
        Cursor cursor = sq.rawQuery(query, null);
        while (cursor.moveToNext()){
            Question question = new Question();
            question.setId(cursor.getInt(0));
            question.setPlaceID(cursor.getInt(1));
            question.setQuestion(cursor.getString(2));
            question.setLocked(cursor.getInt(3));
            question.setAnswered(cursor.getInt(4));
            question.setAnswerA(cursor.getString(5));
            question.setAnswerB(cursor.getString(6));
            question.setAnswerC(cursor.getString(7));
            question.setAnswerD(cursor.getString(8));
            question.setCorrect(cursor.getInt(9));
            list.add(question);
        }
        return list;
    }




}