package com.example.alarmapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Alarm Items")
data class AlarmItem (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var time: String,
    var sound: String,
    var isVibrate: Boolean,
    var isRepeat: String,
    var deleteAfterTrigger: Boolean,
    var content: String,
    var isEnable: Boolean,
)