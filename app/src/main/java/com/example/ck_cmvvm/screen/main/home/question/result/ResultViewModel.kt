package com.example.ck_cmvvm.screen.main.home.question.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ck_cmvvm.data.DBKey.Companion.DB_PER
import com.example.ck_cmvvm.data.DBKey.Companion.DB_RANK
import com.example.ck_cmvvm.data.repository.SharedPreferencesRepository
import com.example.ck_cmvvm.screen.base.BaseViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Job

class ResultViewModel: BaseViewModel() {

    private val _userScore = MutableLiveData<Int>()
    val userScore: LiveData<Int> = _userScore

    private val _scoreChange = MutableLiveData<Int>()
    val scoreChange: LiveData<Int> = _scoreChange

    fun fetchAndUpdateScore(userId: String, userName: String, isCorrect: Boolean, difficulty: String) {
        val rankDB = FirebaseDatabase.getInstance().reference.child(DB_RANK).child(userId)

        rankDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val currentScore = snapshot.child("rankPoint").getValue(Int::class.java) ?: 1000
                val newScore = calculateNewScore(currentScore, isCorrect, difficulty)

                // 점수 변화량 계산
                val change = newScore - currentScore

                _userScore.value = newScore
                _scoreChange.value = change

                // 파이어베이스에 점수 저장
                rankDB.child("rankPoint").setValue(newScore)
                rankDB.child("userName").setValue(userName)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })
    }

    fun fetchAndUpdatePercent(number: String, isCorrect: Boolean){
        val percentDB = FirebaseDatabase.getInstance().reference.child(DB_PER).child(number)

        percentDB.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val correctPer = snapshot.child("correctPer").getValue(Int::class.java)?:0
                val wrongPer = snapshot.child("wrongPer").getValue(Int::class.java)?:0

                if (isCorrect){
                    percentDB.child("correctPer").setValue(correctPer+1)
                }else{
                    percentDB.child("wrongPer").setValue(wrongPer+1)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun calculateNewScore(currentScore: Int, isCorrect: Boolean, difficulty: String): Int {
        return if (isCorrect) {
            when (difficulty) {
                "쉬움" -> currentScore + 10
                "보통" -> currentScore + 15
                else -> currentScore
            }
        } else {
            when (difficulty) {
                "쉬움" -> currentScore - 5
                "보통" -> currentScore - 5
                else -> currentScore
            }
        }
    }
}