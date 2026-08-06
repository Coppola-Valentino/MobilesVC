package com.example.mobilesvc.Api;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mobilesvc.Clases.Medicamento;
import com.example.mobilesvc.Clases.Recordatorio;
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
//                .baseUrl("http://10.0.2.2/mobiles/")
                .baseUrl("http://10.0.2.2:3000/")
//                .baseUrl("https://mobil_1.alwaysdata.net/")
//                .baseUrl("mysql://avnadmin:AVNS_taltKqMHud0XJn74sSk@mobiles-mobiles.b.aivencloud.com:22615/defaultdb?ssl-mode=REQUIRED")
//                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(MiServicio.class);
    }
    public interface MiServicio {
        @FormUrlEncoded
        @POST("api/Usuarios/login")
        Call<String> iniciarSesion(@Field("Nombre") String Nombre, @Field("Password") String Password);

//        @POST("api/Usuarios/perfil")
//        Call<Usuario> CrearUsuario(@Header("Authorization") String token, @Body Usuario usuarioBody);

//        @FormUrlEncoded
//        @POST("api/Usuarios/login")
//        Call<String> iniciarSesion(@Field("Usuario") String usuario, @Field("Password") String password);

        @GET("api/Usuario")
        Call<Usuario> getUsuario(@Header("Authorization") String token);
        @GET("api/Usuarios")
        Call<List<Usuario>> getUsuarios(@Header("Authorization") String token);
        @GET("api/Recetas")
        Call<List<Receta>> getRecetas(@Header("Authorization") String token);
        @GET("api/Recordatorios")
        Call<List<Recordatorio>> getRecordatorios(@Header("Authorization") String token);

        @GET("api/Medicamento/Receta/{id}")
        Call<List<Medicamento>> getMedicamentosPorReceta(@Header("Authorization") String token, @Path("id") int idReceta);
        @GET("api/Receta/Usuario/{id}")
        Call<List<Receta>> getRecetasPorUsuario(@Header("Authorization") String token, @Path("id") int idUsuario);
        @GET("api/Recordatorio/Usuario/{id}")
        Call<List<Recordatorio>> getRecordatoriosPorUsuario(@Header("Authorization") String token, @Path("id") int idUsuario);
        @PUT("api/Usuarios/fix-id")
        Call<Void> restablecerUsuario();

        @PUT("api/Usuarios/editar")
        Call<Usuario> editarUsuario(@Header("Authorization") String token, @Body Usuario usuario);
        @PUT("api/Medicamentos/editar")
        Call<Medicamento> editarMedicamento(@Header("Authorization") String token, @Body Medicamento medicamento);
        @PUT("api/Receta/editar")
        Call<Receta> editarReceta(@Header("Authorization") String token, @Body Receta receta);
        @PUT("api/Recordatorio/editar")
        Call<Recordatorio> editarRecordatorio(@Header("Authorization") String token, @Body Recordatorio recordatorio);
        @FormUrlEncoded
        @PUT("api/Usuarios/changePassword")
        Call<Void> cambiarClave(@Header("Authorization") String token,
                                @Field("currentPassword") String actual,
                                @Field("newPassword") String nueva);
        @POST("api/Usuarios/crear")
        Call<Usuario> CrearUsuario(@Body Usuario usuario);
        @POST("api/Recordatorio/crear")
        Call<Recordatorio> CrearRecordatorio(@Body Recordatorio recordatorio);
        @POST("api/Receta/crear")
        Call<Receta> CrearReceta(@Body Receta receta);
        @POST("api/Medicamento/crear")
        Call<Medicamento> CrearMedicamento(@Body Medicamento medicamento);
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
