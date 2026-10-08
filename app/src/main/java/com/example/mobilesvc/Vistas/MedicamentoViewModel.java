package com.example.mobilesvc.Vistas;

import static android.view.View.INVISIBLE;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mobilesvc.Api.ApiClient;
import com.example.mobilesvc.Clases.Medicamento;
import com.example.mobilesvc.Clases.Receta;
import com.example.mobilesvc.Clases.Recordatorio;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicamentoViewModel extends AndroidViewModel {
    private Context context;

    private MutableLiveData<Medicamento> medicamentoMutable = new MutableLiveData<>();
    private MutableLiveData<Receta> recetaMutable = new MutableLiveData<>();

    public MedicamentoViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Medicamento> getMedicamentoMutable() {
        if (medicamentoMutable == null) {
            medicamentoMutable = new MutableLiveData<>();
        }
        return medicamentoMutable;
    }

    public LiveData<Receta> getRecetaMutable() {
        if (recetaMutable == null) {
            recetaMutable = new MutableLiveData<>();
        }
        return recetaMutable;
    }

    public void cargarMedicamento(Bundle bundle) {
        if (bundle.getSerializable("medicamento", Medicamento.class) == null){
            ApiClient.MiServicio servicio = ApiClient.getServicio();
            String token = ApiClient.obtenerToken(context);
            int IdMedicamento = bundle.getInt("IdMedicamento");
            Call<Medicamento> call = servicio.getMedicamentoPorId(token, IdMedicamento);

            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<Medicamento> call, Response<Medicamento> response) {
                    if (response.isSuccessful()) {
                        medicamentoMutable.setValue(response.body());
//                        Call<Receta> call2 = servicio.getRecetaPorId(token, response.body().getRecID());
//
//                        call2.enqueue(new Callback<>() {
//                            @Override
//                            public void onResponse(Call<Receta> call, Response<Receta> response) {
//                                if (response.isSuccessful()) {
//                                    recetaMutable.setValue(response.body());
//                                }
//                            }
//                            @Override
//                            public void onFailure(Call<Receta> call, Throwable t) {
//                                Log.e("API_ERROR", "Fallo en Medicamento (receta): " + t.getMessage());
//                            }
//                        });
                    }
                }
                @Override
                public void onFailure(Call<Medicamento> call, Throwable t) {
                    Log.e("API_ERROR", "Fallo en Medicamento: " + t.getMessage());
                }
            });
        } else {
            Medicamento bundleMedicamento = bundle.getSerializable("medicamento", Medicamento.class);
            medicamentoMutable.setValue(bundleMedicamento);
        }
    }
}