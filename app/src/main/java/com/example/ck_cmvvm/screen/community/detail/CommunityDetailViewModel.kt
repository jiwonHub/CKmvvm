package com.example.ck_cmvvm.screen.community.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ck_cmvvm.data.DBKey.Companion.DB_CHAT
import com.example.ck_cmvvm.model.community.ChatModel
import com.example.ck_cmvvm.screen.base.BaseViewModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class CommunityDetailViewModel: BaseViewModel() {

    private val _chatState = MutableLiveData<List<ChatModel>>()
    val chatState: LiveData<List<ChatModel>> = _chatState

    fun fetchChat(key: String){
        val chatDB = FirebaseDatabase.getInstance().reference.child(DB_CHAT).child("chat").child(key)

        chatDB.addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatItem = snapshot.getValue(ChatModel::class.java)
                chatItem?:return

                val chatList = mutableListOf<ChatModel>()
                chatList.add(chatItem)

                _chatState.value = chatList
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun uploadChat(name: String, chat:String, timeChat: Long, key: String){
        val chatDB = FirebaseDatabase.getInstance().reference.child(DB_CHAT).child("chat").child(key)
        val chatItem = ChatModel(
            name = name,
            chat = chat,
            time = timeChat
        )
        chatDB.push().setValue(chatItem)
    }

}