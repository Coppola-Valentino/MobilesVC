package com.example.mobilesvc.Vistas;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mobilesvc.Clases.Receta;
import com.example.mobilesvc.Api.ApiClient;

import java.io.IOException;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecetaEditViewModel extends AndroidViewModel {
    private MutableLiveData<Receta> mReceta = new MutableLiveData<>();
    private MutableLiveData<String> mToastMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> mDatosCambiados = new MutableLiveData<>();

    public RecetaEditViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getToastMessage() {
        if (mToastMessage==null) {
            mToastMessage = new MutableLiveData<>();
        }
        return mToastMessage;
    }

    public LiveData<Receta> getReceta() {
        if (mReceta==null) {
            mReceta = new MutableLiveData<>();
        }
        return mReceta;
    }
    public LiveData<Boolean> getDatosCambiados() {
        if (mDatosCambiados==null) {
            mDatosCambiados = new MutableLiveData<>();
        }
        return mDatosCambiados;
    }
    public void cargarReceta(Bundle bundle) {
        if (bundle != null && bundle.containsKey("receta")) {
            mReceta.setValue((Receta) bundle.getSerializable("receta"));
        }
    }
    public void cambiarDatos(Date Fecha) {
        Receta current = mReceta.getValue();
        if (current == null) return;

        if (Fecha == null) {
            mToastMessage.postValue("Todos los campos son obligatorios");
            return;
        }
        current.setFecha(Fecha);

        ApiClient.MiServicio servicio = ApiClient.getServicio();
        String token = ApiClient.obtenerToken(getApplication());

        Call<Receta> call = servicio.editarReceta(token, current);

        call.enqueue(new Callback<Receta>() {
            @Override
            public void onResponse(Call<Receta> call, Response<Receta> response) {
                if (response.isSuccessful()) {
                    mToastMessage.postValue("Actualizado correctamente");
                    mDatosCambiados.postValue(true);
                } else {
                    mToastMessage.postValue("Error en el servidor");
                }
            }
            @Override
            public void onFailure(Call<Receta> call, Throwable t) {
                mToastMessage.postValue("Error de conexión");
            }
        });
    }
}