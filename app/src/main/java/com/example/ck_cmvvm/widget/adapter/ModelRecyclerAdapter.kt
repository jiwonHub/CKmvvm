package com.example.ck_cmvvm.widget.adapter



import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.ck_cmvvm.CKApplication
import com.example.ck_cmvvm.model.DifficultyType
import com.example.ck_cmvvm.model.solution.PercentModel
import com.example.ck_cmvvm.model.solution.SolutionModel
import com.example.ck_cmvvm.screen.base.BaseViewModel
import com.example.ck_cmvvm.util.mapper.ModelViewHolderMapper
import com.example.ck_cmvvm.util.provider.DefaultResourceProvider
import com.example.ck_cmvvm.util.provider.ResourcesProvider
import com.example.ck_cmvvm.widget.adapter.viewholder.ModelViewHolder
import com.example.ck_cmvvm.widget.listener.solution.SolutionListListener

class ModelRecyclerAdapter<VM: BaseViewModel>(
    private var modelList: List<SolutionModel>,
    private var percentData: Map<String, PercentModel>,
    private val viewModel: VM,
    private val resourcesProvider: ResourcesProvider = DefaultResourceProvider(CKApplication.appContext!!),
    private val adapterListener: SolutionListListener
) : ListAdapter<SolutionModel, ModelViewHolder<SolutionModel>>(DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder<SolutionModel> {
        return ModelViewHolderMapper.map(
            parent,
            DifficultyType.values()[viewType],
            viewModel,
            resourcesProvider
        )
    }

    override fun getItemViewType(position: Int): Int {
        return when(modelList[position].difficulty){
            "쉬움" -> 0
            "보통" -> 1
            "어려움" -> 2
            "매우 어려움" -> 3
            else -> 4
        }
    }

    override fun onBindViewHolder(holder: ModelViewHolder<SolutionModel>, position: Int) {
        val model = getItem(position)
        val percentModel = percentData[model.number] ?: PercentModel(0,0)
        holder.bind(model, adapterListener, percentModel)
    }

    override fun submitList(list: List<SolutionModel>?) {
        list?.let { modelList = it }
        super.submitList(list)
    }

    fun setPercentData(data: Map<String, PercentModel>) {
        percentData = data
        notifyDataSetChanged()
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SolutionModel>() {
            override fun areItemsTheSame(oldItem: SolutionModel, newItem: SolutionModel): Boolean {
                return oldItem.number == newItem.number
            }

            override fun areContentsTheSame(oldItem: SolutionModel, newItem: SolutionModel): Boolean {
                return oldItem == newItem
            }
        }
    }

}