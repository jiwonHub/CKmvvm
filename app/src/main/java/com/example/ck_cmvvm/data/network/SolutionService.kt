package com.example.ck_cmvvm.data.network

import com.example.ck_cmvvm.data.dto.EasyDTO
import retrofit2.Call
import retrofit2.http.GET

interface SolutionService {

    @GET("/v3/7a53e139-f1d9-4699-b7cd-cbe8b76499d5")
    fun listQuestions(): Call<EasyDTO>

}