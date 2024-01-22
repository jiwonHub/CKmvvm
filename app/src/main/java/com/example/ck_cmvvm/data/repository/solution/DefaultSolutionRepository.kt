package com.example.ck_cmvvm.data.repository.solution

import com.example.ck_cmvvm.data.db.dao.SolutionDao
import com.example.ck_cmvvm.data.entity.solution.SolutionEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefaultSolutionRepository(
    private val solutionDao: SolutionDao,
    private val ioDispatcher: CoroutineDispatcher
): SolutionRepository{
    override suspend fun getAllSolution() : List<SolutionEntity> = withContext(ioDispatcher){
        solutionDao.getAll()
    }

    override suspend fun insertSolution(solutionEntity: SolutionEntity) = withContext(ioDispatcher) {
        solutionDao.insert(solutionEntity)
    }

    override suspend fun getSolutionForDay(dayStart: Long, dayEnd: Long): List<SolutionEntity> = withContext(ioDispatcher) {
        solutionDao.getSolutionForDay(dayStart, dayEnd)
    }

    override suspend fun getSolutionWrong(): List<SolutionEntity> = withContext(ioDispatcher){
        solutionDao.getSolutionWrong()
    }

    override suspend fun deleteAllSolution() = withContext(ioDispatcher) {
        solutionDao.deleteAllSolution()
    }
}