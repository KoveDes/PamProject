package com.example.pomodoroapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TasksDatabaseDao {
    @Insert
    fun insert(task: Tasks)

    @Update
    fun update(task: Tasks)

    @Query("SELECT * FROM tasks_table WHERE taskId = :key")
    fun get(key:Long): Tasks?

    @Query("SELECT * FROM tasks_table ORDER BY task_state ASC")
    fun getAllTasks(): LiveData<List<Tasks>>

    @Query("SELECT * FROM tasks_table WHERE task_state = 1")
    fun getActiveTasks(): LiveData<List<Tasks>>
    
}