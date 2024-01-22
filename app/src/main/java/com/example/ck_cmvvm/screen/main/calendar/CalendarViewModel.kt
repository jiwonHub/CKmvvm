package com.example.ck_cmvvm.screen.main.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ck_cmvvm.data.entity.solution.SolutionEntity
import com.example.ck_cmvvm.data.repository.solution.SolutionRepository
import com.example.ck_cmvvm.screen.base.BaseViewModel
import kotlinx.coroutines.launch


class CalendarViewModel(
    private val solutionRepository: SolutionRepository
): BaseViewModel() {

    private val _solutionsForDay = MutableLiveData<List<SolutionEntity?>>()
    val solutionsForDay: LiveData<List<SolutionEntity?>> = _solutionsForDay

    fun fetchSolutionsForDay(dayStart: Long, dayEnd: Long) {
        viewModelScope.launch {
            val solutions = solutionRepository.getSolutionForDay(dayStart, dayEnd)
            _solutionsForDay.value = solutions
        }
    }

    fun deleteAllSolution() = viewModelScope.launch{
        solutionRepository.deleteAllSolution()
    }
}