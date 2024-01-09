package com.example.ck_cmvvm

import android.app.Application
import android.content.Context
import com.example.ck_cmvvm.di.appModule
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class CKApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this@CKApplication

        FirebaseApp.initializeApp(this)

        startKoin{
            androidLogger(Level.ERROR)
            androidContext(this@CKApplication)
            modules(appModule)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        appContext = null
    }

    companion object{
        var appContext: Context? = null
            private set
    }
}