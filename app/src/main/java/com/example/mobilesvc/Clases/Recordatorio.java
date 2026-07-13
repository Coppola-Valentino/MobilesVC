package com.example.mobilesvc.Clases;

import java.io.Serializable;

public class Recordatorio implements Serializable {
    private int IDRec, UserID, MedicamentoID, cantidad, intervalo;
    public Recordatorio() {}
    public Recordatorio(int IDRec, int UserID, int MedicamentoID, int cantidad, int intervalo) {
        this.IDRec = IDRec;
        this.UserID = UserID;
        this.MedicamentoID = MedicamentoID;
        this.cantidad = cantidad;
        this.intervalo = intervalo;

    }
    public int getIDRec() {
        return IDRec;
    }
    public void setIDRec(int IDRec) {
        this.IDRec = IDRec;
    }
    public int getUserID() {
        return UserID;
    }
    public void setUserID(int UserID) {
        this.UserID = UserID;
    }
    public int getMedicamentoID() {
        return MedicamentoID;
    }
    public void setMedicamentoID(int MedicamentoID) {
        this.MedicamentoID = MedicamentoID;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public int getIntervalo() {
        return intervalo;
    }
    public void setIntervalo(int intervalo) {
        this.intervalo = intervalo;
    }


    @Override
    public String toString() {
        return "Recordatorio{" +
                "cantidad='" + cantidad + '\'' +
                "intervalo='" + intervalo + '\'' +
                '}';
    }
}
