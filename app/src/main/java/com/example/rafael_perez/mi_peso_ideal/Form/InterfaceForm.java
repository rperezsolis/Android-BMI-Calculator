package com.example.rafael_perez.mi_peso_ideal.Form;

import java.util.ArrayList;

public class InterfaceForm {

    public interface View {
        void results(ArrayList<float[]> results);
    }

    public interface Presenter {
        void calculate(float age, float weight, float height, float neck, float waist, float hip, int genre, String physical_activity);
        void results(ArrayList<float[]> results);
    }

    public interface Model {
        void calculate(float age, float weight, float height, float neck, float waist, float hip, int genre, String physical_activity);
    }
}
