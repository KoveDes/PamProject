package com.example.pomodoroapp.database


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks_table")
data class Tasks(
    @PrimaryKey(autoGenerate = true)
    var taskId: Long = 0L,

    @ColumnInfo(name = "task_text")
    var taskText: String =  "",

    @ColumnInfo(name = "intervals_needed")
    var IntervalsNeeded: Int = 1,

    @ColumnInfo(name = "task_state")
    var taskState: Int = 0
)