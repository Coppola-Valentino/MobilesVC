package com.example.mobilesvc.Vistas;

import static android.view.View.INVISIBLE;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mobilesvc.Clases.Recordatorio;
import com.example.mobilesvc.Api.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecordatoriosViewModel extends AndroidViewModel {
    private MutableLiveData<List<Recordatorio>> recordatorioM = new MutableLiveData<>();
    private MutableLiveData<String> mToastMessage = new MutableLiveData<>();
    private MutableLiveData<String> mMessage = new MutableLiveData<>();
    private MutableLiveData<Integer> mMessageVisible = new MutableLiveData<>();
    private Context context;

    public RecordatoriosViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<List<Recordatorio>> getRecordatorios() {
        if (recordatorioM == null) {
            recordatorioM = new MutableLiveData<>();
        }
        return recordatorioM;
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

    public void cargarRecordatorios(int idUsuario) {
        String token = ApiClient.obtenerToken(context);
        ApiClient.MiServicio servicio = ApiClient.getServicio();

        Call<List<Recordatorio>> call = servicio.getRecordatoriosPorUsuario(token, idUsuario);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Recordatorio>> call, Response<List<Recordatorio>> response) {
                if (response.isSuccessful()) {
                    recordatorioM.postValue(response.body());
                    mMessageVisible.postValue(INVISIBLE);
                } else {
                    manejarErrorHttp(response.code());
                    mMessage.postValue("No se encontraron los recordatorios");
                }
            }
            @Override
            public void onFailure(Call<List<Recordatorio>> call, Throwable t) {
                Log.e("API_ERROR", "Fallo lista recordatorios: " + t.getMessage());
                mToastMessage.postValue("Sin conexion con el servidor");
                mMessage.postValue("No se encontraron los recordatorios");
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