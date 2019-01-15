package com.example.rafael_perez.mi_peso_ideal.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.rafael_perez.mi_peso_ideal.Models.ResultModel;

import java.util.ArrayList;

import static com.example.rafael_perez.mi_peso_ideal.Data.DataBaseContract.*;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "mi_peso_ideal.db";
    public static final int DATABASE_VERSION = 1;

    //constructor de la base de datos
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //crea la base de datos
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

    //modifica la base de datos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    //abrir la base de datos
    public void openDB(){
        this.getWritableDatabase();
    }

    //cerrar la base de datos
    public void closeDB(){
        this.close();
    }

    //insertar registros en la base de datos
    public void insertData(String user_name, String date, float IMC, float MG, float ICC){
        ContentValues values = new ContentValues();//esta clase ContentValues permite almacenar un conjunto de datos
        values.put(COLUMN_USER_NAME,user_name);
        values.put(COLUMN_DATE,date);
        values.put(COLUMN_IMC,IMC);
        values.put(COLUMN_MG,MG);
        values.put(COLUMN_ICC,ICC);//hasta aqui, ya se almacenaron los valores en el ContentValues valores
        this.getWritableDatabase().insert(TABLE_NAME,null,values);//insertamos los registros
    }

    //obtener registros de la base de datos
    public ArrayList<ResultModel> readData(String name){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<ResultModel> results_list= new ArrayList<>();
        String[] columns = {_ID, COLUMN_USER_NAME, COLUMN_DATE, COLUMN_IMC, COLUMN_MG, COLUMN_ICC};
        Cursor cursor = db.query(TABLE_NAME, columns,COLUMN_USER_NAME + " like '" + name + "'",null,null,null,null);
        if (cursor.moveToFirst()) {
            do {
                ResultModel result_model = new ResultModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getDouble(3), cursor.getDouble(4), cursor.getDouble(5));
                results_list.add(result_model);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return results_list;
    }
}
