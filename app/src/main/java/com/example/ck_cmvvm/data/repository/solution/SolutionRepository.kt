package com.example.ck_cmvvm.data.repository.solution

import com.example.ck_cmvvm.data.entity.solution.SolutionEntity

interface SolutionRepository {

    suspend fun getAllSolution(): List<SolutionEntity>

    suspend fun insertSolution(solutionEntity: SolutionEntity)

    suspend fun getSolutionForDay(dayStart: Long, dayEnd: Long): List<SolutionEntity>

    suspend fun getSolutionWrong(): List<SolutionEntity>

    suspend fun deleteAllSolution(dayStart: Long, dayEnd: Long)

}