package com.example.rafael_perez.mi_peso_ideal.Results.Presenter;

import com.example.rafael_perez.mi_peso_ideal.Results.InterfaceResults;
import com.example.rafael_perez.mi_peso_ideal.Results.Model.ResultsModel;

public class ResultsPresenter implements InterfaceResults.Presenter {
    private InterfaceResults.View view;
    private InterfaceResults.Model model;

    public ResultsPresenter(InterfaceResults.View view){
        this.view = view;
        model = new ResultsModel(this);
    }

    @Override
    public void setLevel(float genre, float IMC, float MG, float ICC) {
        if (view!=null) model.setLevel(genre, IMC, MG, ICC);
    }

    @Override
    public void setColor(String[] colors) {
        if (view!=null) view.setColor(colors);
    }
}
