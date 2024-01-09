package com.example.ck_cmvvm.screen.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import com.example.ck_cmvvm.screen.main.MainActivity
import com.example.ck_cmvvm.R
import com.example.ck_cmvvm.databinding.ActivityLoginBinding
import com.example.ck_cmvvm.screen.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>() {

    override val viewModel: LoginViewModel by viewModel()

    override fun getViewBinding(): ActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)

    override fun initViews() {
        super.initViews()
        viewModel.initKakaoSdk(this, getString(R.string.kakao_native_app_key))
        binding.loginButton.setOnClickListener {
            Log.d("login", "login")
            viewModel.login(this)
        }
    }

    override fun observeData() {
        viewModel.loginState.observe(this) { state ->
            when (state) {
                is LoginState.Success -> navigateToMainActivity(state.userId, state.userName, state.userImage)
                is LoginState.Error -> Log.e(TAG, "로그인 실패", state.error)
                LoginState.Cancelled -> Log.i(TAG, "로그인 취소됨")
                else -> Unit
            }
        }
    }

    private fun navigateToMainActivity(userId: Long, userName: String?, userImage: String?) {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}