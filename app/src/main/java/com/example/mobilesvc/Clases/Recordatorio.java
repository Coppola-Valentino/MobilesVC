package com.example.mobilesvc.Clases;

import java.io.Serializable;
import java.sql.Time;
import java.util.Calendar;

public class Recordatorio implements Serializable {
    private int IDRec, UserID, MedicamentoID, Cantidad, Estado;
    private Time Intervalo;
    public Recordatorio() {}
    public Recordatorio(int IDRec, int UserID, int MedicamentoID, int cantidad, Time intervalo, int estado) {
        this.IDRec = IDRec;
        this.UserID = UserID;
        this.MedicamentoID = MedicamentoID;
        this.Cantidad = cantidad;
        this.Intervalo = intervalo;
        this.Estado = estado;

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
        return Cantidad;
    }
    public void setCantidad(int cantidad) {
        this.Cantidad = cantidad;
    }
    public Time getIntervalo() {
        return Intervalo;
    }
    public void setIntervalo(Time intervalo) {
        this.Intervalo = intervalo;
    }
    public int getEstado() {
        return Estado;
    }
    public void setEstado(int estado) {
        this.Estado = estado;
    }

    public Long getIntervaloTime(){
        if(this.Intervalo == null){
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.Intervalo);

        int hours = cal.get(Calendar.HOUR_OF_DAY);
        int minutes = cal.get(Calendar.MINUTE);
        int seconds = cal.get(Calendar.SECOND);

        return ((hours * 3600L) + (minutes * 60L) + seconds) * 1000L;
    }

    //plan: cambiar intervalo de int a Time, primera notificacion seria currentTime + intervalo = hora de notificacion,
    // y luego horaDeNotificacion + intervalo = horaDeNotificacion2 hasta que nos quedemos sin repeticion
    //almacenar lot Time dentro de un array y activarlos todos al mismo tiempo quiza?
    @Override
    public String toString() {
        return "Recordatorio{" +
                "cantidad='" + Cantidad + '\'' +
                "intervalo='" + Intervalo + '\'' +
                "Estado='" + Estado + '\'' +
                '}';
    }
}
