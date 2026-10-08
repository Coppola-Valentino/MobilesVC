package com.example.mobilesvc.Api;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mobilesvc.Clases.Medicamento;
import com.example.mobilesvc.Clases.Recordatorio;
import com.example.mobilesvc.MainActivity;
import com.example.mobilesvc.R;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "MED_REMINDER_CHANNEL";
    private MutableLiveData<Medicamento> medicamentoMutable = new MutableLiveData<>();

    public MutableLiveData<Medicamento> getMedicamentoMutable() {
        if (medicamentoMutable == null) {
            medicamentoMutable = new MutableLiveData<>();
        }
        return medicamentoMutable;
    }

    @SuppressLint("ScheduleExactAlarm")
    @Override
    public void onReceive(Context context, Intent intent) {
        final PendingResult pendingResult = goAsync();
        Recordatorio rec = (Recordatorio) intent.getSerializableExtra("recordatorio");
        ApiClient.MiServicio Servicio = ApiClient.getServicio();
        int userId = ApiClient.obtenerUsuarioId(context);
        String token = ApiClient.obtenerToken(context);
        if (userId == rec.getUserID()) {
            Call<Medicamento> call = Servicio.getMedicamentoPorId(token, rec.getMedicamentoID());
            call.enqueue(new Callback<Medicamento>() {
                @Override
                public void onResponse(Call<Medicamento> call, Response<Medicamento> response) {
                    try {
                        if (response.isSuccessful() && response.body() != null) {
                            showNotification(context, rec, response.body());
//                    medicamentoMutable.postValue(response.body());
                        } else {
                            showNotification(context, rec, null);
                        }
                    } finally {
                        pendingResult.finish();
                    }
                }

                @Override
                public void onFailure(Call<Medicamento> call, Throwable t) {
                    try {
                        Log.e("API_ERROR", "Fallo en Medicamento: " + t.getMessage());
                        int userId = ApiClient.obtenerUsuarioId(context);
                        if (userId == rec.getUserID()) {
                            showNotification(context, rec, null);
                        }
                    } finally {
                        pendingResult.finish();
                    }
                }
            });
            //showNotification(context, rec, getMedicamentoMutable().getValue());

            if (rec.getIntervalo() != null && rec.getCantidad() > 1) {
                rec.setCantidad(rec.getCantidad() - 1);

                long triggerAt = System.currentTimeMillis() + rec.getIntervaloTime();

                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                Intent nextIntent = new Intent(context, AlarmReceiver.class);
                nextIntent.putExtra("recordatorio", rec);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        context, rec.getIDRec(), nextIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

                if (alarmManager != null) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAt, pendingIntent);
                }
            } else {
                rec.setEstado(0);
                Call<Recordatorio> call3 = Servicio.editarRecordatorio(token, rec);

                call3.enqueue(new Callback<Recordatorio>() {
                    @Override
                    public void onResponse(Call<Recordatorio> call, Response<Recordatorio> response) {
                        if (response.isSuccessful()) {
                            Log.e("Success", "Funciono");
                        } else {
                            Log.e("API_ERROR", "Fallo en Editar Recordatorio: ");
                        }
                    }

                    public void onFailure(Call<Recordatorio> call, Throwable t) {
                        Log.e("API_ERROR", "Fallo en Editar Recordatorio");
                    }
                });
            }
            ;
        }
    }

    private void showNotification(Context context, Recordatorio rec, Medicamento med) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Recordatorios de Medicamentos", NotificationManager.IMPORTANCE_HIGH);
        notificationManager.createNotificationChannel(channel);
        Intent notifyIntent = new Intent(context, MainActivity.class);
        notifyIntent.putExtra("navigate_to", "recordatorioActivado");
        notifyIntent.putExtra("recordatorio", rec);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, rec.getIDRec(), notifyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        String titleText = (med != null) ? "Hora de Tomar " + med.getNombre() : "Recordatorio de Medicamento";
        String doseText = (med != null) ? "Debes tomar " + med.getDosis() + " mg/ml, " : "";
        String contentText = doseText + "Quedan " + rec.getCantidad() + " dosis restantes.";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(titleText)
                .setContentText(contentText)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(false);

        notificationManager.notify(rec.getIDRec(), builder.build());
    }
}