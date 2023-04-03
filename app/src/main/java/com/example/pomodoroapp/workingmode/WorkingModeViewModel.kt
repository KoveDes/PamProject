package com.example.pomodoroapp.workingmode

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pomodoroapp.pause.PauseFragment
import com.example.pomodoroapp.pause.PauseViewModel
import java.util.*


class WorkingModeViewModel(currentTasks: MutableList<Objects>?) : ViewModel() {
    /*
    ViewModel holds data for UI and shouldn't reference activities or views
     */

    //TODO wygenerować listę zadań



    companion object {
        private const val DONE = 0L
        private const val ONE_SECOND = 1000L
        private const val COUNTDOWN_TIME = ONE_SECOND * 60 * 25

    }

    //Tasks
    val currentTasksList = currentTasks


    private var timer: CountDownTimer

    //Variables
    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long> get() = _currentTime


    private val _timerState = MutableLiveData<String>()
    val timerState: LiveData<String> get() = _timerState

    private var timeLeft = 0L


    init {
        Log.i("WorkingModeViewModel", "WorkingModeViewModel created")
        //Timer
        timeLeft = COUNTDOWN_TIME
        _timerState.value = "Running"

        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = (millisUntilFinished / ONE_SECOND)
                timeLeft -= ONE_SECOND
            }

            override fun onFinish() {
                _currentTime.value = DONE;
                _timerState.value = "Finished"
                //TODO: Zaimplementować przejście do fragmentu Pauzy
                //TODO: Zaimplementować usunięcie aktywnych i zakonczonych zadan z listy
                //TODO: Zapytać o ukończenie zadań?

            }
        }
        timer.start()

    }

    //Notifications


    override fun onCleared() {
        super.onCleared()
        timer.cancel()// zatrzymuje licznik
        Log.i("WorkingModeViewModel", "WorkingModeViewModel destroyed")
    }

    fun sendIntervalFlag() {
        val argBundle = Bundle()
        val fragment = PauseFragment()

        argBundle.putBoolean("isFromWorkingMode", true)
        fragment.arguments = argBundle

    }

    fun pauseOrResume() {
//        TODO("Not yet implemented")
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

                }
            }
            timer.start()
        }

    }

    fun stopTimer() {
        timer.cancel()
    }
}