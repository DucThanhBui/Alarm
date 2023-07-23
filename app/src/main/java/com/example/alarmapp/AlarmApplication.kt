package com.example.alarmapp

import android.app.Application
import com.example.alarmapp.data.AlarmRoomDatabase

class AlarmApplication : Application() {
    val alarmDatabase: AlarmRoomDatabase by lazy {
        AlarmRoomDatabase.getDatabase(this)
    }
}