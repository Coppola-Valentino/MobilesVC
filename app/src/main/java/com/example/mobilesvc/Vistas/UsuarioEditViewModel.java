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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioEditViewModel extends AndroidViewModel {
    private MutableLiveData<Usuario> mUsuario = new MutableLiveData<>();
    private MutableLiveData<String> mToastMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> mDatosCambiados = new MutableLiveData<>();

    public UsuarioEditViewModel(@NonNull Application application) {
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
    public void cambiarDatos(int edad, int telefono, String nombre, String password, String direccion, String genero, String email, String dni) {
        Usuario current = mUsuario.getValue();
        if (current == null) return;

        if (edad <= 0 || telefono <= 0 || nombre.isBlank() || password.isBlank() || direccion.isBlank() || genero.isBlank() || email.isBlank() || dni.isBlank()) {
            mToastMessage.postValue("Todos los campos son obligatorios");
            return;
        }
        current.setEdad(edad);
        current.setTelefono(telefono);
        current.setNombre(nombre);
        current.setPassword(password);
        current.setDireccion(direccion);
        current.setGenero(genero);
        current.setEmail(email);
        current.setDni(dni);

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