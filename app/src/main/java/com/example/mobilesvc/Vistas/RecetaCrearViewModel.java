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
import com.example.mobilesvc.Clases.Receta;
import com.example.mobilesvc.Api.ApiClient;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecetaCrearViewModel extends AndroidViewModel {
    private MutableLiveData<Receta> recetaMutable;

    public RecetaCrearViewModel(@NonNull Application application) {
        super(application);

    }

    public MutableLiveData<Receta> getRecetaMutable() {
        if (recetaMutable == null) {
            recetaMutable = new MutableLiveData<>();
        }
        return recetaMutable;
    }
    public void crearNuevoReceta(Date fecha){

        try {
            if (fecha == null) {
                Toast.makeText(getApplication(), "Debe completar todos los campos", Toast.LENGTH_LONG).show();
            }else{
                Receta nuevoReceta = new Receta();

                nuevoReceta.setFecha(fecha);

//                String recetaJson = new Gson().toJson(nuevoReceta);
//                RequestBody recetaBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), recetaJson);
//                ApiClient.MiServicio servicio = ApiClient.getServicio();
//                String token = ApiClient.obtenerToken(getApplication());
//                Call<Receta> call = servicio.CrearReceta(token, recetaBody);

                  ApiClient.MiServicio servicio = ApiClient.getServicio();
                  String token = ApiClient.obtenerToken(getApplication());
                  Call<Receta> call = servicio.CrearReceta(nuevoReceta);

                call.enqueue(new Callback<Receta>() {
                    @Override
                    public void onResponse(Call<Receta> call, Response<Receta> response) {
                        if (response.isSuccessful()) {
                            recetaMutable.postValue(response.body());
                            Toast.makeText(getApplication(), "receta creada", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(getApplication(), "Error al crear la receta", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Receta> call, Throwable t) {
                        Toast.makeText(getApplication(), "Error del servidor.", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }catch (NumberFormatException e){
            Toast.makeText(getApplication(), "Debe ingresar un numero valido", Toast.LENGTH_LONG).show();
        }
    }
}