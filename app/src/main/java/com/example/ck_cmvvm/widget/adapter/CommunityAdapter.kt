package com.example.ck_cmvvm.widget.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ck_cmvvm.databinding.ViewholderCommunityBinding
import com.example.ck_cmvvm.model.community.CommunityModel

class CommunityAdapter(val onItemClicked: (CommunityModel) -> Unit):
    ListAdapter<CommunityModel, CommunityAdapter.ViewHolder>(DiffUtil){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityAdapter.ViewHolder {
        val binding = ViewholderCommunityBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommunityAdapter.ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ViewHolder(private val binding: ViewholderCommunityBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: CommunityModel){
            binding.communityTitle.text = item.title
            binding.communityContent.text = item.content
            binding.root.setOnClickListener {
                onItemClicked(item)
            }
            binding.communityUser.text = item.name
        }

    }

    companion object{
        val DiffUtil = object : DiffUtil.ItemCallback<CommunityModel>(){
            override fun areItemsTheSame(
                oldItem: CommunityModel,
                newItem: CommunityModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: CommunityModel,
                newItem: CommunityModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}