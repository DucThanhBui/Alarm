package com.example.alarmapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmapp.R
import com.example.alarmapp.data.AlarmItem
import com.example.alarmapp.data.AlarmItemDao
import kotlinx.coroutines.flow.collect

class AlarmAdapter(private val alarmItemDao: AlarmItemDao) : RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {

    class AlarmViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val alarmTime: TextView

        init {
            alarmTime  = view.findViewById(R.id.txtTime)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.alarm_item, parent, false)
        return AlarmViewHolder(view)
    }

    override fun getItemCount() = 0

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
//        holder.alarmTime.text
//        val tmp = alarmItemDao.getItem(position).collect {vaulue -> }
    }
}