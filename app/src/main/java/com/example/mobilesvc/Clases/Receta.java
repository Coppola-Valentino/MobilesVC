package com.example.mobilesvc.Clases;

import java.io.Serializable;
import java.util.Date;

public class Receta implements Serializable {
    private int IDReceta, PacID, MedID;
    private String Matricula;
    private Date Fecha;
    public Receta() {}
    public Receta(int IDReceta, int PacID, int MedID, Date fecha, String matricula) {
        this.IDReceta = IDReceta;
        this.PacID = PacID;
        this.MedID = MedID;
        this.Fecha = fecha;
        this.Matricula = matricula;

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
    //cambiar fecha final por otro campo mas util, como un nombre y/o guardar la fecha de inicio (del momento en el que se crea la receta)
    //eso es lo unico que falta
    public void setFecha(Date fecha) {
        this.Fecha = fecha;
    }
    public String getMatricula() {
        return Matricula;
    }
    public void setMatricula(String matricula) {
        this.Matricula = matricula;
    }


    @Override
    public String toString() {
        return "Receta{" +
                "Fecha='" + Fecha + '\'' +
                '}';
    }

}
