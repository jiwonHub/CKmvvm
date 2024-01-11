package com.example.ck_cmvvm.data.network

import com.example.ck_cmvvm.data.dto.EasyDTO
import retrofit2.Call
import retrofit2.http.GET

interface SolutionService {

    @GET("/v3/414b7be7-d957-4ad8-b27f-fb0ad07f19d4")
    fun listQuestions(): Call<EasyDTO>

}