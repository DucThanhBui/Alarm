package com.example.alarmapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface StopwatchDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: StopwatchItem)

    @Update
    suspend fun update(item: StopwatchItem)

    @Delete
    suspend fun delete(item: StopwatchItem)

    @Query("SELECT * from `stopwatch table` WHERE id = :id")
    fun getStopwatch(id: Int): Flow<StopwatchItem>

    @Query("SELECT * from `stopwatch table` ORDER BY id ASC")
    fun getAllStopwatch(): Flow<List<StopwatchItem>>

    @Query("DELETE FROM `stopwatch table`")
    suspend fun deleteAll()
}