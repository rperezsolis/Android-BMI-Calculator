package com.example.rafael_perez.mi_peso_ideal.Data.Presenter;

import android.content.Context;

import com.example.rafael_perez.mi_peso_ideal.Data.InterfaceDBQuery;
import com.example.rafael_perez.mi_peso_ideal.Data.Model.DBQueryModel;

import java.util.ArrayList;
import java.util.Date;

public class DBQueryPresenter implements InterfaceDBQuery.Presenter {
    private InterfaceDBQuery.View view;
    private InterfaceDBQuery.Model model;

    public DBQueryPresenter(InterfaceDBQuery.View view){
        this.view = view;
        model = new DBQueryModel(this);
    }

    @Override
    public void checkForUserName(Context context, String userName) {
        if (view!=null) model.checkForUserName(context, userName);
    }

    @Override
    public void saveResults(Context context, String name, String date, float IMC, float MG, float ICC) {
        if (view!=null) model.saveResults(context, name, date, IMC, MG, ICC);
    }

    @Override
    public void getData(Context context, ArrayList<Double> values_imc, ArrayList<Double> values_mg,
                        ArrayList<Double> values_icc, ArrayList<Date> values_dates) {
        if (view!=null) model.getData(context, values_imc, values_mg, values_icc, values_dates);
    }

    @Override
    public void setProgressData(ArrayList<Double> values_imc, ArrayList<Double> values_mg, ArrayList<Double> values_icc, ArrayList<Date> values_dates) {
        if (view!=null) view.setProgressData(values_imc, values_mg, values_icc, values_dates);
    }

    @Override
    public void goToMyProgress() {
        if (view!=null) view.goToMyProgress();
    }

    @Override
    public void noUser() {
        if (view!=null) view.noUser();
    }

    @Override
    public void saved() {
        if (view!=null) view.saved();
    }

    @Override
    public void notSaved() {
        if (view!=null) view.notSaved();
    }
}
