package com.multitimer.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.multitimer.step
import com.multitimer.timer.Timer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainScreenViewModel : ViewModel() {
    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()
    var timerId: Int = 0

    fun addTimer(name: String, hours: Int, minutes: Int, seconds: Int) {
        // Duration in milliseconds
        val duration: Long =
            ((hours.toLong() * 60 + minutes.toLong()) * 60 + seconds.toLong()) * 1000

        val t = if (name.isEmpty()) Timer(duration, getTimerId()) else Timer(duration, name)

        viewModelScope.launch(Dispatchers.Default) {
            t.run()
        }

        _state.update {
            it.copy(timers = (it.timers + t).sortedBy { it.timeLeft })
        }
    }

    fun toggleAdditionPopup() {
        _state.update {
            it.copy(showAdditionPopup = !it.showAdditionPopup)
        }
    }

    fun stopAlarm() {
        _state.update {
            it.copy(ringAlarm = false)
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                if ((_state.value.timers.firstOrNull()?.timeLeft ?: 1L) == 0L) {
                    _state.update {
                        it.copy(timers = it.timers.drop(1), ringAlarm = true)
                    }
                    Log.i("ViewModel", "A timer finished")
                }
                _state.update {
                    it.copy(displayTimers = it.timers.map {
                        val (hours, minutes, seconds) = it.getTime()
                        DisplayTimer(
                            it.name,
                            hours.toString().padStart(length = 2, padChar = '0'),
                            minutes.toString(),
                            seconds.toString()
                        )
                    })
                }

                delay(step)
            }
        }
    }

    private fun getTimerId(): String {
        timerId++

        return timerId.toString()
    }
}