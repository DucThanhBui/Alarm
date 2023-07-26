package com.example.alarmapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stopwatch table")
data class StopwatchItem (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val duration: String,
    val totalTime: String
)