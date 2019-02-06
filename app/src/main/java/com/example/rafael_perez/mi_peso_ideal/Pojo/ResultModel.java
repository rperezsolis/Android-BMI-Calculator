package com.example.rafael_perez.mi_peso_ideal.Pojo;

public class ResultModel { private int Result_Number;
    private String Nombre, Fecha;
    private Double IMC, MG, ICC;

    public ResultModel(int Result_Number, String Nombre, String Fecha, Double IMC, Double MG, Double ICC) {
        this.Result_Number = Result_Number;
        this.Nombre        = Nombre;
        this.Fecha         = Fecha;
        this.IMC           = IMC;
        this.MG            = MG;
        this.ICC           = ICC;
    }

    public int getResult_Number() {
        return Result_Number;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getFecha() {
        return Fecha;
    }

    public Double getIMC() {
        return IMC;
    }

    public Double getMG() {
        return MG;
    }

    public Double getICC() {
        return ICC;
    }

    public void setResult_Number(int Result_Number) {
        this.Result_Number = Result_Number;
    }

    public void setNombre(String Nombre) {this.Nombre = Nombre; }

    public void setFecha(String Fecha) {this.Fecha = Fecha; }

    public void setIMC(Double IMC) {
        this.IMC = IMC;
    }

    public void setMG(Double MG) {
        this.MG = MG;
    }

    public void setICC(Double ICC) {
        this.ICC = ICC;
    }
}
