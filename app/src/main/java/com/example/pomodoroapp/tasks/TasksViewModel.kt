package com.example.pomodoroapp.tasks

import android.util.Log
import androidx.lifecycle.ViewModel

class TasksViewModel:ViewModel() {
    init {
        Log.i("TaskViewModel", "TaskViewModel created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("TaskViewModel", "TaskViewModel destroyed")
    }
}