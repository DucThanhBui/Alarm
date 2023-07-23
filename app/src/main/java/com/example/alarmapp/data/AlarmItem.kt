package com.example.alarmapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Alarm Items")
data class AlarmItem (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val time: String,
    val sound: String,
    val isVibrate: Boolean,
    val isRepeat: String,
    val deleteAfterTrigger: Boolean,
    val content: String
)