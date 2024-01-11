package com.example.ck_cmvvm.util.mapper

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.ck_cmvvm.databinding.ViewholderQuestionEasyBinding
import com.example.ck_cmvvm.databinding.ViewholderQuestionHardBinding
import com.example.ck_cmvvm.databinding.ViewholderQuestionNormalBinding
import com.example.ck_cmvvm.databinding.ViewholderQuestionVeryHardBinding
import com.example.ck_cmvvm.model.DifficultyType
import com.example.ck_cmvvm.model.solution.SolutionModel
import com.example.ck_cmvvm.screen.base.BaseViewModel
import com.example.ck_cmvvm.util.provider.ResourcesProvider
import com.example.ck_cmvvm.widget.adapter.viewholder.solution.EasyViewHolder
import com.example.ck_cmvvm.widget.adapter.viewholder.ModelViewHolder
import com.example.ck_cmvvm.widget.adapter.viewholder.solution.HardViewHolder
import com.example.ck_cmvvm.widget.adapter.viewholder.solution.NormalViewHolder
import com.example.ck_cmvvm.widget.adapter.viewholder.solution.VeryHardViewHolder

object ModelViewHolderMapper {

    fun map(
        parent: ViewGroup,
        type: DifficultyType,
        viewModel: BaseViewModel,
        resourcesProvider: ResourcesProvider
    ): ModelViewHolder<SolutionModel> {
        val inflater = LayoutInflater.from(parent.context)
        val viewHolder = when(type){
            DifficultyType.EASY_DIFFICULTY -> EasyViewHolder(
                ViewholderQuestionEasyBinding.inflate(inflater, parent, false),
                viewModel,
                resourcesProvider
            )
            DifficultyType.NORMAL_DIFFICULTY -> NormalViewHolder(
                ViewholderQuestionNormalBinding.inflate(inflater, parent, false),
                viewModel,
                resourcesProvider
            )
            DifficultyType.HARD_DIFFICULTY -> HardViewHolder(
                ViewholderQuestionHardBinding.inflate(inflater, parent, false),
                viewModel,
                resourcesProvider
            )
            DifficultyType.HELL_DIFFICULTY -> VeryHardViewHolder(
                ViewholderQuestionVeryHardBinding.inflate(inflater, parent, false),
                viewModel,
                resourcesProvider
            )
        }
        return viewHolder
    }

}