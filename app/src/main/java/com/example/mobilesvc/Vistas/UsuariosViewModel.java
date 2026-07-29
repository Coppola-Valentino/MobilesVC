package com.example.mobilesvc.Vistas;

import static android.view.View.INVISIBLE;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mobilesvc.Clases.Usuario;
import com.example.mobilesvc.Api.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuariosViewModel extends AndroidViewModel {
    private MutableLiveData<List<Usuario>> usuarioM;
    private MutableLiveData<String> mToastMessage;
    private MutableLiveData<String> mMessage;
    private MutableLiveData<Integer> mMessageVisible;
    private Context context;

    public UsuariosViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<List<Usuario>> getUsuarios() {
        if (usuarioM == null) {
            usuarioM = new MutableLiveData<>();
        }
        return usuarioM;
    }
    public LiveData<String> getToastMessage() {
        if (mToastMessage==null) {
            mToastMessage = new MutableLiveData<>();
        }
        return mToastMessage;
    }
    public LiveData<String> getMessage() {
        if (mMessage ==null) {
            mMessage = new MutableLiveData<>();
        }
        return mMessage;
    }
    public LiveData<Integer> getMessageVisible() {
        if (mMessageVisible ==null) {
            mMessageVisible = new MutableLiveData<>();
        }
        return mMessageVisible;
    }

    public void cargarUsuarios() {
        String token = ApiClient.obtenerToken(context);
        ApiClient.MiServicio servicio = ApiClient.getServicio();

        Call<List<Usuario>> call = servicio.getUsuarios(token);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful()) {
                    usuarioM.postValue(response.body());
                    mMessageVisible.postValue(INVISIBLE);
                } else {
                    manejarErrorHttp(response.code());
                    mMessage.postValue("No se encontraron los usuarios");
                }
            }
            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Log.e("API_ERROR", "Fallo lista usuarios: " + t.getMessage());
                mToastMessage.postValue("Sin conexion con el servidor");
                mMessage.postValue("No se encontraron los usuarios");
            }
        });
    }

    private void manejarErrorHttp(int codigo) {
        switch (codigo) {
            case 401: Log.e("API_ERROR", "No autorizado: Token inválido"); break;
            case 403: Log.e("API_ERROR", "Prohibido: Sin permisos suficientes"); break;
            case 404: Log.e("API_ERROR", "No encontrado"); break;
            case 500: Log.e("API_ERROR", "Error del servidor"); break;
            default:  Log.e("API_ERROR", "Error desconocido: Código " + codigo); break;
        }
    }
}