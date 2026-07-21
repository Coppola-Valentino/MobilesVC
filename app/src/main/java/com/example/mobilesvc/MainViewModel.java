package com.example.mobilesvc;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mobilesvc.Api.ApiClient;
import com.example.mobilesvc.Clases.Usuario;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends AndroidViewModel {
    MutableLiveData<Usuario> mUsuario;
    private MutableLiveData<String> mToastMessage;

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Usuario> getUsuario() {
        if (mUsuario==null) {
            mUsuario = new MutableLiveData<>();
        }
        return mUsuario;
    }

    public LiveData<String> getToastMessage() {
        if (mToastMessage==null) {
            mToastMessage = new MutableLiveData<>();
        }
        return mToastMessage;
    }

    public void cargarUsuario() {
        ApiClient.MiServicio servicio = ApiClient.getServicio();
        String token = ApiClient.obtenerToken(getApplication());

        if(token == null) {

            return;
        }

        Call<Usuario> call = servicio.getUsuario(token);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful() && response.body() != null){
                    Log.d("LOG_MAIN", "Usuario obtenido");
                    mUsuario.postValue(response.body());
                }else{
                    Log.d("LOG_MAIN_ERROR","Código: " + response.code());
                    try {
                        Log.d("LOG_MAIN_ERROR", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mToastMessage.postValue("Error al obtener el Usuario");
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.d("LOG_MAIN_FAILURE", t.getMessage());
                mToastMessage.postValue("el Call en el MainViewModel");
            }
        });
    }
}
