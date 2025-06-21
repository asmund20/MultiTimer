package com.multitimer.timer

import android.os.SystemClock
import com.multitimer.step
import kotlinx.coroutines.delay

class Timer(val duration: Long, val name: String) {
    var timeLeft = duration
    var isFinished = false

    suspend fun run() {
        var startTime = SystemClock.uptimeMillis()

        while (timeLeft > 0) {
            timeLeft = (duration - SystemClock.uptimeMillis() + startTime).coerceAtLeast(0)
            delay(step.coerceAtMost(timeLeft))
        }
        isFinished = true
    }

    /**
     * @return <Hours, Minutes, Seconds> in a triple
     */
    fun getTime(): Triple<Int, Int, Int> {
        // Convert milliseconds to total seconds
        val totalSeconds = (timeLeft / 1000).toInt()

        // Calculate hours
        val hours = totalSeconds / 3600

        // Calculate remaining seconds after hours
        val remainingSecondsAfterHours = totalSeconds % 3600

        // Calculate minutes
        val minutes = remainingSecondsAfterHours / 60

        // Calculate seconds
        val seconds = remainingSecondsAfterHours % 60

        return Triple(hours, minutes, seconds)
    }
}
