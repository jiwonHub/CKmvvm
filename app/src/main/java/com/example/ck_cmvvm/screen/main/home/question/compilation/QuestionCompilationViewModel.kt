package com.example.ck_cmvvm.screen.main.home.question.compilation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ck_cmvvm.data.DBKey
import com.example.ck_cmvvm.data.dto.EasyDTO
import com.example.ck_cmvvm.data.network.SolutionService
import com.example.ck_cmvvm.model.solution.PercentModel
import com.example.ck_cmvvm.model.solution.SolutionModel
import com.example.ck_cmvvm.screen.base.BaseViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuestionCompilationViewModel : BaseViewModel() {

    val easyQuestionLiveData = MutableLiveData<List<SolutionModel>>()

    private val _questionPercents = MutableLiveData<Map<String, PercentModel>>()
    val questionPercents: LiveData<Map<String, PercentModel>> = _questionPercents

    private var selectedDifficulty: String = ""

    fun setSelectedDifficulty(difficulty: String) {
        selectedDifficulty = difficulty
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

    override fun fetchData(): Job = viewModelScope.launch {
        getQuestion()
    }

    private fun getQuestion(){
        Retrofit.Builder()
            .baseUrl("https://run.mocky.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SolutionService::class.java)
            .listQuestions()
            .enqueue(object : Callback<EasyDTO>{
                override fun onResponse(call: Call<EasyDTO>, response: Response<EasyDTO>) {
                    if (response.isSuccessful.not()) {
                        return
                    }
                    response.body()?.let { DTO ->
                        val filteredQuestions = DTO.question.filter {
                            it.difficulty == selectedDifficulty
                        }
                        easyQuestionLiveData.value = filteredQuestions
                    }
                }

                override fun onFailure(call: Call<EasyDTO>, t: Throwable) {

                }

            })
    }
}