package com.example.mobilesvc.Vistas;

import android.app.Activity;
import android.app.Application;
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
    private MutableLiveData<Recordatorio> recordatorioMutable;

    public RecordatorioCrearViewModel(@NonNull Application application) {
        super(application);

    }

    public MutableLiveData<Recordatorio> getRecordatorioMutable() {
        if (recordatorioMutable == null) {
            recordatorioMutable = new MutableLiveData<>();
        }
        return recordatorioMutable;
    }
    public void crearNuevoRecordatorio(int cantidad,int intervalo){

        try {
            if (cantidad == 0 || intervalo == 0) {
                Toast.makeText(getApplication(), "Debe completar todos los campos", Toast.LENGTH_LONG).show();
            }else{
                Recordatorio nuevoRecordatorio = new Recordatorio();

                nuevoRecordatorio.setCantidad(cantidad);
                nuevoRecordatorio.setIntervalo(intervalo);

//                String recordatorioJson = new Gson().toJson(nuevoRecordatorio);
//                RequestBody recordatorioBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), recordatorioJson);
//                ApiClient.MiServicio servicio = ApiClient.getServicio();
//                String token = ApiClient.obtenerToken(getApplication());
//                Call<Recordatorio> call = servicio.CrearRecordatorio(token, recordatorioBody);

                  ApiClient.MiServicio servicio = ApiClient.getServicio();
                  String token = ApiClient.obtenerToken(getApplication());
                  Call<Recordatorio> call = servicio.CrearRecordatorio(token, nuevoRecordatorio);

                call.enqueue(new Callback<Recordatorio>() {
                    @Override
                    public void onResponse(Call<Recordatorio> call, Response<Recordatorio> response) {
                        if (response.isSuccessful()) {
                            recordatorioMutable.postValue(response.body());
                            Toast.makeText(getApplication(), "recordatorio creado", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(getApplication(), "Error al crear el recordatorio", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Recordatorio> call, Throwable t) {
                        Toast.makeText(getApplication(), "Error del servidor.", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }catch (NumberFormatException e){
            Toast.makeText(getApplication(), "Debe ingresar un numero valido", Toast.LENGTH_LONG).show();
        }
    }
}