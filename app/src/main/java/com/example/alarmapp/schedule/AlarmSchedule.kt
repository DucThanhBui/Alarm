package com.example.alarmapp.schedule

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.alarmapp.data.AlarmItem
import java.time.ZoneId
import java.util.Calendar

class AlarmSchedule(private val context: Context) {

    private var alarmManager: AlarmManager? = null
    private var alarmIntent: PendingIntent

    init {
        alarmManager = context.getSystemService(AlarmManager::class.java)
        alarmIntent = Intent(context, AlarmReceiver::class.java).let {
            PendingIntent.getBroadcast(context, 0, it, PendingIntent.FLAG_MUTABLE)
        }
    }

        fun schedule(item: AlarmItem) {
//            val calendar: Calendar = Calendar.getInstance().apply {
//                timeInMillis = System.currentTimeMillis()
//                set(Calendar.HOUR_OF_DAY, 11)
//                set(Calendar.MINUTE, 35)
//                set(Calendar.DAY_OF_WEEK, WEDNESDAY)
//            }

            // Set the alarm to start at 8:30 a.m.
            val calendar: Calendar = Calendar.getInstance().apply {
                add(Calendar.SECOND, 3)
                set(Calendar.HOUR_OF_DAY, item.time.split(" : ")[0].toInt())
                set(Calendar.MINUTE, item.time.split(" : ")[1].toInt())
            }

// setRepeating() lets you specify a precise custom interval--in this case,
// 20 minutes.
            alarmManager?.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                1000 * 60 * 20,
                alarmIntent
            )
            Log.d("Schedule", "schedule called")
            //alarmManager!!.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, alarmIntent)
        }

    private fun createExactAlarmIntent(item: AlarmItem): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java)
        return PendingIntent.getBroadcast(
            context,
            item.id,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }

}