package com.example.ck_cmvvm.model.rank

data class RankModel(
    val userName: String,
    val rankPoint: Int,
    var rank: Int = 0
)