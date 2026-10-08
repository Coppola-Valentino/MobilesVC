package com.example.mobilesvc.Clases;

import java.io.Serializable;
import java.util.Date;

public class Receta implements Serializable {
    private int IDReceta, PacID, MedID;
    private Date Fecha, FechaFin;
    public Receta() {}
    public Receta(int IDReceta, int PacID, int MedID, Date fecha, Date fechaFin) {
        this.IDReceta = IDReceta;
        this.PacID = PacID;
        this.MedID = MedID;
        this.Fecha = fecha;
        this.FechaFin = fechaFin;

    }
    public int getIDReceta() {
        return IDReceta;
    }
    public void setIDReceta(int IDReceta) {
        this.IDReceta = IDReceta;
    }
    public int getPacID() {
        return PacID;
    }
    public void setPacID(int PacID) {
        this.PacID = PacID;
    }
    public int getMedID() {
        return MedID;
    }
    public void setMedID(int MedID) {
        this.MedID = MedID;
    }
    public Date getFecha() { return Fecha; }
    public void setFecha(Date fecha) {
        this.Fecha = fecha;
    }
    public Date getFechaFin() { return FechaFin; }
    public void setFechaFin(Date fechaFin) {
        this.FechaFin = fechaFin;
    }


    @Override
    public String toString() {
        return "Receta{" +
                "Fecha ='" + Fecha + '\'' +
                ", Fecha de Vencimiento ='" + FechaFin + '\'' +
                '}';
    }

}
