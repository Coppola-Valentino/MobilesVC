package com.example.mobilesvc.Api;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mobilesvc.Clases.Medicamento;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.example.mobilesvc.Clases.Usuario;
import com.example.mobilesvc.Clases.Receta;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public class ApiClient {

    public static MiServicio getServicio() {
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(MiServicio.class);
    }
    public interface MiServicio {
        @FormUrlEncoded
        @POST("api/Usuarios/login")
        Call<String> iniciarSesion(@Field("Usuario") String usuario, @Field("Password") String password);

        @GET("api/Usuario")
        Call<Usuario> getUsuario(@Header("Authorization") String token);
        @GET("/api/Usuarios")
        Call<List<Usuario>> getUsuarios(@Header("Authorization") String token);
        @GET("api/Recetas")
        Call<List<Receta>> getRecetas(@Header("Authorization") String token);

        @GET("api/Medicamento/Receta/{id}")
        Call<Medicamento> getMedicamentoPorReceta(@Header("Authorization") String token, @Path("id") int idReceta);
        @PUT("api/Usuarios/fix-id3")
        Call<Void> restablecerUsuario3();

        @PUT("api/Usuarios/editar")
        Call<Usuario> actualizarUsuario(@Header("Authorization") String token, @Body Usuario usuario);

        @FormUrlEncoded
        @PUT("api/Usuarios/changePassword")
        Call<Void> cambiarClave(@Header("Authorization") String token,
                                @Field("currentPassword") String actual,
                                @Field("newPassword") String nueva);

    }
    public static void guardarToken(Context context, String token) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", "Bearer "+token);
        editor.apply();
    }
    public static String obtenerToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        return sp.getString("token", null);
    }
    public static void eliminarCredenciales(Context context) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }
}
