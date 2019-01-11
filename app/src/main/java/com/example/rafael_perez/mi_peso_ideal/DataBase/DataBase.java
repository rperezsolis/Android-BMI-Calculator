package com.example.rafael_perez.mi_peso_ideal.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.rafael_perez.mi_peso_ideal.Models.ResultModel;

import java.util.ArrayList;

public class DataBase extends SQLiteOpenHelper {

    //constructor de la base de datos
    public DataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //crea la base de datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCrearTablaResultados = "create table " + DataBaseContract.TABLE_NAME + "(" +
                DataBaseContract.CAMPO_RESULT_NUMBER + " integer primary key autoincrement, " +
                DataBaseContract.CAMPO_NOMBRE + " string," +
                DataBaseContract.CAMPO_FECHA + " string, " +
                DataBaseContract.CAMPO_IMC + " float, " +
                DataBaseContract.CAMPO_MG + " float, " +
                DataBaseContract.CAMPO_ICE + " float" +
                ");";
        db.execSQL(queryCrearTablaResultados);
    }

    //modifica la base de datos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if EXISTS "+ DataBaseContract.TABLE_NAME);
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
    public void insertData(String Nombre, String Fecha, float IMC, float MG, float ICE){
        ContentValues valores = new ContentValues();//esta clase ContentValues permite almacenar un conjunto de datos
        valores.put(DataBaseContract.CAMPO_NOMBRE,Nombre);
        valores.put(DataBaseContract.CAMPO_FECHA,Fecha);
        valores.put(DataBaseContract.CAMPO_IMC,IMC);
        valores.put(DataBaseContract.CAMPO_MG,MG);
        valores.put(DataBaseContract.CAMPO_ICE,ICE);//hasta aqui, ya se almacenaron los valores en el ContentValues valores
        this.getWritableDatabase().insert(DataBaseContract.TABLE_NAME,null,valores);//insertamos los registros guardados en
        // valores en la tabla
    }

    //obtener registros de la base de datos
    public ArrayList<ResultModel> leerRegistros(String Nombre){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<ResultModel> listaRegistros= new ArrayList<>();
        String[] valoresRegistros = {"ID","NOMBRE","Fecha","IMC","MG","ICE"};
        Cursor cursor = db.query(DataBaseContract.TABLE_NAME, valoresRegistros,"NOMBRE like '"+Nombre+"'",null,null,null,null);
        if (cursor.moveToFirst()) {
            do {
                ResultModel registros = new ResultModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getDouble(3), cursor.getDouble(4), cursor.getDouble(5));
                listaRegistros.add(registros);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return listaRegistros;
    }
}
