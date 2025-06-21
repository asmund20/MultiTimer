package com.multitimer.ui.main

import com.multitimer.timer.Timer

data class DisplayTimer(
    val name: String,
    val hours: String,
    val minutes: String,
    val seconds: String,
)

data class State(
    val timers: List<Timer> = emptyList(),
    val displayTimers: List<DisplayTimer> = emptyList(),
    val ringAlarm: Boolean = false,
    val showAdditionPopup: Boolean = false,
)
