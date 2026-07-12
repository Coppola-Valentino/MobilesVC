package com.example.mobilesvc.Clases;

import java.io.Serializable;
import java.util.Date;

public class Receta implements Serializable {
    private int IDReceta, PacID, MedID;
    private Date fecha;
    public Receta() {}
    public Receta(int IDReceta, int PacID, int MedID, Date fecha) {
        this.IDReceta = IDReceta;
        this.PacID = PacID;
        this.MedID = MedID;
        this.fecha = fecha;

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
    public Date getfecha() {
        return fecha;
    }
    public void setfecha(Date fecha) {
        this.fecha = fecha;
    }


    @Override
    public String toString() {
        return "Receta{" +
                "Fecha='" + fecha + '\'' +
                '}';
    }

}
