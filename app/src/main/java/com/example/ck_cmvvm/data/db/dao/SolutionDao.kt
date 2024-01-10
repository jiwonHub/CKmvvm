package com.example.ck_cmvvm.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ck_cmvvm.data.entity.solution.SolutionEntity

@Dao
interface SolutionDao {

    @Query("SELECT * FROM SolutionEntity")
    suspend fun getAll(): List<SolutionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(SolutionEntity: SolutionEntity)

    @Query("SELECT * FROM SolutionEntity WHERE time BETWEEN :dayStart AND :dayEnd")
    suspend fun getSolutionForDay(dayStart: Long, dayEnd: Long): List<SolutionEntity>

    @Query("SELECT * FROM SolutionEntity WHERE isCorrect = 0")
    suspend fun getSolutionWrong(): List<SolutionEntity>

}