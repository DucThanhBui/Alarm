package com.example.alarmapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmapp.R
import com.example.alarmapp.data.AlarmItem
import com.example.alarmapp.data.AlarmItemDao
import com.example.alarmapp.databinding.AlarmItemBinding
import kotlinx.coroutines.flow.collect

class AlarmAdapter(
    private val onItemClicked: (AlarmItem) -> Unit
) : ListAdapter<AlarmItem, AlarmAdapter.AlarmViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<AlarmItem>() {
            override fun areItemsTheSame(oldItem: AlarmItem, newItem: AlarmItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: AlarmItem, newItem: AlarmItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    class AlarmViewHolder(private val binding: AlarmItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AlarmItem) {
            binding.apply {
                txtTime.text = item.time
                alarmContent.text = item.content
                txtScheduled.text = item.isRepeat
                isAlarmOn.isChecked = item.isEnable
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val viewHolder = AlarmViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.alarm_item, parent, false
            )
        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}