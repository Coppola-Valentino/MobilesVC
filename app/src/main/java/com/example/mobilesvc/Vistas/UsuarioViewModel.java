package com.example.mobilesvc.Vistas;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mobilesvc.Clases.Usuario;

public class UsuarioViewModel extends AndroidViewModel {

    private MutableLiveData<Usuario> usuarioMutable = new MutableLiveData<>();

    public UsuarioViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Usuario> getUsuarioMutable() {
        if (usuarioMutable == null){
            usuarioMutable = new MutableLiveData<>();
        }
        return usuarioMutable;
    }

    public void cargarUsuario(Bundle bundle) {
        Usuario bundleUsuario = bundle.getSerializable("usuario", Usuario.class);
        usuarioMutable.setValue(bundleUsuario);
    }

}
