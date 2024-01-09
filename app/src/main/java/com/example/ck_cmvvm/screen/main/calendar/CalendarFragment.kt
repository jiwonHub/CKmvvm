package com.example.ck_cmvvm.screen.main.calendar

import com.example.ck_cmvvm.databinding.FragmentCalendarBinding
import com.example.ck_cmvvm.screen.base.BaseFragment
import com.example.ck_cmvvm.screen.main.home.HomeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CalendarFragment: BaseFragment<CalendarViewModel, FragmentCalendarBinding>() {

    override val viewModel by viewModel<CalendarViewModel>()

    override fun getViewBinding(): FragmentCalendarBinding = FragmentCalendarBinding.inflate(layoutInflater)

    override fun observeData() {

    }

    companion object{

        fun newInstance() = CalendarFragment()

        const val TAG = "CalendarFragment"

    }
}