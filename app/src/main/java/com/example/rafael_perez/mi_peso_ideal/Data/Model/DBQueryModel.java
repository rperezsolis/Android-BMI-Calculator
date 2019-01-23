package com.example.rafael_perez.mi_peso_ideal.Data.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import com.example.rafael_perez.mi_peso_ideal.Data.InterfaceDBQuery;
import com.example.rafael_perez.mi_peso_ideal.Pojo.ResultModel;
import com.example.rafael_perez.mi_peso_ideal.ProgressPresentationActivity;
import com.example.rafael_perez.mi_peso_ideal.ResultsActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.rafael_perez.mi_peso_ideal.Data.ProgressDBContract.COLUMN_DATE;
import static com.example.rafael_perez.mi_peso_ideal.Data.ProgressDBContract.COLUMN_ICC;
import static com.example.rafael_perez.mi_peso_ideal.Data.ProgressDBContract.COLUMN_IMC;
import static com.example.rafael_perez.mi_peso_ideal.Data.ProgressDBContract.COLUMN_MG;
import static com.example.rafael_perez.mi_peso_ideal.Data.ProgressDBContract.COLUMN_USER_NAME;
import static com.example.rafael_perez.mi_peso_ideal.Data.ProgressDBContract.CONTENT_URI;
import static com.example.rafael_perez.mi_peso_ideal.Data.ProgressDBContract._ID;

public class DBQueryModel implements InterfaceDBQuery.Model {
    private InterfaceDBQuery.Presenter presenter;

    public DBQueryModel(InterfaceDBQuery.Presenter presenter){
        this.presenter = presenter;
    }

    @Override
    public void checkForUserName(Context context, String userName) {
        String[] projection = {_ID};
        String selection = COLUMN_USER_NAME + "=?";
        String[] selectionArgs = new String[] {userName};
        Cursor cursor = context.getContentResolver().query(CONTENT_URI, projection, selection, selectionArgs, null);
        if (cursor!=null && cursor.getCount()==0 || TextUtils.isEmpty(userName)) {
            presenter.noUser();
            cursor.close();
        } else presenter.goToMyProgress();
    }

    @Override
    public void saveResults(Object sDataLock, Context context, String name, String date, float IMC, float MG, float ICC) {
        synchronized (ResultsActivity.sDataLock){
            ContentValues values = new ContentValues();  //ContentValues allows to store a data set
            values.put(COLUMN_USER_NAME, name);
            values.put(COLUMN_DATE, date);
            values.put(COLUMN_IMC, IMC);
            values.put(COLUMN_MG, MG);
            values.put(COLUMN_ICC, ICC);
            Uri uri = context.getContentResolver().insert(CONTENT_URI, values);
            if (uri == null) presenter.notSaved();
            else presenter.saved();
        }
    }

    @Override
    public void getData(Object sDataLock, Context context, ArrayList<Double> values_imc, ArrayList<Double> values_mg, ArrayList<Double> values_icc, ArrayList<Date> values_dates) {
        synchronized (ProgressPresentationActivity.sDataLock){
            ArrayList<ResultModel> results_list= new ArrayList<>();
            String[] projection = {_ID, COLUMN_USER_NAME, COLUMN_DATE, COLUMN_IMC, COLUMN_MG, COLUMN_ICC};
            Cursor cursor = context.getContentResolver().query(CONTENT_URI, projection, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    ResultModel result_model = new ResultModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                            cursor.getDouble(3), cursor.getDouble(4), cursor.getDouble(5));
                    results_list.add(result_model);
                } while (cursor.moveToNext());
                cursor.close();
            }
            for (int i = 0; i < results_list.size(); i++) {
                values_imc.add(results_list.get(i).getIMC());
                values_mg.add(results_list.get(i).getMG());
                values_icc.add(results_list.get(i).getICC());
                try {
                    DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = format.parse(results_list.get(i).getFecha());
                    values_dates.add(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        if (values_imc!=null && values_imc.size()>0) presenter.setGraphSeries(values_imc, values_mg, values_icc, values_dates);
    }
}
