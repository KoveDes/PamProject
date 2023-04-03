package com.example.pomodoroapp.pause

import android.app.Application
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pomodoroapp.database.PauseDatabaseDao
import com.example.pomodoroapp.database.PauseTime
import kotlinx.coroutines.*

class PauseViewModel(
    isFromWorkingMode: Boolean,
    val database: PauseDatabaseDao,
    application: Application

) : AndroidViewModel(application) {
    companion object {
        private const val DONE = 0L
        private const val ONE_SECOND = 1000L
    }

    //DATABASE
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val _pauseTimeFromDb = MutableLiveData<PauseTime>()
    val pauseTimeFromDb : LiveData<PauseTime> get() =_pauseTimeFromDb


    //Arguments
    val isPauseTimeRetrived: Boolean = false;
     val pauseTime: Long = _pauseTimeFromDb.value?.pauseTimeStored ?: 300000//time in MS

    //Timer
    private lateinit var timer: CountDownTimer

    //Timer variables for LIVEDATA
    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long> get() = _currentTime

    private val _timerState = MutableLiveData<String>()
    val timerState: LiveData<String> get() = _timerState

    private var timeLeft = 0L



    private fun initializePauseTimeDb() {
        uiScope.launch {
            _pauseTimeFromDb.value = getPauseTimeFromDatabase()
        }
    }
    private suspend fun getPauseTimeFromDatabase(): PauseTime? {
        return withContext(Dispatchers.IO) {
            var pauseTime = database.get(1L)
            pauseTime
        }
    }

    //ViewModel initialization
    init {
        initializePauseTimeDb()
        Log.i("PauseModel", "PauseViewModel created")
        timeLeft = pauseTime
        _timerState.value = "Running"
        timer = object : CountDownTimer(
            pauseTime,
            ONE_SECOND
        ) {
            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = (millisUntilFinished / ONE_SECOND)
                timeLeft -= ONE_SECOND
            }

            override fun onFinish() {
                _currentTime.value = DONE;
                _timerState.value = "Finished"
            }

//            timer.start()
        }

    }




    fun pauseOrResume() {
        if (_timerState.value == "Running") {
            _timerState.value = "Stopped"
            timer.cancel()
        } else {
            _timerState.value = "Running"
            timer = object : CountDownTimer(timeLeft, ONE_SECOND) {
                override fun onTick(millisUntilFinished: Long) {
                    _currentTime.value = (millisUntilFinished / ONE_SECOND)
                    timeLeft -= ONE_SECOND
                }

                override fun onFinish() {
                    _currentTime.value = DONE;
                    _timerState.value = "Finished"
                }
            }
            timer.start()
        }

    }


    fun stopTimer() {
        timer.cancel()
    }

    fun startTimer() {
        timer.start()
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
        Log.i("PauseViewModel", "PauseModeViewModel destroyed")
    }

}