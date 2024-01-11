package com.example.ck_cmvvm.widget.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ck_cmvvm.R
import com.example.ck_cmvvm.databinding.ViewholderRankBinding
import com.example.ck_cmvvm.model.rank.RankModel

class RankAdapter: ListAdapter<RankModel, RankAdapter.ViewHolder>(DiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankAdapter.ViewHolder {
        val binding = ViewholderRankBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RankAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ViewholderRankBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: RankModel) = with(binding){
            rankNumber.text = item.rank.toString()
            rankName.text = item.userName
            rankScore.text = item.rankPoint.toString()
            val color = when (item.rank) {
                1 -> R.drawable.circle_round_orange
                2 -> R.drawable.circle_round_purple
                3 -> R.drawable.circle_round_blue
                4 -> R.drawable.circle_round_gray
                else -> R.drawable.circle_round_transprants
            }
            rankNumber.setBackgroundResource(color)
        }
    }

    companion object{
        val DiffUtil = object : DiffUtil.ItemCallback<RankModel>(){
            override fun areItemsTheSame(
                oldItem: RankModel,
                newItem: RankModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: RankModel,
                newItem: RankModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}