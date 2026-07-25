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
import com.example.mobilesvc.Clases.Medicamento;
import com.example.mobilesvc.Api.ApiClient;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicamentoCrearViewModel extends AndroidViewModel {
    private MutableLiveData<Medicamento> medicamentoMutable;

    public MedicamentoCrearViewModel(@NonNull Application application) {
        super(application);

    }

    public MutableLiveData<Medicamento> getMedicamentoMutable() {
        if (medicamentoMutable == null) {
            medicamentoMutable = new MutableLiveData<>();
        }
        return medicamentoMutable;
    }
    public void crearNuevoMedicamento(String nombre,int cantidad,double intervalo,double dosis){

        try {
            if (nombre.isBlank() || cantidad == 0 || intervalo == 0 || dosis == 0) {
                Toast.makeText(getApplication(), "Debe completar todos los campos", Toast.LENGTH_LONG).show();
            }else{
                Medicamento nuevoMedicamento = new Medicamento();

                nuevoMedicamento.setNombre(nombre);
                nuevoMedicamento.setDosis(dosis);
                nuevoMedicamento.setCantidad(cantidad);
                nuevoMedicamento.setIntervalo(intervalo);

                String medicamentoJson = new Gson().toJson(nuevoMedicamento);
                RequestBody medicamentoBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), medicamentoJson);
                ApiClient.MiServicio servicio = ApiClient.getServicio();
                String token = ApiClient.obtenerToken(getApplication());
                Call<Medicamento> call = servicio.CrearMedicamento(token, medicamentoBody);
                call.enqueue(new Callback<Medicamento>() {
                    @Override
                    public void onResponse(Call<Medicamento> call, Response<Medicamento> response) {
                        if (response.isSuccessful()) {
                            medicamentoMutable.postValue(response.body());
                            Toast.makeText(getApplication(), "medicamento creado", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(getApplication(), "Error al crear el medicamento", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Medicamento> call, Throwable t) {
                        Toast.makeText(getApplication(), "Error del servidor.", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }catch (NumberFormatException e){
            Toast.makeText(getApplication(), "Debe ingresar un numero valido", Toast.LENGTH_LONG).show();
        }
    }
}