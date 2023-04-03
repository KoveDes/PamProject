package com.example.pomodoroapp.workingmode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.util.Objects

//TODO Nie wiem czy typ listy jest dobry
class WorkingModeViewModelFactory(private val currentTasks: MutableList<Objects>?) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkingModeViewModel::class.java)) {
            return WorkingModeViewModel(currentTasks) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
