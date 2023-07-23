package com.example.alarmapp.fragment

import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.example.alarmapp.R
import com.example.alarmapp.adapter.ViewPagerAdapter
import com.example.alarmapp.databinding.FragmentViewPagerBinding
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerFragment : Fragment() {

    private lateinit var binding: FragmentViewPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_view_pager, container, false)

        val fragmentList = arrayListOf(
            AlarmFragment(),
            ClockFragment(),
            StopWatchFragment(),
            TimerFragment()
        )

        val adapter = ViewPagerAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)

        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.icon = when (position) {
                0 -> {
                    ResourcesCompat.getDrawable(resources, R.drawable.img_alarm_black, null)
                }
                1 -> {
                    ResourcesCompat.getDrawable(resources, R.drawable.img_clock_black_900, null)
                }
                2 -> {
                    ResourcesCompat.getDrawable(resources, R.drawable.img_stop_watch_black, null)
                }
                else -> {
                    ResourcesCompat.getDrawable(resources, R.drawable.img_timer_black_900, null)
                }
            }
        }.attach()

        return binding.root
    }
}