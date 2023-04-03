package com.example.pomodoroapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Tasks::class, PauseTime::class], version = 1, exportSchema = false)
abstract class PomodoroDatabase: RoomDatabase() {
    abstract val tasksDatabaseDao: TasksDatabaseDao
    abstract val pauseDatabaseDao: PauseDatabaseDao

    companion object {
        //Pozwala uniknąć łączenia się wiele razy z bazą danych
        @Volatile
        private var INSTANCE: PomodoroDatabase? = null

        fun getInstance(context: Context): PomodoroDatabase {
            synchronized(this) {
                var instance = INSTANCE

                //sprawdza czy BD już istnieje
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext, PomodoroDatabase::class.java, "sleep_history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}