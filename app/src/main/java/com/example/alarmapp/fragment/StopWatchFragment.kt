package com.example.alarmapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.alarmapp.R
import com.example.alarmapp.databinding.FragmentStopWatchBinding

class StopWatchFragment : Fragment() {

    private var _binding: FragmentStopWatchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_stop_watch, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvCurTime2.visibility = View.INVISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun StateStart() {
        binding.btStart.visibility = View.VISIBLE
        binding.tvCurTime1.visibility = View.VISIBLE

        binding.tvCurTime2.visibility = View.INVISIBLE
        binding.btPause.visibility = View.INVISIBLE
        binding.btStop.visibility = View.INVISIBLE
        binding.rvStopwatch.visibility = View.INVISIBLE
    }

    fun StateEmtpyHistory() {
        binding.btPause.visibility = View.VISIBLE
        binding.btStop.visibility = View.VISIBLE
        binding.tvCurTime1.visibility = View.VISIBLE

        binding.btStart.visibility = View.INVISIBLE
        binding.tvCurTime2.visibility = View.INVISIBLE
        binding.rvStopwatch.visibility = View.INVISIBLE
    }

    fun StateHaveHistory() {
        binding.btPause.visibility = View.VISIBLE
        binding.btStop.visibility = View.VISIBLE
        binding.tvCurTime2.visibility = View.VISIBLE
        binding.rvStopwatch.visibility = View.VISIBLE

        binding.btStart.visibility = View.INVISIBLE
        binding.tvCurTime1.visibility = View.INVISIBLE
    }
}