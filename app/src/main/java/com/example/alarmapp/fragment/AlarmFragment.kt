package com.example.alarmapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmapp.AlarmApplication
import com.example.alarmapp.R
import com.example.alarmapp.adapter.AlarmAdapter
import com.example.alarmapp.databinding.FragmentAlarmBinding
import com.example.alarmapp.viewmodel.ShareViewModel
import com.example.alarmapp.viewmodel.ShareViewModelFactory
import kotlinx.coroutines.launch

class AlarmFragment : Fragment() {

    private var _binding: FragmentAlarmBinding? = null
    private val binding get() = _binding!!
    private lateinit var recycleView: RecyclerView

    private val viewModel: ShareViewModel by activityViewModels {
        ShareViewModelFactory(
            (activity?.application as AlarmApplication).alarmDatabase
                .itemDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_alarm, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController
        recycleView = binding.rvAlarmList
        recycleView.layoutManager = LinearLayoutManager(requireContext())



        val alarmAdapter = AlarmAdapter {
            val action = AlarmFragmentDirections.actionAlarmFragmentToAddAlarmFragment()
            view.findNavController().navigate(action)
        }
        recycleView.adapter = alarmAdapter
        lifecycle.coroutineScope.launch {
            viewModel.getAllItems().collect() {
                alarmAdapter.submitList(it)
            }
        }

        binding.btAddAlarm.setOnClickListener {
            ///\view.findNavController().navigate(R.id.action_alarmFragment_to_addAlarmFragment)
        }
        binding.rvAlarmList.layoutManager = LinearLayoutManager(this.context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun Fragment.findNavControllerSafely(id: Int): NavController? {
        return if (findNavController().currentDestination?.id == id) {
            findNavController()
        } else {
            null
        }
    }
}