package com.example.ck_cmvvm.widget.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ck_cmvvm.R
import com.example.ck_cmvvm.data.entity.solution.SolutionEntity
import com.example.ck_cmvvm.databinding.ViewholderCalendarBinding

class CalendarAdapter: ListAdapter<SolutionEntity, CalendarAdapter.ViewHolder>(DiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarAdapter.ViewHolder {
        val binding = ViewholderCalendarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarAdapter.ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ViewHolder(private val binding: ViewholderCalendarBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: SolutionEntity){
            binding.title.text = item.title
            binding.difficulty.text = item.difficulty
            if (item.isCorrect){
                Glide.with(binding.isCorrect)
                    .load(R.drawable.ic_true)
                    .into(binding.isCorrect)
            }else{
                Glide.with(binding.isCorrect)
                    .load(R.drawable.ic_false)
                    .into(binding.isCorrect)
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
}