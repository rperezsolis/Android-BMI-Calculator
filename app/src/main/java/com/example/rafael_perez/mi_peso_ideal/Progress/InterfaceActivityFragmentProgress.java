package com.example.rafael_perez.mi_peso_ideal.Progress;

import java.util.ArrayList;
import java.util.Date;

public class InterfaceActivityFragmentProgress {

    public interface SetDataCallback{
        void setData(ArrayList<Double> values_imc, ArrayList<Double> values_mg, ArrayList<Double> values_icc, ArrayList<Date> values_dates);
    }
}
