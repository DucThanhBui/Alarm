package com.example.alarmapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Stopwatch History")
data class StopwatchItem (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val previousTime: String,
    val curTime: String
)