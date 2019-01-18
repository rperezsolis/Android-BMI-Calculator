package com.example.rafael_perez.mi_peso_ideal.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.rafael_perez.mi_peso_ideal.Data.ProgressDBContract.COLUMN_DATE;
import static com.example.rafael_perez.mi_peso_ideal.Data.ProgressDBContract.COLUMN_ICC;
import static com.example.rafael_perez.mi_peso_ideal.Data.ProgressDBContract.COLUMN_IMC;
import static com.example.rafael_perez.mi_peso_ideal.Data.ProgressDBContract.COLUMN_MG;
import static com.example.rafael_perez.mi_peso_ideal.Data.ProgressDBContract.COLUMN_USER_NAME;
import static com.example.rafael_perez.mi_peso_ideal.Data.ProgressDBContract.TABLE_NAME;
import static com.example.rafael_perez.mi_peso_ideal.Data.ProgressDBContract._ID;

public class ProgressDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "mi_peso_ideal.db";
    public static final int DATABASE_VERSION = 1;

    public ProgressDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_progress_table_query = "create table " + TABLE_NAME + "(" +
                _ID + " integer primary key autoincrement, " +
                COLUMN_USER_NAME + " string," +
                COLUMN_DATE + " string, " +
                COLUMN_IMC + " float, " +
                COLUMN_MG + " float, " +
                COLUMN_ICC + " float" +
                ");";
        db.execSQL(create_progress_table_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if EXISTS "+ TABLE_NAME);
        onCreate(db);
    }
}
