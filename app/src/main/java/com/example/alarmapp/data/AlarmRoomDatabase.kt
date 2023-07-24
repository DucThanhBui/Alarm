package com.example.alarmapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [AlarmItem::class],
    version = 2,
    exportSchema = false
)
abstract class AlarmRoomDatabase : RoomDatabase() {

    abstract fun itemDao(): AlarmItemDao

    companion object {
        @Volatile
        private var INSTANCE: AlarmRoomDatabase? = null
        fun getDatabase(context: Context): AlarmRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AlarmRoomDatabase::class.java,
                    "alarm_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                //return instance
                return instance
            }
        }
    }
}