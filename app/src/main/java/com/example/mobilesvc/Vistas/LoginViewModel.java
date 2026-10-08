package com.example.mobilesvc.Vistas;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.view.View.INVISIBLE;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mobilesvc.Api.AlarmReceiver;
import com.example.mobilesvc.Clases.Medicamento;
import com.example.mobilesvc.Clases.Recordatorio;
import com.example.mobilesvc.MainActivity;
import com.example.mobilesvc.Api.ApiClient;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    private MutableLiveData<String> mToastMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> loginExitoso = new MutableLiveData<>();
    private int i;
    private Context context;

    public LiveData<Boolean> getLoginExitoso() {
        return loginExitoso;
    }

    public LoginViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<String> getToastMessage() {
        if (mToastMessage == null) {
            mToastMessage = new MutableLiveData<>();
        }
        return mToastMessage;
    }

    public void iniciarSesion(String nombre, String pass) {
        if (nombre.isBlank() || pass.isBlank()) {
            mToastMessage.postValue("Complete todos los campos");
            return;
        }
        loginExitoso.setValue(false);
        ApiClient.MiServicio servicio = ApiClient.getServicio();
        ApiClient.getServicio().iniciarSesion(nombre, pass).enqueue(new Callback<String>() {

            //call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String token = response.body();
                    ApiClient.guardarToken(getApplication(), token);
                    Log.d("LOG_LOGIN", token);
                    loginExitoso.postValue(true);
//                    Intent i = new Intent(getApplication(), MainActivity.class);
//                    i.addFlags(FLAG_ACTIVITY_NEW_TASK);
//                    getApplication().startActivity(i);
                    int userId = ApiClient.obtenerUsuarioId(context);
                    Call<List<Recordatorio>> call2 = servicio.getRecordatoriosActivosPorUsuario(token, userId);

                    call2.enqueue(new Callback<>() {
                        @Override
                        public void onResponse(Call<List<Recordatorio>> call, Response<List<Recordatorio>> response) {
                            if (response.isSuccessful()) {
                                i = response.body().toArray().length -1;
                                Recordatorio rec = response.body().get(i);
                                while (i >= 0) {
                                    startAlarm(context, rec);
                                    response.body().remove(i);
                                    i--;
                                    //funciona, pero el alarmReceiver hace que los componentes del cel se sobrecargen
                                    //y si lo dejo asi?
                                }
                            } else {
                                Log.e("API_ERROR", "Fallo lista recordatorios");
                            }
                        }
                        @Override
                        public void onFailure(Call<List<Recordatorio>> call, Throwable t) {
                            Log.e("API_ERROR", "Fallo lista recordatorios: " + t.getMessage());
                            mToastMessage.postValue("Sin conexion con el servidor");
                        }
                    });
                } else {
                    Log.d("LOG_LOGIN_ERROR", "Código: " + response.code());
                    try {
                        Log.d("LOG_LOGIN_ERROR", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @SuppressLint("ScheduleExactAlarm")
            private void startAlarm(Context context, Recordatorio rec) {
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
                Intent intent = new Intent(context, AlarmReceiver.class);
                intent.putExtra("recordatorio", rec);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        context, rec.getIDRec(), intent,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

                long triggerAt = System.currentTimeMillis() + rec.getIntervaloTime();

                if (alarmManager != null) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAt, pendingIntent);
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("LOGIN_FAILURE",t.getMessage());
                mToastMessage.postValue("Usuario o Contraseña incorrectos");
            }
        });
    }
}

