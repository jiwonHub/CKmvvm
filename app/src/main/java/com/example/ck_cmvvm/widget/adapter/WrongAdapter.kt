package com.example.ck_cmvvm.widget.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ck_cmvvm.data.entity.solution.SolutionEntity
import com.example.ck_cmvvm.databinding.ViewholderWrongBinding
import com.example.ck_cmvvm.model.solution.PercentModel


class WrongAdapter(
    private val onNumberFetched: (String) -> Unit,
    private val onClickedItem: (SolutionEntity, Map<String, PercentModel>) -> Unit
): ListAdapter<SolutionEntity, WrongAdapter.ViewHolder>(DiffUtil) {

    private var percentData: Map<String, PercentModel>? = null
    private var isClick: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WrongAdapter.ViewHolder {
        val binding = ViewholderWrongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WrongAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        val percentModel = percentData?.get(item.number) ?: PercentModel(0,0)
        holder.bind(item, percentModel)
        onNumberFetched(item.number)
    }

    inner class ViewHolder(private val binding: ViewholderWrongBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: SolutionEntity, percentModel: PercentModel) = with(binding){
            title.text = item.title
            difficulty.text = item.difficulty
            val correct = percentModel.correctCount
            val wrong = percentModel.wrongCount
            val totalValue = correct + wrong
            val percent: Double = if (correct != 0){
                (correct.toDouble() / totalValue) * 100.0
            } else {
                0.0
            }
            correctPer.text = "$percent%"
            root.isClickable = isClick
            root.setOnClickListener {
                onClickedItem(item, percentData!!)
            }
        }
    }

    companion object{
        val DiffUtil = object : DiffUtil.ItemCallback<SolutionEntity>(){
            override fun areItemsTheSame(
                oldItem: SolutionEntity,
                newItem: SolutionEntity
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: SolutionEntity,
                newItem: SolutionEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun setPercentData(data: Map<String, PercentModel>) {
        percentData = data
        notifyDataSetChanged()
    }

    fun setClickable(clickable: Boolean){
        isClick = clickable
        notifyDataSetChanged()
    }
}