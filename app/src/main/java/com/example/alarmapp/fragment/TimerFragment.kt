package com.example.alarmapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.example.alarmapp.R
import com.example.alarmapp.databinding.FragmentTimerBinding
import com.example.alarmapp.counter.CounterTimer

class TimerFragment : Fragment() {

    private var _binding: FragmentTimerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_timer, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //binding.btPauseTimer.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.baseline_pause_24, null))
        binding.btPauseTimer.visibility = View.INVISIBLE
        binding.tvTimer.visibility = View.INVISIBLE
        binding.btStopTimer.visibility = View.INVISIBLE


        val fm = NumberPicker.Formatter { value ->
            if (value < 10) {
                "0$value"
            } else {
                "$value"
            }
        }

        binding.npHour.apply {
            maxValue = 23
            minValue = 0
            setFormatter(fm)
        }

        binding.npMin.apply {
            maxValue = 59
            minValue = 0
            setFormatter(fm)
        }

        binding.npSec.apply {
            maxValue = 59
            minValue = 0
            setFormatter(fm)
        }

        var hour = 0
        var min = 0
        var sec = 0
        var counterTimer = CounterTimer(hour, min, sec, binding.tvTimer)

        binding.btStartTimer.setOnClickListener {
            binding.npHour.visibility = View.INVISIBLE
            binding.npMin.visibility = View.INVISIBLE
            binding.npSec.visibility = View.INVISIBLE
            binding.btStartTimer.visibility = View.INVISIBLE

            binding.btPauseTimer.visibility = View.VISIBLE
            binding.btStopTimer.visibility = View.VISIBLE
            binding.tvTimer.visibility = View.VISIBLE

            hour = binding.npHour.value
            min = binding.npMin.value
            sec = binding.npSec.value

            counterTimer = CounterTimer(hour, min, sec, binding.tvTimer)
            binding.tvTimer.text = counterTimer.timeInText(hour, min, sec)
            counterTimer.startCountDown()
        }


        binding.btPauseTimer.setOnClickListener {
            if (binding.btPauseTimer.tag == "pause") {
                counterTimer.pauseCountDown()
                binding.btPauseTimer.tag = "start"
                binding.btPauseTimer.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.baseline_play_arrow_24, null))
            } else {
                counterTimer.startCountDown()
                binding.btPauseTimer.tag = "pause"
                binding.btPauseTimer.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.baseline_pause_24, null))
            }
        }




        binding.btStopTimer.setOnClickListener {
            counterTimer.stopCountDown()
            binding.tvTimer.visibility = View.INVISIBLE
            binding.btPauseTimer.visibility = View.INVISIBLE
            binding.btStopTimer.visibility = View.INVISIBLE

            binding.npHour.visibility = View.VISIBLE
            binding.npMin.visibility = View.VISIBLE
            binding.npSec.visibility = View.VISIBLE
            binding.btStartTimer.visibility = View.VISIBLE
        }
    }

    fun pause() {
        Toast.makeText(requireContext(), "Pause", Toast.LENGTH_SHORT).show()
    }
    fun resume() {
        Toast.makeText(requireContext(), "Resume", Toast.LENGTH_SHORT).show()
    }

    fun clear() {
        Toast.makeText(requireContext(), "Clear", Toast.LENGTH_SHORT).show()
    }

    fun doneNotif() {
        Toast.makeText(requireContext(), "End timer", Toast.LENGTH_SHORT).show()
    }

    fun convertDigit(digit: Int) = if (digit < 10) "0$digit" else "$digit"

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}