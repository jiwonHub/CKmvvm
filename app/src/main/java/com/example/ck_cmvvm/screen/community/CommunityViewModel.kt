package com.example.ck_cmvvm.screen.community

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ck_cmvvm.data.DBKey.Companion.DB_COMMUNITY
import com.example.ck_cmvvm.model.community.CommunityModel
import com.example.ck_cmvvm.screen.base.BaseViewModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class CommunityViewModel: BaseViewModel() {

    private val _communityState = MutableLiveData<List<CommunityModel?>>()
    val communityState: LiveData<List<CommunityModel?>> = _communityState

    fun fetchCommunity(){
        val communityDB = FirebaseDatabase.getInstance().reference.child(DB_COMMUNITY)
        val communityList = mutableListOf<CommunityModel>()

        communityDB.addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val communityModel = snapshot.getValue(CommunityModel::class.java)

                communityModel ?: return
                communityList.add(communityModel)
                _communityState.value = communityList
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

}