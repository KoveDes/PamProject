package com.example.pomodoroapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pause_time_table")
data class PauseTime(
    @PrimaryKey(autoGenerate = true)
    var pauseId: Long = 0L, //id

    @ColumnInfo(name = "pause_time_stored")
    var pauseTimeStored: Long =  300000,

)