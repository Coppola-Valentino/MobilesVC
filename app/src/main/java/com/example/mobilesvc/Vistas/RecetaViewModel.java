package com.example.mobilesvc.Vistas;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mobilesvc.Clases.Receta;

public class RecetaViewModel extends AndroidViewModel {

    private MutableLiveData<Receta> recetaMutable = new MutableLiveData<>();

    public RecetaViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Receta> getRecetaMutable() {
        if (recetaMutable == null) {
            recetaMutable = new MutableLiveData<>();
        }
        return recetaMutable;
    }

    public void cargarReceta(Bundle bundle) {
        Receta bundleRecordatorio = bundle.getSerializable("receta", Receta.class);
        recetaMutable.setValue(bundleRecordatorio);
    }
}