package com.example.ck_cmvvm.screen.main.home.question.compilation

import android.content.Intent
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ck_cmvvm.databinding.ActivityEasyQuestionCompilationBinding
import com.example.ck_cmvvm.model.solution.SolutionModel
import com.example.ck_cmvvm.screen.base.BaseActivity
import com.example.ck_cmvvm.screen.main.home.question.solving.SolvingActivity
import com.example.ck_cmvvm.util.provider.ResourcesProvider
import com.example.ck_cmvvm.widget.adapter.ModelRecyclerAdapter
import com.example.ck_cmvvm.widget.listener.solution.SolutionListListener
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuestionCompilationActivity: BaseActivity<QuestionCompilationViewModel, ActivityEasyQuestionCompilationBinding>() {

    override val viewModel by viewModel<QuestionCompilationViewModel>()

    override fun getViewBinding(): ActivityEasyQuestionCompilationBinding = ActivityEasyQuestionCompilationBinding.inflate(layoutInflater)

    private val resourcesProvider by inject<ResourcesProvider>()

    private val adapter by lazy {
        ModelRecyclerAdapter<QuestionCompilationViewModel>(
            listOf(),
            mapOf(),
            viewModel,
            resourcesProvider,
            adapterListener = object : SolutionListListener{
                override fun onClickItem(model: SolutionModel) {
                    val intent = Intent(this@QuestionCompilationActivity, SolvingActivity::class.java)
                    val questionModelFields = listOf(
                        model.number,
                        model.title,
                        model.difficulty,
                        model.explan,
                        model.limit,
                        model.content,
                        model.choice1,
                        model.choice2,
                        model.choice3,
                        model.choice4,
                        model.choice5,
                        model.correct,
                        model.comment,
                        model.correctComment
                    )
                    intent.putStringArrayListExtra("questionModelFields", ArrayList(questionModelFields))
                    startActivity(intent)
                }
            }
        )
    }

    override fun observeData() {
        viewModel.easyQuestionLiveData.observe(this, Observer { questions ->
            adapter.submitList(questions)
            viewModel.fetchData()
            Log.d("logggg", questions.toString())
            questions.forEach { question ->
                viewModel.fetchPercent(question.number)
            }
        })
        viewModel.questionPercents.observe(this, Observer { percents ->
            adapter.setPercentData(percents)
        })
    }

    override fun initViews() = with(binding) {
        super.initViews()

        val selectedDifficulty = intent.getStringExtra("difficulty")
        viewModel.setSelectedDifficulty(selectedDifficulty!!)
        viewModel.fetchData()

        easyCompilationRecyclerView.layoutManager = LinearLayoutManager(this@QuestionCompilationActivity)
        easyCompilationRecyclerView.adapter = adapter

        when(selectedDifficulty){
            "쉬움" -> {compilationTitle.text = "쉬움 문제 모음"}
            "보통" -> {compilationTitle.text = "보통 문제 모음"}
            "어려움" -> {compilationTitle.text = "어려움 문제 모음"}
            "매우 어려움" -> {compilationTitle.text = "매우 어려움 문제 모음"}
        }

        backButton.setOnClickListener {
            finish()
        }

    }

}