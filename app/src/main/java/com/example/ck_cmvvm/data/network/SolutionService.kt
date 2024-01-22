package com.example.ck_cmvvm.data.network

import com.example.ck_cmvvm.data.dto.EasyDTO
import retrofit2.Call
import retrofit2.http.GET

interface SolutionService {

    @GET("/v3/c4ce6bc1-e713-4aa4-96fd-bce579fcacbd")
    fun listQuestions(): Call<EasyDTO>

}