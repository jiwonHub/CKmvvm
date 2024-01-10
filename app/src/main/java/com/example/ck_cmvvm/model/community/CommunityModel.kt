package com.example.ck_cmvvm.model.community

data class CommunityModel(
    val title: String,
    val content: String,
    val uri: String,
    val time: Long,
    val name: String
){
    constructor() : this("", "", "", 0, "")
}
