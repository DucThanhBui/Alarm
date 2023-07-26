package com.example.alarmapp.schedule

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.alarmapp.R

private const val CHANEL_NAME = "CHANEL NAME"
private const val CHANEL_DESCRIPTION = "CHANEL DES"
private const val CHANNEL_ID = "CHANEL ID"

private const val textTitle = "Alarm"
private const val textContent = "Alarm content"
private const val notificationId = 1

class AlarmReceiver : BroadcastReceiver() {
    //private lateinit var mediaPlayer: MediaPlayer
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "I'm running", Toast.LENGTH_LONG).show();
        Log.d("Notification", "noti started")
        //create notification
        val builder = NotificationCompat.Builder(context!!, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle(textTitle)
            .setContentText(textContent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        val name = CHANEL_NAME
        val descriptionText = CHANEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            context.getSystemService(NotificationManager::class.java) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            )
            notify(notificationId, builder.build())
        }
    }
}