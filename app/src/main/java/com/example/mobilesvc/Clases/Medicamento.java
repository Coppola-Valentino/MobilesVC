package com.example.mobilesvc.Clases;
import java.io.Serializable;
public class Medicamento implements Serializable{
    private int IDMedicamento, RecID, cantidad;
    private String nombre;
    private Double intervalo, dosis;
    public Medicamento() {}
    public Medicamento(int IDMedicamento, int RecID, int cantidad, String nombre, Double intervalo, Double dosis) {
        this.IDMedicamento = IDMedicamento;
        this.RecID = RecID;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.intervalo = intervalo;
        this.dosis = dosis;

    }
    public int getIDMedicamento() {
        return IDMedicamento;
    }
    public void setIDMedicamento(int IDMedicamento) {
        this.IDMedicamento = IDMedicamento;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public Double getIntervalo() {
        return intervalo;
    }
    public void setIntervalo(Double intervalo) {
        this.intervalo = intervalo;
    }
    public Double getDosis() {
        return dosis;
    }
    public void setDosis(Double dosis) {
        this.dosis = dosis;
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
                "nombre='" + nombre + '\'' +
                ", Cantidad='" + cantidad + '\'' +
                ", Intervalo='" + intervalo + '\'' +
                ", Dosis='" + dosis + '\'' +
                '}';
    }
}

