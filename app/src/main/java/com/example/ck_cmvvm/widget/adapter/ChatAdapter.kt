package com.example.ck_cmvvm.widget.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ck_cmvvm.databinding.ViewholderChatBinding
import com.example.ck_cmvvm.model.community.ChatModel
import java.text.SimpleDateFormat

class ChatAdapter: ListAdapter<ChatModel, ChatAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAdapter.ViewHolder {
        return ViewHolder(ViewholderChatBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ChatAdapter.ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ViewHolder(private val binding : ViewholderChatBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SimpleDateFormat")
        fun bind(chatItem: ChatModel){
            val postDate = SimpleDateFormat("MM/dd")
            val postTime = SimpleDateFormat("hh:mm")

            binding.chatName.text = chatItem.name
            binding.chatContent.text = chatItem.chat
            binding.chatDate.text = postDate.format(chatItem.time)
            binding.chatTime.text = postTime.format(chatItem.time)
        }
    }

    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<ChatModel>(){
            override fun areItemsTheSame(oldItem: ChatModel, newItem: ChatModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ChatModel, newItem: ChatModel): Boolean {
                return oldItem == newItem
            }

        }
    }

}