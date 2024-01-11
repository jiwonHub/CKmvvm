package com.example.ck_cmvvm.screen.main.home.wrong

import android.content.Intent
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ck_cmvvm.databinding.FragmentHomeWrongBinding
import com.example.ck_cmvvm.screen.base.BaseFragment
import com.example.ck_cmvvm.screen.main.home.wrong.detail.WrongDetailActivity
import com.example.ck_cmvvm.widget.adapter.WrongAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class WrongFragment: BaseFragment<WrongViewModel, FragmentHomeWrongBinding>() {

    private val adapter by lazy {
        WrongAdapter (
            onNumberFetched = { number ->
            viewModel.fetchPercent(number) },
            onClickedItem = { solutionEntity, percentData ->
                val intent = Intent(requireContext(), WrongDetailActivity::class.java)
                val percentModel = percentData[solutionEntity.number]
                percentModel?.let {
                    intent.putExtra("correctPer", it.correctCount)
                    intent.putExtra("wrongPer", it.wrongCount)
                }
                intent.putExtra("number", solutionEntity.number)
                intent.putExtra("title", solutionEntity.title)
                intent.putExtra("difficulty", solutionEntity.difficulty)
                intent.putExtra("explan", solutionEntity.explan)
                intent.putExtra("limit", solutionEntity.limit)
                intent.putExtra("content", solutionEntity.content)
                intent.putExtra("choice1", solutionEntity.choice1)
                intent.putExtra("choice2", solutionEntity.choice2)
                intent.putExtra("choice3", solutionEntity.choice3)
                intent.putExtra("choice4", solutionEntity.choice4)
                intent.putExtra("choice5", solutionEntity.choice5)
                intent.putExtra("correct", solutionEntity.correct)
                intent.putExtra("comment", solutionEntity.comment)
                intent.putExtra("userChoice", solutionEntity.userChoice)
                intent.putExtra("correctComment", solutionEntity.correctComment)
                startActivity(intent)
            }
        )
    }

    override val viewModel by viewModel<WrongViewModel>()

    override fun getViewBinding(): FragmentHomeWrongBinding = FragmentHomeWrongBinding.inflate(layoutInflater)

    override fun observeData() = viewModel.solutionWrong.observe(viewLifecycleOwner) {
        adapter.submitList(it)
        adapter.setClickable(true)
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