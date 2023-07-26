package com.example.alarmapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmapp.AlarmApplication
import com.example.alarmapp.R
import com.example.alarmapp.adapter.StopwatchAdapter
import com.example.alarmapp.counter.CounterStopwatch
import com.example.alarmapp.databinding.FragmentStopWatchBinding
import com.example.alarmapp.viewmodel.ShareViewModel
import com.example.alarmapp.viewmodel.ShareViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StopWatchFragment : Fragment() {

    private var _binding: FragmentStopWatchBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var counterStopwatch: CounterStopwatch


    private val viewModel: ShareViewModel by activityViewModels {
        ShareViewModelFactory(
            (activity?.application as AlarmApplication).alarmDatabase
                .itemDao(),
            (activity?.application as AlarmApplication).alarmDatabase
                .stopwatchDao()
        )
    }

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
        counterStopwatch = CounterStopwatch(binding)
        recyclerView = binding.rvStopwatch
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = StopwatchAdapter()
        recyclerView.adapter = adapter

        lifecycle.coroutineScope.launch {
            viewModel.getAllStopwatchItems().collect() {
                adapter.submitList(it)
            }
        }

        stateStart()
        counterStopwatch.onStart()

        binding.btStart.setOnClickListener {
            stateEmptyHistory1()
            startCountNoHistory()
        }

        binding.btPause.setOnClickListener {
            if (binding.btPause.tag == "stateEmptyHistory1" && binding.btStop.tag == "stateEmptyHistory1") {
                stateEmptyHistory2()
                pauseCountNoHistory()
            } else if (binding.btPause.tag == "stateEmptyHistory2" && binding.btStop.tag == "stateEmptyHistory2") {
                stateEmptyHistory1()
                startCountNoHistory()
            } else if (binding.btPause.tag == "stateHaveHistoryContinue" && binding.btStop.tag == "stateHaveHistoryContinue") {
                stateHaveHistoryPause()
                pauseAndHadHistory()
            } else {
                stateHaveHistoryContinue()
                startCountHadHistory()
            }
        }

        binding.btStop.setOnClickListener {
            if (binding.btPause.tag == "stateEmptyHistory1" && binding.btStop.tag == "stateEmptyHistory1") {
                stateHaveHistoryContinue()
                saveHistoryButCounting()
            } else if (binding.btPause.tag == "stateEmptyHistory2" && binding.btStop.tag == "stateEmptyHistory2") {
                stateStart()
                resetCount()
            } else if (binding.btPause.tag == "stateHaveHistoryContinue" && binding.btStop.tag == "stateHaveHistoryContinue") {
                saveHistoryButCounting()
            } else {
                stateStart()
                resetCount()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun stateStart() {
        binding.btStart.visibility = View.VISIBLE
        binding.tvCurTime1.visibility = View.VISIBLE

        binding.tvCurTime2.visibility = View.INVISIBLE
        binding.btPause.visibility = View.INVISIBLE
        binding.btStop.visibility = View.INVISIBLE
        binding.rvStopwatch.visibility = View.INVISIBLE
    }

    //counting but no history
    fun stateEmptyHistory1() {
        binding.btPause.visibility = View.VISIBLE
        binding.btPause.tag = "stateEmptyHistory1"
        binding.btStop.visibility = View.VISIBLE
        binding.btStop.tag = "stateEmptyHistory1"
        binding.tvCurTime1.visibility = View.VISIBLE

        binding.btStart.visibility = View.INVISIBLE
        binding.tvCurTime2.visibility = View.INVISIBLE
        binding.rvStopwatch.visibility = View.INVISIBLE
        binding.btStop.setImageDrawable(ResourcesCompat.getDrawable(resources,
            R.drawable.baseline_flag_24, null))
        binding.btPause.setImageDrawable(ResourcesCompat.getDrawable(resources,
            R.drawable.baseline_pause_24, null))
    }

    //pause and have no history
    fun stateEmptyHistory2() {
        binding.btPause.visibility = View.VISIBLE
        binding.btPause.tag = "stateEmptyHistory2"
        binding.btStop.visibility = View.VISIBLE
        binding.btStop.tag = "stateEmptyHistory2"
        binding.tvCurTime1.visibility = View.VISIBLE

        binding.btStart.visibility = View.INVISIBLE
        binding.tvCurTime2.visibility = View.INVISIBLE
        binding.rvStopwatch.visibility = View.INVISIBLE
        binding.btStop.setImageDrawable(ResourcesCompat.getDrawable(resources,
            R.drawable.baseline_stop_24, null))
        binding.btPause.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.baseline_play_arrow_24, null))
    }

    fun stateHaveHistoryContinue() {
        binding.btPause.visibility = View.VISIBLE
        binding.btPause.tag = "stateHaveHistoryContinue"
        binding.btStop.visibility = View.VISIBLE
        binding.btStop.tag = "stateHaveHistoryContinue"
        binding.tvCurTime2.visibility = View.VISIBLE
        binding.rvStopwatch.visibility = View.VISIBLE

        binding.btStart.visibility = View.INVISIBLE
        binding.tvCurTime1.visibility = View.INVISIBLE
        binding.btStop.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.baseline_flag_24, null))
        binding.btPause.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.baseline_pause_24, null))
    }

    fun stateHaveHistoryPause() {
        binding.btPause.visibility = View.VISIBLE
        binding.btPause.tag = "stateHaveHistoryPause"
        binding.btStop.visibility = View.VISIBLE
        binding.btStop.tag = "stateHaveHistoryPause"
        binding.tvCurTime2.visibility = View.VISIBLE
        binding.rvStopwatch.visibility = View.VISIBLE

        binding.btStart.visibility = View.INVISIBLE
        binding.tvCurTime1.visibility = View.INVISIBLE
        binding.btStop.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.baseline_stop_24, null))
        binding.btPause.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.baseline_play_arrow_24, null))
    }

    fun startCountNoHistory() {
        counterStopwatch.startCount()
        //Toast.makeText(requireContext(), "start Count no history", Toast.LENGTH_SHORT).show()
    }
    fun startCountHadHistory() {
        counterStopwatch.startCount()
        //Toast.makeText(requireContext(), "start Count had history", Toast.LENGTH_SHORT).show()
    }

    fun pauseCountNoHistory() {
        counterStopwatch.pauseCount()
        //Toast.makeText(requireContext(), "pause Count no history", Toast.LENGTH_SHORT).show()
    }

    fun saveHistoryButCounting() {
        val item = counterStopwatch.saveResult()
        viewModel.addStopwatch(item)
        //Toast.makeText(requireContext(), "count and save history ", Toast.LENGTH_SHORT).show()
    }
    fun pauseAndHadHistory() {
        counterStopwatch.pauseCount()
        //Toast.makeText(requireContext(), "pause and had history ", Toast.LENGTH_SHORT).show()
    }

    fun resetCount() {
        counterStopwatch.stopCount()
        lifecycleScope.launch(Dispatchers.IO) {
            requireActivity().runOnUiThread {
                viewModel.deleteAllStopwatchHistory()
            }


        }
        //Toast.makeText(requireContext(), "restart count", Toast.LENGTH_SHORT).show()
    }
}