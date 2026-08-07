package com.example.mobilesvc.Vistas;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mobilesvc.MainActivity;
import com.example.mobilesvc.Api.ApiClient;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    private MutableLiveData<String> mToastMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> loginExitoso = new MutableLiveData<>();

    public LiveData<Boolean> getLoginExitoso() {
        return loginExitoso;
    }

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getToastMessage() {
        if (mToastMessage == null) {
            mToastMessage = new MutableLiveData<>();
        }
        return mToastMessage;
    }

    public void iniciarSesion(String nombre, String pass) {
        if (nombre.isBlank() || pass.isBlank()) {
            mToastMessage.postValue("Complete todos los campos");
            return;
        }
        loginExitoso.setValue(false);
        //ApiClient.MiServicio servicio = ApiClient.getServicio();

        ApiClient.getServicio().iniciarSesion(nombre, pass).enqueue(new Callback<String>() {

            //call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String token = response.body();
                    ApiClient.guardarToken(getApplication(), token);
                    Log.d("LOG_LOGIN", token);
                    loginExitoso.postValue(true);
//                    Intent i = new Intent(getApplication(), MainActivity.class);
//                    i.addFlags(FLAG_ACTIVITY_NEW_TASK);
//                    getApplication().startActivity(i);
                } else {
                    Log.d("LOG_LOGIN_ERROR", "Código: " + response.code());
                    try {
                        Log.d("LOG_LOGIN_ERROR", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("LOGIN_FAILURE",t.getMessage());
                mToastMessage.postValue("Usuario o Contraseña incorrectos");
            }
        });
    }

        public void restablecerUsuario () {
            ApiClient.MiServicio servicio = ApiClient.getServicio();
            Call<Void> call = servicio.restablecerUsuario();

            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Log.d("LOG_LOGIN", "Usuario 3 Restablecido");
                        mToastMessage.postValue("Usuario Restablecido");
                    } else {
                        Log.d("LOG_LOGIN_ERROR", "Código: " + response.code());
                        try {
                            Log.d("LOG_LOGIN_ERROR", response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mToastMessage.postValue("Error al restablecer usuario");
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.d("LOG_LOGIN_FAILURE", t.getMessage());
                    mToastMessage.postValue("Fallo del CallBack en el LoginViewModel");
                }
            });
        }
    }

