package com.example.ck_cmvvm.screen.login

sealed class LoginState {
    data class Success(val userId: Long, val userName: String?, val userImage: String?) : LoginState()
    data class Error(val error: Throwable) : LoginState()
    object Cancelled : LoginState()
    object Uninitialized : LoginState()
}