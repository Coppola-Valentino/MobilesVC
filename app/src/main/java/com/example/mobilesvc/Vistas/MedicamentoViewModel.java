package com.example.mobilesvc.Vistas;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mobilesvc.Clases.Medicamento;

public class MedicamentoViewModel extends AndroidViewModel {

    private MutableLiveData<Medicamento> medicamentoMutable = new MutableLiveData<>();

    public MedicamentoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Medicamento> getMedicamentoMutable() {
        if (medicamentoMutable == null) {
            medicamentoMutable = new MutableLiveData<>();
        }
        return medicamentoMutable;
    }

    public void cargarMedicamento(Bundle bundle) {
        Medicamento bundleRecordatorio = bundle.getSerializable("medicamento", Medicamento.class);
        medicamentoMutable.setValue(bundleRecordatorio);
    }
}