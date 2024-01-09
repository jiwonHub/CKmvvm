package com.example.ck_cmvvm.screen.main.summary

import com.example.ck_cmvvm.databinding.FragmentSummaryBinding
import com.example.ck_cmvvm.screen.base.BaseFragment
import com.example.ck_cmvvm.screen.main.calendar.CalendarFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SummaryFragment: BaseFragment<SummaryViewModel, FragmentSummaryBinding>() {

    override val viewModel by viewModel<SummaryViewModel>()

    override fun getViewBinding(): FragmentSummaryBinding = FragmentSummaryBinding.inflate(layoutInflater)

    override fun observeData() {

    }

    companion object{

        fun newInstance() = SummaryFragment()

        const val TAG = "SummaryFragment"

    }
}