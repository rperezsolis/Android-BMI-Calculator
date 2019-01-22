package com.example.rafael_perez.mi_peso_ideal.Form.Presenter;

import com.example.rafael_perez.mi_peso_ideal.Form.InterfaceForm;
import com.example.rafael_perez.mi_peso_ideal.Form.Model.MainModel;

import java.util.ArrayList;

public class MainPresenter implements InterfaceForm.Presenter {
    private InterfaceForm.View view;
    private InterfaceForm.Model model;

    public MainPresenter(InterfaceForm.View view){
        this.view = view;
        model = new MainModel(this);
    }

    @Override
    public void calculate(float age, float weight, float height, float neck, float waist, float hip, int genre, String physical_activity) {
        if (view!=null) model.calculate(age, weight, height, neck, waist, hip, genre, physical_activity);
    }

    @Override
    public void results(ArrayList<float[]> results) {
        if (view!=null) view.results(results);
    }
}
