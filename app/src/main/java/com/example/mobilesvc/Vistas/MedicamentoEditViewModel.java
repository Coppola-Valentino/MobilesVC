package com.example.mobilesvc.Vistas;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mobilesvc.Clases.Medicamento;
import com.example.mobilesvc.Api.ApiClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicamentoEditViewModel extends AndroidViewModel {
    private MutableLiveData<Medicamento> mMedicamento
    private MutableLiveData<String> mToastMessage;
    private MutableLiveData<Boolean> mDatosCambiados;

    public MedicamentoEditViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getToastMessage() {
        if (mToastMessage==null) {
            mToastMessage = new MutableLiveData<>();
        }
        return mToastMessage;
    }

    public LiveData<Medicamento> getMedicamento() {
        if (mMedicamento==null) {
            mMedicamento = new MutableLiveData<>();
        }
        return mMedicamento;
    }
    public LiveData<Boolean> getDatosCambiados() {
        if (mDatosCambiados==null) {
            mDatosCambiados = new MutableLiveData<>();
        }
        return mDatosCambiados;
    }
    public void cargarMedicamento(Bundle bundle) {
        if (bundle != null && bundle.containsKey("medicamento")) {
            mMedicamento.setValue((Medicamento) bundle.getSerializable("medicamento"));
        }
    }
    public void cambiarDatos(String nombre, int cantidad, double dosis, double intervalo) {
        Medicamento current = mMedicamento.getValue();
        if (current == null) return;

        if (nombre.isBlank() || cantidad <= 0 || dosis <= 0 || intervalo <= 0) {
            mToastMessage.postValue("Todos los campos son obligatorios");
            return;
        }
        current.setNombre(nombre);
        current.setCantidad(cantidad);
        current.setDosis(dosis);
        current.setIntervalo(intervalo);

        ApiClient.MiServicio servicio = ApiClient.getServicio();
        String token = ApiClient.obtenerToken(getApplication());

        Call<Medicamento> call = servicio.editarMedicamento(token, current);

        call.enqueue(new Callback<Medicamento>() {
            @Override
            public void onResponse(Call<Medicamento> call, Response<Medicamento> response) {
                if (response.isSuccessful()) {
                    mToastMessage.postValue("Actualizado correctamente");
                    mDatosCambiados.postValue(true);
                } else {
                    mToastMessage.postValue("Error en el servidor");
                }
            }
            @Override
            public void onFailure(Call<Medicamento> call, Throwable t) {
                mToastMessage.postValue("Error de conexión");
            }
        });
    }
}