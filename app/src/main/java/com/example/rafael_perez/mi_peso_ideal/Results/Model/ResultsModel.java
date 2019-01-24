package com.example.rafael_perez.mi_peso_ideal.Results.Model;

import com.example.rafael_perez.mi_peso_ideal.Results.InterfaceResults;

public class ResultsModel implements InterfaceResults.Model {
    private InterfaceResults.Presenter presenter;

    public ResultsModel(InterfaceResults.Presenter presenter){
        this.presenter = presenter;
    }

    @Override
    public void setLevel(float genre, float IMC, float MG, float ICC) {
        String imc_mg_color = null;
        String icc_color = null;

        String RED = "#FF1744";
        String ORANGE = "#FFA726";
        String YELLOW = "#FFFF00";
        String GREEN_BAD = "#C6FF00";
        String GREEN_GOOD = "#00C853";
        String BLUE = "#0091EA";

        if (IMC<=16) imc_mg_color = RED;//desnutricion 3
        if (16<IMC && IMC<=17) imc_mg_color = ORANGE;//desnutricion 2
        if (17<IMC && IMC<=18.5) imc_mg_color = YELLOW;//desnutricion 1
        if (18.5<IMC && IMC<=25) imc_mg_color = GREEN_GOOD;//normal
        if (25<IMC && genre ==1){
            if (25<IMC && MG<25) imc_mg_color = BLUE;//normal musculoso
            if (25<IMC && IMC<=30 && MG>=25) imc_mg_color = GREEN_BAD;//sobrepeso
            if (30<IMC && IMC<=35 && MG>=25) imc_mg_color = YELLOW;//obesidad 1
            if (35<IMC && IMC<=40 && MG>=25) imc_mg_color = ORANGE;//obesidad 2
            if (40<IMC && MG>=25) imc_mg_color = RED;//obesidad 3
        }
        if (25<IMC && genre ==2){
            if (25<IMC && MG<30) imc_mg_color = BLUE;//normal musculoso
            if (25<IMC && IMC<=30 & MG>=30) imc_mg_color = GREEN_BAD;//sobrepeso
            if (30<IMC && IMC<=35 & MG>=30) imc_mg_color = YELLOW;//obesidad 1
            if (35<IMC && IMC<=40 & MG>=30) imc_mg_color = ORANGE;//obesidad 2
            if (40<IMC && MG>=30) imc_mg_color = RED;//obesidad 3
        }
        if (genre ==1 && ICC <0.94) icc_color = GREEN_GOOD;//normal
        if (genre ==1 && ICC >=0.94) icc_color = RED;//obesidad
        if (genre ==2 && ICC <0.84) icc_color = GREEN_GOOD;//normal
        if (genre ==2 && ICC >=0.84) icc_color = RED;//obesidad

        String[] colors = new String[]{imc_mg_color, icc_color};
        presenter.setColor(colors);
    }
}
