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
import com.example.mobilesvc.Clases.Usuario;
import com.example.mobilesvc.Api.ApiClient;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioCrearViewModel extends AndroidViewModel {
    private MutableLiveData<Usuario> usuarioMutable;

    public UsuarioCrearViewModel(@NonNull Application application) {
        super(application);

    }

    public MutableLiveData<Usuario> getUsuarioMutable() {
        if (usuarioMutable == null) {
            usuarioMutable = new MutableLiveData<>();
        }
        return usuarioMutable;
    }
    public void crearNuevoUsuario(String nombre,String password,String direccion,String dni,String email,
                                  String genero,int telefono,int edad){

        try {
            if (nombre.isBlank() || password.isEmpty() || direccion.isEmpty() || dni.isEmpty() ||
                    email.isEmpty() || genero.isEmpty() || telefono == 0 || edad == 0) {
                Toast.makeText(getApplication(), "Debe completar todos los campos", Toast.LENGTH_LONG).show();
            }else{
                Usuario nuevoUsuario = new Usuario();

                nuevoUsuario.setNombre(nombre);
                nuevoUsuario.setPassword(password);
                nuevoUsuario.setDni(dni);
                nuevoUsuario.setDireccion(direccion);
                nuevoUsuario.setGenero(genero);
                nuevoUsuario.setEdad(edad);
                nuevoUsuario.setEmail(email);
                nuevoUsuario.setTelefono(telefono);

//                String usuarioJson = new Gson().toJson(nuevoUsuario);
//                RequestBody usuarioBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), usuarioJson);
//                ApiClient.MiServicio servicio = ApiClient.getServicio();
//                String token = ApiClient.obtenerToken(getApplication());
//                Call<Usuario> call = servicio.CrearUsuario(token, usuarioBody);

                  ApiClient.MiServicio servicio = ApiClient.getServicio();
                  String token = ApiClient.obtenerToken(getApplication());
                  Call<Usuario> call = servicio.CrearUsuario(nuevoUsuario);

                call.enqueue(new Callback<Usuario>() {
                     @Override
                     public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                         if (response.isSuccessful()) {
                             usuarioMutable.postValue(response.body());
                             Toast.makeText(getApplication(), "usuario creado", Toast.LENGTH_LONG).show();
                         }else {
                             try {
                                 Log.e("PHP_ERROR", response.errorBody().string());
                             } catch (IOException e) {
                                 throw new RuntimeException(e);
                             }
                             Toast.makeText(getApplication(), "Error al crear el usuario", Toast.LENGTH_LONG).show();
                             //se atasca en este error al intentar crear un usuario, ta mas cerca que antes
                             //hacer lo de la database, va a tardar decadas en hacer funcionar
                         }
                     }

                     @Override
                     public void onFailure(Call<Usuario> call, Throwable t) {
                         Log.e("REGISTRATION_FAILURE", "Error: ", t);
                         Toast.makeText(getApplication(), "Failure: " + t.getMessage(), Toast.LENGTH_LONG).show();
                         Toast.makeText(getApplication(), "Error del servidor.", Toast.LENGTH_LONG).show();
                     }
                 });
            }
        }catch (NumberFormatException e){
            Toast.makeText(getApplication(), "Debe ingresar un numero valido", Toast.LENGTH_LONG).show();
        }
    }
}