package com.example.alarmapp.fragment

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.alarmapp.AlarmApplication
import com.example.alarmapp.R
import com.example.alarmapp.data.AlarmItem
import com.example.alarmapp.databinding.FragmentAddAlarmBinding
import com.example.alarmapp.schedule.AlarmReceiver
import com.example.alarmapp.viewmodel.ShareViewModel
import com.example.alarmapp.viewmodel.ShareViewModelFactory
import java.util.Calendar

class AddAlarmFragment : Fragment() {

    lateinit var item: AlarmItem
    private var _binding: FragmentAddAlarmBinding? = null
    private val binding get() = _binding!!


    private val viewModel: ShareViewModel by activityViewModels {
        ShareViewModelFactory (
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
        _binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_add_alarm, container, false)
        return binding.root
    }

    companion object {
        const val requestId = 1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.timePickerAlarm.setIs24HourView(true)
        val alarmManager =
            requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent =
            PendingIntent.getBroadcast(
                context, requestId, Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_IMMUTABLE
            )
//        if (pendingIntent != null && alarmManager != null) {
//            alarmManager.cancel(pendingIntent)
//        }

        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 15)
            set(Calendar.MINUTE, 11)
            set(Calendar.SECOND, 0)
        }

// setRepeating() lets you specify a precise custom interval--in this case,
// 20 minutes.


        ////

        val id = arguments?.getString(AlarmFragment.ITEM_ID)?.toInt()
        //Log.d("Hello", "id = $id")

        if (id != null) {
            viewModel.retrieveItem(id).asLiveData().observe(this.viewLifecycleOwner) { selectedItem ->
                //Log.d("Sua", "data moi")
                item = selectedItem
                update(item)
            }
        } else {
            binding.alarmSave.setOnClickListener {
                viewModel.addNewAlarm(getCurrentAlarmInfo())
//                sc.schedule(getCurrentAlarmInfo())
                alarmManager.set (
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + 60 * 1000,
                    pendingIntent
                )
                Log.d("schedule", "start")
                backToAlarmScreen()
            }
        }
        binding.alarmCancel.setOnClickListener {
            backToAlarmScreen()
        }
        binding.alarmSound.setOnClickListener {
            Toast.makeText(requireContext(), "Chon nhac chuong", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun getCurrentAlarmInfo(): AlarmItem {
        var time = ""
        val hour = binding.timePickerAlarm.hour.toInt()
        time += if (hour < 10) "0$hour" else "$hour"
        time += " : "
        val minute = binding.timePickerAlarm.minute.toInt()
        time += if (minute < 10) "0$minute" else "$minute"
        return AlarmItem (
            time = time,
            sound = binding.alarmSound.text.toString(),
            isVibrate = binding.alarmVibrateCheck.isChecked,
            isRepeat = binding.alarmRepeat.text.toString(),
            deleteAfterTrigger = binding.alarmDeleteCheck.isChecked,
            content = binding.alarmContent.text.toString(),
            isEnable = true
        )
    }

    private fun update(item: AlarmItem) {
        binding.alarmSave.setOnClickListener {
                val newItem = getCurrentAlarmInfo().copy(id = item.id)
                viewModel.updateAlarm(item, newItem)
                backToAlarmScreen()
        }
    }

    private fun backToAlarmScreen() {
        findNavController().popBackStack()
    }
}