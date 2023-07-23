package com.example.alarmapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.alarmapp.AlarmApplication
import com.example.alarmapp.R
import com.example.alarmapp.data.AlarmItem
import com.example.alarmapp.databinding.FragmentAddAlarmBinding
import com.example.alarmapp.viewmodel.ShareViewModel
import com.example.alarmapp.viewmodel.ShareViewModelFactory

class AddAlarmFragment : Fragment() {

    lateinit var item: AlarmItem
    private var _binding: FragmentAddAlarmBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ShareViewModel by activityViewModels {
        ShareViewModelFactory (
            (activity?.application as AlarmApplication).alarmDatabase
                .itemDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_add_alarm, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.timePickerAlarm.setIs24HourView(true)

        binding.alarmSave.setOnClickListener {
            addNewItem()
        }
        binding.alarmCancel.setOnClickListener {
            backToAlarmScreen()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Inserts the new Item into database and navigates up to list fragment.
     */
    private fun addNewItem() {
        val time = binding.timePickerAlarm.hour.toString() + " : " + binding.timePickerAlarm.minute
        viewModel.addNewItem(
            time = time,
            sound = binding.alarmSound.text.toString(),
            vibrate = binding.alarmVibrateCheck.isChecked,
            repeat = "Mot lan",
            delete = binding.alarmDeleteCheck.isChecked,
            content = "Mac dinh"
        )
        //backToAlarmScreen()
    }

    private fun backToAlarmScreen() {
        val action = AddAlarmFragmentDirections.actionAddAlarmFragmentToAlarmFragment()
        findNavController().navigate(action)
    }
}