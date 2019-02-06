package com.example.rafael_perez.mi_peso_ideal.Data;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;

public class InterfaceDBQuery {

    public static class InterfaceGoToMyProgress {

        public interface View {
            void goToMyProgress();
            void noUser();
        }

        public interface Presenter {
            void checkForUserName(Context context, String userName);
            void goToMyProgress();
            void noUser();
        }

        public interface Model {
            void checkForUserName(Context context, String userName);
        }
    }

    public static class InterfaceSaveResults {

        public interface View {
            void saved();
            void notSaved();
        }

        public interface Presenter {
            void saveResults(Context context, String name, String date, float IMC, float MG, float ICC);
            void saved();
            void notSaved();
        }

        public interface Model {
            void saveResults(Context context, String name, String date, float IMC, float MG, float ICC);
        }
    }

    public static class InterfaceGetAllDataQuery {

        public interface View {
            void setProgressData(ArrayList<Double> values_imc, ArrayList<Double> values_mg, ArrayList<Double> values_icc,
                                 ArrayList<Date> values_dates);
        }

        public interface Presenter {
            void getData(Context context, String userName, ArrayList<Double> values_imc, ArrayList<Double> values_mg,
                         ArrayList<Double> values_icc, ArrayList<Date> values_dates);
            void setProgressData(ArrayList<Double> values_imc, ArrayList<Double> values_mg, ArrayList<Double> values_icc,
                                 ArrayList<Date> values_dates);
        }

        public interface Model {
            void getData(Context context, String userName, ArrayList<Double> values_imc, ArrayList<Double> values_mg,
                         ArrayList<Double> values_icc, ArrayList<Date> values_dates);
        }
    }
}
