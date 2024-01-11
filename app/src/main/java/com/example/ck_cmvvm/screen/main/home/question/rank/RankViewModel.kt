package com.example.ck_cmvvm.screen.main.home.question.rank

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ck_cmvvm.data.DBKey.Companion.DB_RANK
import com.example.ck_cmvvm.model.rank.RankModel
import com.example.ck_cmvvm.screen.base.BaseViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RankViewModel: BaseViewModel() {

    private val _rankData = MutableLiveData<List<RankModel>>()
    val rankData: LiveData<List<RankModel>> = _rankData

    val rankList = mutableListOf<RankModel>()

    fun fetchRankData(){
        val rankDB = FirebaseDatabase.getInstance().reference.child(DB_RANK)
        rankDB.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                rankList.clear()
                for (childSnapShot in snapshot.children){
                    val userName = childSnapShot.child("userName").getValue(String::class.java)
                    val rankPoint = childSnapShot.child("rankPoint").getValue(Int::class.java)

                    if (userName != null && rankPoint != null){
                        val rankItem = RankModel(userName, rankPoint)
                        rankList.add(rankItem)
                    }
                    rankList.sortByDescending { it.rankPoint }
                    updateRank()
                    _rankData.value = rankList
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun updateRank() {
        // 랭킹 순위를 업데이트
        for ((index, rankItem) in rankList.withIndex()) {
            rankItem.rank = index + 1
        }
    }

}