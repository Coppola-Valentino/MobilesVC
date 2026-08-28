package com.example.mobilesvc.Vistas;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mobilesvc.Api.AlarmReceiver;
import com.google.gson.Gson;
import com.example.mobilesvc.R;
import com.example.mobilesvc.Clases.Recordatorio;
import com.example.mobilesvc.Api.ApiClient;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecordatorioCrearViewModel extends AndroidViewModel {
    private MutableLiveData<Recordatorio> recordatorioMutable = new MutableLiveData<>();
    private Context context;

    public RecordatorioCrearViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();

    }

    public MutableLiveData<Recordatorio> getRecordatorioMutable() {
        if (recordatorioMutable == null) {
            recordatorioMutable = new MutableLiveData<>();
        }
        return recordatorioMutable;
    }
    public void crearNuevoRecordatorio(Recordatorio rec){

        try {
            if (rec.getCantidad() == 0 || rec.getIntervalo() == null) {
                Toast.makeText(getApplication(), "Debe completar todos los campos", Toast.LENGTH_LONG).show();
            }else{
//                Recordatorio nuevoRecordatorio = new Recordatorio();
//
//                nuevoRecordatorio.setCantidad(cantidad);
//                nuevoRecordatorio.setIntervalo(intervalo);
//                nuevoRecordatorio.setUserID(ApiClient.obtenerUsuarioId(context));

//                String recordatorioJson = new Gson().toJson(nuevoRecordatorio);
//                RequestBody recordatorioBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), recordatorioJson);
//                ApiClient.MiServicio servicio = ApiClient.getServicio();
//                String token = ApiClient.obtenerToken(getApplication());
//                Call<Recordatorio> call = servicio.CrearRecordatorio(token, recordatorioBody);

                  ApiClient.MiServicio servicio = ApiClient.getServicio();
                  String token = ApiClient.obtenerToken(getApplication());
                  Call<Recordatorio> call = servicio.CrearRecordatorio(rec);


                call.enqueue(new Callback<Recordatorio>() {
                    @Override
                    public void onResponse(Call<Recordatorio> call, Response<Recordatorio> response) {
                        if (response.isSuccessful()) {
                            recordatorioMutable.postValue(response.body());
                            Recordatorio rec = response.body();
                            startAlarm(getApplication(), rec);
                            Toast.makeText(getApplication(), "recordatorio creado", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(getApplication(), "Error al crear el recordatorio", Toast.LENGTH_LONG).show();
                        }
                    }
                    @SuppressLint("ScheduleExactAlarm")
                    private void startAlarm(Context context, Recordatorio rec) {
                        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
                        Intent intent = new Intent(context, AlarmReceiver.class);
                        intent.putExtra("Recordatorio", rec);
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
                        Log.e("LOGIN_FAILURE", t.getMessage(), t);
                        Toast.makeText(getApplication(), "Error del servidor.", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }catch (NumberFormatException e){
            Toast.makeText(getApplication(), "Debe ingresar un numero valido", Toast.LENGTH_LONG).show();
        }
    }
}