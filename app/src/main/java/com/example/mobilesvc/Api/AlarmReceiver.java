package com.example.mobilesvc.Api;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.mobilesvc.Clases.Recordatorio;
import com.example.mobilesvc.MainActivity;
import com.example.mobilesvc.R;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "MED_REMINDER_CHANNEL";

    @SuppressLint("ScheduleExactAlarm")
    @Override
    public void onReceive(Context context, Intent intent) {
        Recordatorio rec = (Recordatorio) intent.getSerializableExtra("recordatorio");
        showNotification(context, rec);

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
        }
    }

    private void showNotification(Context context, Recordatorio rec) {
        //la alarma se crea, y se activa cuando pasa el tiempo, pero no muestra/hace nada, arreglar
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Recordatorios de Medicamentos", NotificationManager.IMPORTANCE_HIGH);
        notificationManager.createNotificationChannel(channel);
        Intent notifyIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, notifyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);



        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Hora de Tomar tu Medicamento")
                .setContentText("Es momento de tomar tu dosis. Quedan " + (rec.getCantidad() - 1) + " restantes.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(false);

        notificationManager.notify(rec.getIDRec(), builder.build());
    }
}