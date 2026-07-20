package com.example.mobilesvc.Vistas;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mobilesvc.Clases.Recordatorio;

public class RecordatorioViewModel extends AndroidViewModel {

    private MutableLiveData<Recordatorio> recordatorioMutable = new MutableLiveData<>();

    public RecordatorioViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Recordatorio> getRecordatorioMutable() {
        if (recordatorioMutable == null) {
            recordatorioMutable = new MutableLiveData<>();
        }
        return recordatorioMutable;
    }

    public void cargarRecordatorio(Bundle bundle) {
        Recordatorio bundleRecordatorio = bundle.getSerializable("recordatorio", Recordatorio.class);
        recordatorioMutable.setValue(bundleRecordatorio);
    }
}