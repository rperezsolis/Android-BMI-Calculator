package com.example.rafael_perez.mi_peso_ideal.Results;

public class InterfaceResults {

    public interface View {
        void setColor(String[] colors);
    }

    public interface Presenter {
        void setLevel(float genre, float IMC, float MG, float ICC);
        void setColor(String[] colors);
    }

    public interface Model {
        void setLevel(float genre, float IMC, float MG, float ICC);
    }
}
