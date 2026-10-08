package com.example.mobilesvc.Clases;
import java.io.Serializable;
public class Medicamento implements Serializable{
    private int IDMedicamento, RecID, Cantidad;
    private String Nombre, Marca;
    private Double Intervalo, Dosis;
    public Medicamento() {}
    public Medicamento(int IDMedicamento, int RecID, int cantidad, String nombre, String marca, Double intervalo, Double dosis) {
        this.IDMedicamento = IDMedicamento;
        this.RecID = RecID;
        this.Nombre = nombre;
        this.Cantidad = cantidad;
        this.Intervalo = intervalo;
        this.Dosis = dosis;
        this.Marca = marca;
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
    public String getMarca() {
        return Marca;
    }
    public void setMarca(String marca) {
        this.Marca = marca;
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

// cambiar tdo a Paciente y adulto responsable, admin y medico fusionados en funciones
    //ultimo cambio, menos distraccion (pero que mierda cambio?????)

    @Override
    public String toString() {
        return "Medicamento{" +
                "Nombre='" + Nombre + '\'' +
                ", Marca='" + Marca + '\'' +
                ", Cantidad='" + Cantidad + '\'' +
                ", Intervalo='" + Intervalo + '\'' +
                ", Dosis='" + Dosis + '\'' +
                '}';
    }
}

