package com.example.rafael_perez.mi_peso_ideal.Data.Presenter;

import android.content.Context;

import com.example.rafael_perez.mi_peso_ideal.Data.InterfaceDBQuery;
import com.example.rafael_perez.mi_peso_ideal.Data.Model.DBQueryModel;

import java.util.ArrayList;
import java.util.Date;

public class DBQueryPresenter implements
        InterfaceDBQuery.InterfaceGoToMyProgress.Presenter,
        InterfaceDBQuery.InterfaceSaveResults.Presenter,
        InterfaceDBQuery.InterfaceGetAllDataQuery.Presenter {
    private InterfaceDBQuery.InterfaceGoToMyProgress.View my_progress_view;
    private InterfaceDBQuery.InterfaceSaveResults.View save_results_view;
    private InterfaceDBQuery.InterfaceGetAllDataQuery.View get_data_view;
    private InterfaceDBQuery.InterfaceGoToMyProgress.Model my_progress_model;
    private InterfaceDBQuery.InterfaceSaveResults.Model save_results_model;
    private InterfaceDBQuery.InterfaceGetAllDataQuery.Model get_data_model;

    public DBQueryPresenter(InterfaceDBQuery.InterfaceGoToMyProgress.View view){
        this.my_progress_view = view;
        my_progress_model = new DBQueryModel((InterfaceDBQuery.InterfaceGoToMyProgress.Presenter) this);
    }

    public DBQueryPresenter(InterfaceDBQuery.InterfaceSaveResults.View view){
        this.save_results_view = view;
        save_results_model = new DBQueryModel((InterfaceDBQuery.InterfaceSaveResults.Presenter) this);
    }

    public DBQueryPresenter(InterfaceDBQuery.InterfaceGetAllDataQuery.View view){
        this.get_data_view = view;
        get_data_model = new DBQueryModel((InterfaceDBQuery.InterfaceGetAllDataQuery.Presenter) this);
    }

    @Override
    public void checkForUserName(Context context, String userName) {
        if (my_progress_view!=null) my_progress_model.checkForUserName(context, userName);
    }

    @Override
    public void saveResults(Context context, String name, String date, float IMC, float MG, float ICC) {
        if (save_results_view!=null) save_results_model.saveResults(context, name, date, IMC, MG, ICC);
    }

    @Override
    public void getData(Context context, ArrayList<Double> values_imc, ArrayList<Double> values_mg,
                        ArrayList<Double> values_icc, ArrayList<Date> values_dates) {
        if (get_data_view!=null) get_data_model.getData(context, values_imc, values_mg, values_icc, values_dates);
    }

    @Override
    public void setProgressData(ArrayList<Double> values_imc, ArrayList<Double> values_mg, ArrayList<Double> values_icc, ArrayList<Date> values_dates) {
        if (get_data_view!=null) get_data_view.setProgressData(values_imc, values_mg, values_icc, values_dates);
    }

    @Override
    public void goToMyProgress() {
        if (my_progress_view!=null) my_progress_view.goToMyProgress();
    }

    @Override
    public void noUser() {
        if (my_progress_view!=null) my_progress_view.noUser();
    }

    @Override
    public void saved() {
        if (save_results_view!=null) save_results_view.saved();
    }

    @Override
    public void notSaved() {
        if (save_results_view!=null) save_results_view.notSaved();
    }
}
