package com.example.ck_cmvvm.widget.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.ck_cmvvm.model.solution.PercentModel
import com.example.ck_cmvvm.model.solution.SolutionModel
import com.example.ck_cmvvm.screen.base.BaseViewModel
import com.example.ck_cmvvm.util.provider.ResourcesProvider
import com.example.ck_cmvvm.widget.listener.solution.SolutionListListener

abstract class ModelViewHolder<M: SolutionModel>( // 모든 뷰 홀더가 공통적으로 가져야 할 속성과 메서드를 정의한다.
    binding: ViewBinding,
    protected val viewModel: BaseViewModel,
    protected val resourcesProvider: ResourcesProvider
): RecyclerView.ViewHolder(binding.root) {

    abstract fun bind(model: M, listener: SolutionListListener, percentModel: PercentModel)

}