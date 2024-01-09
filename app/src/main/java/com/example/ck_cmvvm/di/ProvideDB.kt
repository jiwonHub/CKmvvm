package com.example.ck_cmvvm.di

import android.content.Context
import androidx.room.Room
import com.example.ck_cmvvm.data.db.ApplicationDatabase

fun provideDB(context: Context): ApplicationDatabase =
        Room.databaseBuilder(context, ApplicationDatabase::class.java, ApplicationDatabase.DB_NAME)
            .build()

fun provideSolutionDao(database: ApplicationDatabase) = database.SolutionDao()