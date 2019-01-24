package com.example.rafael_perez.mi_peso_ideal.Data;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;

public class InterfaceDBQuery {

    public interface View {
        void goToMyProgress();
        void setProgressData(ArrayList<Double> values_imc, ArrayList<Double> values_mg, ArrayList<Double> values_icc,
                             ArrayList<Date> values_dates);
        void noUser();
        void saved();
        void notSaved();
    }

    public interface Presenter {
        void checkForUserName(Context context, String userName);
        void saveResults(Context context, String name, String date, float IMC, float MG, float ICC);
        void getData(Context context, ArrayList<Double> values_imc, ArrayList<Double> values_mg,
                     ArrayList<Double> values_icc, ArrayList<Date> values_dates);
        void setProgressData(ArrayList<Double> values_imc, ArrayList<Double> values_mg, ArrayList<Double> values_icc,
                             ArrayList<Date> values_dates);
        void goToMyProgress();
        void noUser();
        void saved();
        void notSaved();
    }

    public interface Model {
        void checkForUserName(Context context, String userName);
        void saveResults(Context context, String name, String date, float IMC, float MG, float ICC);
        void getData(Context context, ArrayList<Double> values_imc, ArrayList<Double> values_mg,
                     ArrayList<Double> values_icc, ArrayList<Date> values_dates);
    }
}
