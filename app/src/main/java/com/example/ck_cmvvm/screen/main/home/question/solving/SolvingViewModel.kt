package com.example.ck_cmvvm.screen.main.home.question.solving

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ck_cmvvm.data.DBKey
import com.example.ck_cmvvm.model.solution.PercentModel
import com.example.ck_cmvvm.screen.base.BaseViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SolvingViewModel: BaseViewModel() {

    private val _questionPercents = MutableLiveData<PercentModel>()
    val questionPercents: LiveData<PercentModel> = _questionPercents

    fun fetchPercent(number: String){
        val percentDB = FirebaseDatabase.getInstance().reference.child(DBKey.DB_PER).child(number)

        percentDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val correctPer = snapshot.child("correctPer").getValue(Int::class.java)?:0
                val wrongPer = snapshot.child("wrongPer").getValue(Int::class.java)?:0

                _questionPercents.value = PercentModel(correctPer, wrongPer)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

}