package com.example.ck_cmvvm.screen.main.home.question.rank

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ck_cmvvm.databinding.ActivityRankBinding
import com.example.ck_cmvvm.screen.base.BaseActivity
import com.example.ck_cmvvm.widget.adapter.RankAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class RankActivity: BaseActivity<RankViewModel, ActivityRankBinding>() {

    private val adapter = RankAdapter()

    override val viewModel by viewModel<RankViewModel>()

    override fun getViewBinding(): ActivityRankBinding = ActivityRankBinding.inflate(layoutInflater)

    override fun observeData() = viewModel.rankData.observe(this) {
        adapter.submitList(it)
    }

    override fun initViews() = with(binding) {
        super.initViews()

        rankRecyclerView.layoutManager = LinearLayoutManager(this@RankActivity)
        rankRecyclerView.adapter = adapter

        viewModel.fetchRankData()
    }
}