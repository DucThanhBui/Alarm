package com.example.alarmapp.schedule

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ReScheduleAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "android.intent.action.BOOT_COMPLETED") {

        }
    }
}