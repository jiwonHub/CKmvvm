package com.example.ck_cmvvm.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ck_cmvvm.data.db.dao.SolutionDao
import com.example.ck_cmvvm.data.entity.solution.SolutionEntity

@Database(
    entities = [SolutionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ApplicationDatabase: RoomDatabase() {

    companion object{
        const val DB_NAME = "ApplicationDataBase.db"
    }

    abstract fun SolutionDao(): SolutionDao

}