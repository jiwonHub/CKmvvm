package com.example.ck_cmvvm.di

import com.example.ck_cmvvm.data.repository.SharedPreferencesRepository
import com.example.ck_cmvvm.data.repository.solution.DefaultSolutionRepository
import com.example.ck_cmvvm.data.repository.solution.SolutionRepository
import com.example.ck_cmvvm.screen.community.CommunityViewModel
import com.example.ck_cmvvm.screen.community.create.CreateCommunityViewModel
import com.example.ck_cmvvm.screen.community.detail.CommunityDetailViewModel
import com.example.ck_cmvvm.screen.login.LoginViewModel
import com.example.ck_cmvvm.screen.main.calendar.CalendarViewModel
import com.example.ck_cmvvm.screen.main.home.HomeViewModel
import com.example.ck_cmvvm.screen.main.home.question.QuestionViewModel
import com.example.ck_cmvvm.screen.main.home.question.compilation.QuestionCompilationViewModel
import com.example.ck_cmvvm.screen.main.home.question.rank.RankViewModel
import com.example.ck_cmvvm.screen.main.home.question.result.ResultViewModel
import com.example.ck_cmvvm.screen.main.home.question.solving.SolvingViewModel
import com.example.ck_cmvvm.screen.main.home.wrong.WrongViewModel
import com.example.ck_cmvvm.screen.main.home.wrong.detail.WrongDetailViewModel
import com.example.ck_cmvvm.screen.main.summary.SummaryViewModel
import com.example.ck_cmvvm.util.provider.DefaultResourceProvider
import com.example.ck_cmvvm.util.provider.ResourcesProvider
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { LoginViewModel(get()) }
    viewModel { HomeViewModel() }
    viewModel { CalendarViewModel(get()) }
    viewModel { SummaryViewModel() }
    viewModel { QuestionViewModel() }
    viewModel { WrongViewModel(get()) }
    viewModel { QuestionCompilationViewModel() }
    viewModel { SolvingViewModel() }
    viewModel { ResultViewModel(get()) }
    viewModel { CommunityViewModel() }
    viewModel { CreateCommunityViewModel() }
    viewModel { CommunityDetailViewModel() }
    viewModel { WrongDetailViewModel() }
    viewModel { RankViewModel() }

    single<SolutionRepository> { DefaultSolutionRepository(get(), get()) }

    single<ResourcesProvider> { DefaultResourceProvider(androidApplication()) }

    single { SharedPreferencesRepository(androidContext()) }

    single { provideDB(androidApplication()) }
    single { provideSolutionDao(get()) }

    single { Dispatchers.IO }
    single { Dispatchers.Main }

}