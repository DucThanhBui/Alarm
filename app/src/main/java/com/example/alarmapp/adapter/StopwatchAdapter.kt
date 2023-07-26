package com.example.alarmapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmapp.R
import com.example.alarmapp.data.StopwatchItem
import com.example.alarmapp.databinding.StopwatchItemBinding

class StopwatchAdapter() : ListAdapter<StopwatchItem, StopwatchAdapter.StopwatchViewHolder>(DiffCallback) {
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<StopwatchItem>() {
            override fun areItemsTheSame(oldItem: StopwatchItem, newItem: StopwatchItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: StopwatchItem, newItem: StopwatchItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    class StopwatchViewHolder(private val binding: StopwatchItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StopwatchItem, pos: Int) {
            binding.apply {
                tvId.text = if (pos < 10) "0" + pos else pos.toString()
                tvDuration.text = item.duration
                tvSum.text = item.totalTime
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StopwatchAdapter.StopwatchViewHolder {
        val viewHolder = StopwatchAdapter.StopwatchViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.stopwatch_item, parent, false
            )
        )
        return viewHolder
    }

    override fun onBindViewHolder(holder: StopwatchViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

//    override fun onBindViewHolder(holder: AlarmAdapter.AlarmViewHolder, position: Int) {
//        holder.bind(getItem(position))
//    }
}