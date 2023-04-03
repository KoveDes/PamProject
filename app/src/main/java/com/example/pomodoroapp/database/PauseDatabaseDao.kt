package com.example.pomodoroapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PauseDatabaseDao {
    @Insert
    fun insert(pauseTime: PauseTime)

    @Update
    fun update(pauseTime: PauseTime)

    @Query("SELECT * FROM pause_time_table WHERE pauseId = :key")
    fun get(key: Long): PauseTime?

    @Query("SELECT * FROM pause_time_table ORDER BY pauseId ASC")
    fun getAllPauses(): LiveData<List<PauseTime>>
}