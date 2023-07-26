package com.example.alarmapp.schedule

import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

private const val CHANEL_NAME = "CHANEL NAME"
private const val CHANEL_DESCRIPTION = "CHANEL DES"
private const val CHANNEL_ID = "CHANEL ID"

private const val textTitle = "notif title test"
private const val textContent = "notif content test"
private const val notificationId = 1

class AlarmReceiver : BroadcastReceiver() {
    //private lateinit var mediaPlayer: MediaPlayer
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "I'm running", Toast.LENGTH_LONG).show();
        Log.d("Notification", "noti started")
        //create notification
//        val builder = NotificationCompat.Builder(context!!, CHANNEL_ID)
//            .setSmallIcon(R.drawable.notification_icon)
//            .setContentTitle(textTitle)
//            .setContentText(textContent)
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            .setAutoCancel(true)
//
//        val name = CHANEL_NAME
//        val descriptionText = CHANEL_DESCRIPTION
//        val importance = NotificationManager.IMPORTANCE_DEFAULT
//        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
//            description = descriptionText
//        }
//        // Register the channel with the system
//        val notificationManager: NotificationManager =
//            context.getSystemService(NotificationManager::class.java) as NotificationManager
//        notificationManager.createNotificationChannel(channel)
//
//        with(NotificationManagerCompat.from(context)) {
//            // notificationId is a unique int for each notification that you must define
//            if (ActivityCompat.checkSelfPermission(
//                    context,
//                    Manifest.permission.POST_NOTIFICATIONS
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return
//            }
//            notify(notificationId, builder.build())
//        }
    }
}