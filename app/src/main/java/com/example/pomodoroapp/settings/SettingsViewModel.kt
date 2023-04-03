package com.example.pomodoroapp.settings

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pomodoroapp.database.PauseDatabaseDao
import com.example.pomodoroapp.database.PauseTime
import kotlinx.coroutines.*
import kotlin.properties.Delegates

class SettingsViewModel(
    val database: PauseDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

     var currentPauseTime = MutableLiveData<PauseTime?>()

    //Variables
    var newPauseTime = 0L

    init {
        initializeCurrentPauseTime()

    }

    private fun initializeCurrentPauseTime() {
        uiScope.launch {
            currentPauseTime.value = getPauseTimeFromDatabase()
        }
    }

    //Pobieranie czasu z BD
    private suspend fun getPauseTimeFromDatabase(): PauseTime? {
        return withContext(Dispatchers.IO) {
            var pauseTime = database.get(1L)
            pauseTime
        }
    }
    fun addTime() {
        uiScope.launch {
            val newDbPauseTime = PauseTime()
            insert(newDbPauseTime)
            currentPauseTime.value = getPauseTimeFromDatabase()
        }
    }
    private suspend fun insert(pauseTime: PauseTime) {
        withContext(Dispatchers.IO) {

            database.insert(pauseTime)
        }
    }
    //UPDATE
    fun saveNewPauseTime() {
        uiScope.launch {

            val oldPauseTime = currentPauseTime.value ?: return@launch
            oldPauseTime.pauseTimeStored = newPauseTime
            update(oldPauseTime)
            Log.i("Ustawieniaaaaaaaaaaaaaaaaaaaa", "$oldPauseTime.value")
        }
    }
    private suspend fun update(pauseTime: PauseTime) {
        withContext(Dispatchers.IO){

            database.update(pauseTime)
        }
    }





    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    fun calculateTime(progress: Int): String {
        newPauseTime = ((progress * 15).toLong()); // zamiana na sekundy
        var min = newPauseTime / 60
        var sec = newPauseTime % 60
        return "$min:$sec"
    }
}