package com.example.mobilesvc.Vistas;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mobilesvc.Api.AlarmReceiver;
import com.example.mobilesvc.Clases.Recordatorio;
import com.example.mobilesvc.Api.ApiClient;

import java.io.IOException;
import java.sql.Time;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecordatorioEditViewModel extends AndroidViewModel {
    private MutableLiveData<Recordatorio> mRecordatorio = new MutableLiveData<>();
    private MutableLiveData<String> mToastMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> mDatosCambiados = new MutableLiveData<>();

    public RecordatorioEditViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getToastMessage() {
        if (mToastMessage==null) {
            mToastMessage = new MutableLiveData<>();
        }
        return mToastMessage;
    }

    public LiveData<Recordatorio> getRecordatorio() {
        if (mRecordatorio==null) {
            mRecordatorio = new MutableLiveData<>();
        }
        return mRecordatorio;
    }
    public LiveData<Boolean> getDatosCambiados() {
        if (mDatosCambiados==null) {
            mDatosCambiados = new MutableLiveData<>();
        }
        return mDatosCambiados;
    }
    public void cargarRecordatorio(Bundle bundle) {
        if (bundle != null && bundle.containsKey("recordatorio")) {
            mRecordatorio.setValue((Recordatorio) bundle.getSerializable("recordatorio"));
        }
    }
    public void cambiarDatos(String intervalo, int cantidad, int estado) {
        Recordatorio current = mRecordatorio.getValue();
        if (current == null) return;

        if (intervalo == null || cantidad <= 0) {
            mToastMessage.postValue("Todos los campos son obligatorios");
            return;
        }
        current.setIntervalo(intervalo);
        current.setCantidad(cantidad);
        current.setEstado(estado);

        ApiClient.MiServicio servicio = ApiClient.getServicio();
        String token = ApiClient.obtenerToken(getApplication());

        Call<Recordatorio> call = servicio.editarRecordatorio(token, current);

        call.enqueue(new Callback<Recordatorio>() {
            @Override
            public void onResponse(Call<Recordatorio> call, Response<Recordatorio> response) {
                if (response.isSuccessful()) {
                    mToastMessage.postValue("Actualizado correctamente");
                    mDatosCambiados.postValue(true);
                    if(current.getEstado() == 0) {
                        cancelAlarm(getApplication(), current);
                    } else {
                        startAlarm(getApplication(), current);
                    }
                } else {
                    mToastMessage.postValue("Error en el servidor");
                }
            }
            @SuppressLint("ScheduleExactAlarm")
            public void cancelAlarm(Context context, Recordatorio rec) {
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(context, AlarmReceiver.class);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        context, rec.getIDRec(), intent,
                        PendingIntent.FLAG_NO_CREATE | PendingIntent.FLAG_IMMUTABLE);

                if (pendingIntent != null && alarmManager != null) {
                    alarmManager.cancel(pendingIntent);
                    pendingIntent.cancel();
                }
            }
            @SuppressLint("ScheduleExactAlarm")
            private void startAlarm(Context context, Recordatorio rec) {
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
                Intent intent = new Intent(context, AlarmReceiver.class);
                intent.putExtra("recordatorio", rec);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        context, rec.getIDRec(), intent,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

                long triggerAt = System.currentTimeMillis() + rec.getIntervaloTime();

                if (alarmManager != null) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAt, pendingIntent);
                }
            }
            @Override
            public void onFailure(Call<Recordatorio> call, Throwable t) {
                mToastMessage.postValue("Error de conexión");
            }
        });
    }
}