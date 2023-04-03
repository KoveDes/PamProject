package com.example.pomodoroapp.pause

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pomodoroapp.database.PauseDatabaseDao
import com.example.pomodoroapp.database.TasksDatabaseDao

class PauseViewModelfactory(
    private val isFromWorkingMode: Boolean,
    private val dataSource: PauseDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PauseViewModel::class.java)) {
            return PauseViewModel(isFromWorkingMode, dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
