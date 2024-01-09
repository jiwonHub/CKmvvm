package com.example.ck_cmvvm.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.ck_cmvvm.data.entity.user.User

class SharedPreferencesRepository(private val context: Context) {

    companion object {
        private const val PREFS_NAME = "KakaoLoginPrefs"
        private const val KEY_USER_ID = "userId"
        private const val KEY_USER_NAME = "userName"
        private const val KEY_USER_IMAGE = "userImage"
    }

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveUserInfo(userId: Long, userName: String?, userImage: String?) {
        sharedPreferences.edit().apply {
            putLong(KEY_USER_ID, userId)
            putString(KEY_USER_NAME, userName)
            putString(KEY_USER_IMAGE, userImage)
            apply()
        }
    }

    fun getUserInfo(): User {
        val userId = sharedPreferences.getLong(KEY_USER_ID, -1)
        val userName = sharedPreferences.getString(KEY_USER_NAME, null)
        val userImage = sharedPreferences.getString(KEY_USER_IMAGE, null)
        return User(userId, userName, userImage)
    }

}