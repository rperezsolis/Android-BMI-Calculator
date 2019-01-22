package com.example.rafael_perez.mi_peso_ideal.Form.Model;

import com.example.rafael_perez.mi_peso_ideal.Form.InterfaceForm;

import java.util.ArrayList;

public class MainModel implements InterfaceForm.Model {
    private InterfaceForm.Presenter presenter;

    public MainModel(InterfaceForm.Presenter presenter){
        this.presenter = presenter;
    }

    @Override
    public void calculate(float age, float weight, float height, float neck, float waist, float hip, int genre, String physical_activity) {
        float GEB, ETA, AF = 0, GET, IMC, MG, ICC;
        if(genre ==1){
            GEB = (float) (66.5 + 13.75*weight + 5.08*height*100 - 4.68*age);
            ETA = (float) (0.10*GEB);
            MG = (float) (495/(1.0324-0.19077*(Math.log10(waist-neck)) + 0.15456*(Math.log10(height*100))) - 450);
        } else {
            GEB = (float) (665.1 + 9.56*weight + 1.85*height*100 - 4.68*age);
            ETA = (float) (0.10*GEB);
            MG = (float) (495/(1.29579-0.35004*(Math.log10(waist + hip - neck)) + 0.22100*(Math.log10(height*100))) - 450);
        }
        switch (physical_activity) {
            case "Nula":
                AF = (float) (0.1 * GEB);
                break;
            case "Leve":
                AF = (float) (0.2 * GEB);
                break;
            case "Moderada":
                AF = (float) (0.3 * GEB);
                break;
            case "Intensa":
                AF = (float) (0.4 * GEB);
                break;
        }
        GET = GEB + ETA + AF;
        IMC = weight/(height*height);
        ICC = waist/hip;
        float[] data = new float[]{genre, IMC, MG, ICC, GET};
        float[] calories = new float[]{GEB, ETA, AF};
        ArrayList<float[]> results = new ArrayList<>();
        results.add(data);
        results.add(calories);
        presenter.results(results);
    }
}
