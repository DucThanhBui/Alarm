package com.example.alarmapp.counter

import android.os.Handler
import android.os.Looper
import com.example.alarmapp.data.StopwatchItem
import com.example.alarmapp.databinding.FragmentStopWatchBinding

class CounterStopwatch(private val binding: FragmentStopWatchBinding) {

    var prevTime = 0
    var id = 0
    var m = 0
    var s = 0
    var mili = 0
    var runnable = Runnable {}
    var handler = Handler(Looper.myLooper()!!)

    fun startCount () {
        runnable = Runnable {
            binding.tvCurTime1.text = timeInText(m, s, mili)
            binding.tvCurTime2.text = timeInText(m, s, mili)
            mili += 1
            handler.postDelayed(runnable,10)
            if (m > 99) stopCount()
            if (mili > 99) {
                mili = 0
                s += 1
            }

            if (s > 59) {
                s = 0
                m += 1
            }
        }
        handler.post(runnable)
    }

    fun onStart() {
        binding.tvCurTime2.text = timeInText (0, 0, 0)
        binding.tvCurTime1.text = timeInText (0, 0, 0)
    }

    fun pauseCount () {
        handler.removeCallbacks(runnable)
        binding.tvCurTime2.text = timeInText (m, s, mili)
        binding.tvCurTime1.text = timeInText (m, s, mili)
    }

    fun stopCount() {
        handler.removeCallbacks(runnable)
        m = 0
        s = 0
        mili = 0
        prevTime = 0
        onStart()
    }

    fun saveResult(): StopwatchItem {
        val total = timeInText(m, s, mili)
        val dur = getDur(m, s, mili)
        prevTime = (m*60+s)*1000+mili*10
        return StopwatchItem(id, dur, total)
    }

    private fun convertDigit(digit: Int) = if (digit < 10) "0$digit" else "$digit"

    private fun getDur(min: Int, sec: Int, milisec: Int): String {
        val totalTime = (min * 60 + sec) * 1000 + milisec*10
        val duration = totalTime - prevTime
        val mi = duration / 60_000
        val se = (duration - mi*60_000) / 1000
        val mil = ((duration - mi*60_000) - se*1000) / 10

        return timeInText(mi, se, mil)
    }

    fun timeInText(hour: Int, min: Int, sec: Int) =
        convertDigit(hour) + " : " + convertDigit(min) + " : " + convertDigit(sec)

}