package com.example.ck_cmvvm.screen.main.home.wrong

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ck_cmvvm.data.DBKey
import com.example.ck_cmvvm.data.entity.solution.SolutionEntity
import com.example.ck_cmvvm.data.repository.solution.SolutionRepository
import com.example.ck_cmvvm.model.solution.PercentModel
import com.example.ck_cmvvm.screen.base.BaseViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

class WrongViewModel(
    private val solutionRepository: SolutionRepository
): BaseViewModel() {

    private val _solutionWrong = MutableLiveData<List<SolutionEntity>>()
    val solutionWrong: LiveData<List<SolutionEntity>> = _solutionWrong

    private val _questionPercents = MutableLiveData<Map<String, PercentModel>>()
    val questionPercents: LiveData<Map<String, PercentModel>> = _questionPercents

    fun fetchSolutionWrong() {
        viewModelScope.launch {
            val solutions = solutionRepository.getSolutionWrong()
            _solutionWrong.value = solutions
        }
    }

    fun fetchPercent(number: String){
        val percentDB = FirebaseDatabase.getInstance().reference.child(DBKey.DB_PER).child(number)

        percentDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val correctPer = snapshot.child("correctPer").getValue(Int::class.java)?:0
                val wrongPer = snapshot.child("wrongPer").getValue(Int::class.java)?:0

                _questionPercents.value = _questionPercents.value?.toMutableMap()?.apply {
                    put(number, PercentModel(correctPer, wrongPer))
                } ?: mapOf(number to PercentModel(correctPer, wrongPer))
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

}