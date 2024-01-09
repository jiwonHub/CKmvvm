package com.example.ck_cmvvm.screen.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ck_cmvvm.data.repository.SharedPreferencesRepository
import com.example.ck_cmvvm.screen.base.BaseViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.user.UserApiClient

class LoginViewModel(private val sharedPreferencesRepository: SharedPreferencesRepository): BaseViewModel() {

    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> = _loginState

    fun initKakaoSdk(context: Context, appKey: String) {
        KakaoSdk.init(context, appKey)
    }

    fun login(context: Context) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            loginWithKakaoTalk(context)
        } else {
            loginWithKakaoAccount(context)
        }
    }

    private fun loginWithKakaoTalk(context: Context) {
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            handleLoginResult(token, error)
        }
    }

    private fun loginWithKakaoAccount(context: Context) {
        UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
            handleLoginResult(token, error)
        }
    }

    private fun handleLoginResult(token: OAuthToken?, error: Throwable?) {
        if (error != null) {
            _loginState.value = LoginState.Error(error)
        } else if (token != null) {
            fetchUserInfo()
        }
    }

    private fun fetchUserInfo() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                _loginState.value = LoginState.Error(error)
            } else if (user != null) {
                val userId = user.id!!
                val userName = user.kakaoAccount?.profile?.nickname
                val userImage = user.kakaoAccount?.profile?.thumbnailImageUrl

                sharedPreferencesRepository.saveUserInfo(userId, userName, userImage)

                _loginState.value = LoginState.Success(userId, userName, userImage)
            }
        }
    }

}