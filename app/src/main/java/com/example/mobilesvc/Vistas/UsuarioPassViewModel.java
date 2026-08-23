package com.example.mobilesvc.Vistas;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mobilesvc.Clases.Usuario;
import com.example.mobilesvc.Api.ApiClient;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioPassViewModel extends AndroidViewModel {
    private MutableLiveData<Usuario> mUsuario = new MutableLiveData<>();
    private MutableLiveData<String> mToastMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> mDatosCambiados = new MutableLiveData<>();

    public UsuarioPassViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getToastMessage() {
        if (mToastMessage==null) {
            mToastMessage = new MutableLiveData<>();
        }
        return mToastMessage;
    }

    public LiveData<Usuario> getUsuario() {
        if (mUsuario==null) {
            mUsuario = new MutableLiveData<>();
        }
        return mUsuario;
    }
    public LiveData<Boolean> getDatosCambiados() {
        if (mDatosCambiados==null) {
            mDatosCambiados = new MutableLiveData<>();
        }
        return mDatosCambiados;
    }
    public void cargarUsuario(Bundle bundle) {
        if (bundle != null && bundle.containsKey("usuario")) {
            mUsuario.setValue((Usuario) bundle.getSerializable("usuario"));
        }
    }
    public void cambiarPass(String actPass,String pass1,String pass2) {
        Usuario current = mUsuario.getValue();
        if (current == null) return;

        if (actPass.isBlank() || pass1.isBlank() || pass2.isBlank()) {
            mToastMessage.postValue("Llenar campos obligatorios");
            return;
        }
        if (!Objects.equals(pass1, pass2)){
            mToastMessage.postValue("La Contraseña no coincide");
            return;
        }
        current.setPassword(pass1);

        ApiClient.MiServicio servicio = ApiClient.getServicio();
        String token = ApiClient.obtenerToken(getApplication());

        Call<Usuario> call = servicio.editarUsuario(token, current);

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    mToastMessage.postValue("Actualizado correctamente");
                    mDatosCambiados.postValue(true);
                } else {
                    mToastMessage.postValue("Error en el servidor");
                }
            }
            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                mToastMessage.postValue("Error de conexión");
            }
        });
    }
}