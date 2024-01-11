package com.example.ck_cmvvm.widget.adapter.viewholder.solution

import com.example.ck_cmvvm.databinding.ViewholderQuestionHardBinding
import com.example.ck_cmvvm.databinding.ViewholderQuestionNormalBinding
import com.example.ck_cmvvm.model.solution.PercentModel
import com.example.ck_cmvvm.model.solution.SolutionModel
import com.example.ck_cmvvm.screen.base.BaseViewModel
import com.example.ck_cmvvm.util.provider.ResourcesProvider
import com.example.ck_cmvvm.widget.adapter.viewholder.ModelViewHolder
import com.example.ck_cmvvm.widget.listener.solution.SolutionListListener

class HardViewHolder(
    private val binding: ViewholderQuestionHardBinding,
    viewModel: BaseViewModel,
    resourcesProvider: ResourcesProvider
): ModelViewHolder<SolutionModel>(binding, viewModel, resourcesProvider) {

    override fun bind(model: SolutionModel, listener: SolutionListListener, percentModel: PercentModel) = with(binding) {
        root.setOnClickListener {
            listener.onClickItem(model)
        }
        val correct = percentModel.correctCount
        val wrong = percentModel.wrongCount
        val totalValue = correct + wrong
        val percent: Double = if (correct != 0){
            (correct.toDouble() / totalValue) * 100.0
        } else {
            0.0
        }

        QuestionTitle.text = model.title
        questionPercent.text = "$percent%"
    }
}