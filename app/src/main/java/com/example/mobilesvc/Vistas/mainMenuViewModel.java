package com.example.mobilesvc.Vistas;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mobilesvc.Clases.Usuario;
import com.example.mobilesvc.Api.ApiClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class mainMenuViewModel extends AndroidViewModel {
    MutableLiveData<Usuario> mUsuario = new MutableLiveData<>();
    private MutableLiveData<String> mToastMessage = new MutableLiveData<>();


    public mainMenuViewModel(@NonNull Application application) {
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
        if(token == null) { return; }

        Call<Usuario> call = servicio.getUsuario(token);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful() && response.body() != null){
                    Log.d("LOG_PERFIL", "Propietario obtenido");
                    mUsuario.postValue(response.body());
                }else{
                    Log.d("LOG_PERFIL_ERROR","Código: " + response.code());
                    try {
                        Log.d("LOG_PERFIL_ERROR", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mToastMessage.postValue("Error al obtener el Propietario del PerfilViewModel");
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.d("LOG_PERFIL_FAILURE", t.getMessage());
                mToastMessage.postValue("Fallo del CallBack en el mainMenuViewModel");
            }
        });
    }
}