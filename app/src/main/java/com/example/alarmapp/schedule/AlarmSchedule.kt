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
    companion object {
        const val alarm_cont = "alarm cont"
    }

    private var alarmManager: AlarmManager? = null
    private var alarmIntent: PendingIntent

    init {
        alarmManager = context.getSystemService(AlarmManager::class.java)
        alarmIntent = Intent(context, AlarmReceiver::class.java).let {
            PendingIntent.getBroadcast(context, 0, it, PendingIntent.FLAG_MUTABLE)
        }
    }

        fun scheduleEveryday(item: AlarmItem) {
            val calendar: Calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, item.time.split(" : ")[0].toInt())
                set(Calendar.MINUTE, item.time.split(" : ")[1].toInt())
                set(Calendar.SECOND, 0)
            }

            alarmManager?.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                24*60*60*1000,
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