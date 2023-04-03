package com.example.pomodoroapp.pause

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pomodoroapp.database.PauseDatabaseDao
import com.example.pomodoroapp.database.TasksDatabaseDao
import com.example.pomodoroapp.settings.SettingsViewModel

class SettingsViewModelFactory(private val dataSource: PauseDatabaseDao, private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
