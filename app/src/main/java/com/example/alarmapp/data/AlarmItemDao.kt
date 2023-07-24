package com.example.alarmapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: AlarmItem)

    @Update
    suspend fun update(item: AlarmItem)

    @Delete
    suspend fun delete(item: AlarmItem)

    @Query("SELECT * from `Alarm Items` WHERE id = :id")
    fun getItem(id: Int): Flow<AlarmItem>

    @Query("SELECT * from `Alarm Items` ORDER BY id ASC")
    fun getAllItems(): Flow<List<AlarmItem>>
}