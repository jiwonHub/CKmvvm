package com.example.ck_cmvvm.screen.main.home.wrong

import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ck_cmvvm.databinding.FragmentHomeWrongBinding
import com.example.ck_cmvvm.screen.base.BaseFragment
import com.example.ck_cmvvm.widget.adapter.WrongAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class WrongFragment: BaseFragment<WrongViewModel, FragmentHomeWrongBinding>() {

    private val adapter by lazy {
        WrongAdapter { number ->
            viewModel.fetchPercent(number)
        }
    }

    override val viewModel by viewModel<WrongViewModel>()

    override fun getViewBinding(): FragmentHomeWrongBinding = FragmentHomeWrongBinding.inflate(layoutInflater)

    override fun observeData() = viewModel.solutionWrong.observe(viewLifecycleOwner) {
        adapter.submitList(it)

    }

    override fun initViews() = with(binding){
        super.initViews()

        wrongRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        wrongRecyclerView.adapter = adapter
        viewModel.questionPercents.observe(requireActivity(), Observer { percents ->
            adapter.setPercentData(percents)
        })

        viewModel.fetchSolutionWrong()

    }
}