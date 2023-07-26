package com.example.alarmapp.counter

import android.os.Looper
import android.widget.TextView
import android.os.Handler
import com.example.alarmapp.databinding.FragmentTimerBinding
import com.example.alarmapp.fragment.TimerFragment

class CounterTimer (
    private val hour: Int, private val min: Int, private val sec: Int,
    private val counter_text: TextView,
    private val frg: TimerFragment) {

    private var number = hour * 60 * 60 + min * 60 + sec
    var h = hour
    var m = min
    var s = sec
    var runnable = Runnable {}
    var handler = Handler(Looper.myLooper()!!)

    fun startCountDown () {
        runnable = Runnable {
            counter_text.text = timeInText(h, m, s)
            number -= 1
            s -= 1
            handler.postDelayed(runnable,1000)
            if (number < 0) {
                stopCountDown()
            }
            if (s < 0) {
                if (m > 0) m -= 1
                s = 59
            }

            if (m < 0) {
                m = 59
                if (h > 0) h -= 1
            }
        }
        handler.post(runnable)
    }
    fun pauseCountDown () {
        handler.removeCallbacks(runnable)
        counter_text.text = timeInText(h, m, s)
    }

    fun stopCountDown() {
        handler.removeCallbacks(runnable)
        frg.startState()
    }

    private fun convertDigit(digit: Int) = if (digit < 10) "0$digit" else "$digit"

    fun timeInText(hour: Int, min: Int, sec: Int) =
        convertDigit(hour) + " : " + convertDigit(min) + " : " + convertDigit(sec)

}