package com.example.ck_cmvvm.model.community

data class ChatModel (
    val name : String,
    val chat : String,
    val time : Long
){
    constructor() : this("", "", 0)
}