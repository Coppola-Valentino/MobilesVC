package com.example.mobilesvc.Clases;

import java.io.Serializable;
import java.util.Date;

public class Receta implements Serializable {
    private int IDReceta, PacID, MedID;
    private Date Fecha;
    public Receta() {}
    public Receta(int IDReceta, int PacID, int MedID, Date fecha) {
        this.IDReceta = IDReceta;
        this.PacID = PacID;
        this.MedID = MedID;
        this.Fecha = fecha;

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
    public Date getFecha() {
        return Fecha;
    }
    public void setFecha(Date fecha) {
        this.Fecha = fecha;
    }


    @Override
    public String toString() {
        return "Receta{" +
                "Fecha='" + Fecha + '\'' +
                '}';
    }

}
