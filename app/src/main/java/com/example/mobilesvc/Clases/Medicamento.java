package com.example.mobilesvc.Clases;
import java.io.Serializable;
public class Medicamento implements Serializable{
    private int IDMedicamento, RecID, Cantidad;
    private String Nombre;
    private Double Intervalo, Dosis;
    public Medicamento() {}
    public Medicamento(int IDMedicamento, int RecID, int cantidad, String nombre, Double intervalo, Double dosis) {
        this.IDMedicamento = IDMedicamento;
        this.RecID = RecID;
        this.Nombre = nombre;
        this.Cantidad = cantidad;
        this.Intervalo = intervalo;
        this.Dosis = dosis;

    }
    public int getIDMedicamento() {
        return IDMedicamento;
    }
    public void setIDMedicamento(int IDMedicamento) {
        this.IDMedicamento = IDMedicamento;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }
    public int getCantidad() {
        return Cantidad;
    }
    public void setCantidad(int cantidad) {
        this.Cantidad = cantidad;
    }
    public Double getIntervalo() {
        return Intervalo;
    }
    public void setIntervalo(Double intervalo) {
        this.Intervalo = intervalo;
    }
    public Double getDosis() {
        return Dosis;
    }
    public void setDosis(Double dosis) {
        this.Dosis = dosis;
    }
    public int getRecID() {
        return RecID;
    }
    public void setRecID(int recID) {
        this.RecID = recID;
    }


    @Override
    public String toString() {
        return "Medicamento{" +
                "nombre='" + Nombre + '\'' +
                ", Cantidad='" + Cantidad + '\'' +
                ", Intervalo='" + Intervalo + '\'' +
                ", Dosis='" + Dosis + '\'' +
                '}';
    }
}

