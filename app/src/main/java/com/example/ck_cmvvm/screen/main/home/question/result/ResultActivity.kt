package com.example.ck_cmvvm.screen.main.home.question.result

import android.content.Intent
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.ck_cmvvm.data.repository.SharedPreferencesRepository
import com.example.ck_cmvvm.databinding.ActivityResultBinding
import com.example.ck_cmvvm.model.solution.SolutionModel
import com.example.ck_cmvvm.screen.base.BaseActivity
import com.example.ck_cmvvm.screen.community.CommunityActivity
import com.example.ck_cmvvm.screen.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResultActivity: BaseActivity<ResultViewModel, ActivityResultBinding>() {

    private val sharedPreferencesRepository: SharedPreferencesRepository by lazy {
        SharedPreferencesRepository(this)
    }

    override val viewModel by viewModel<ResultViewModel>()

    override fun getViewBinding(): ActivityResultBinding = ActivityResultBinding.inflate(layoutInflater)

    override fun observeData() {
        viewModel.userScore.observe(this) { score ->
            binding.score.text = "점수: $score"
        }
        viewModel.scoreChange.observe(this) { change ->
            val changeText = if (change >= 0) "(+$change)" else "($change)"
            binding.scoreChange.text = changeText
        }
    }

    override fun initViews() = with(binding) {
        super.initViews()

        val userInfo = sharedPreferencesRepository.getUserInfo()

        val questionModelField = intent.getStringArrayListExtra("questionModelFields")
        val isCorrect = intent.getBooleanExtra("isCorrect", false)
        val userChoice = intent.getStringExtra("userChoice")
        val time = System.currentTimeMillis()

        if (isCorrect){
            konfettiView.isVisible = true
            resultTextView.text = "정답입니다!"
        }else{
            konfettiView.isGone = true
            resultTextView.text = "틀렸습니다 ㅜㅜ"
        }

        if (questionModelField != null){
            val questionModel = SolutionModel(
                number = questionModelField[0],
                title = questionModelField[1],
                difficulty = questionModelField[2],
                explan = questionModelField[3],
                limit = questionModelField[4],
                content = questionModelField[5],
                choice1 = questionModelField[6],
                choice2 = questionModelField[7],
                choice3 = questionModelField[8],
                choice4 = questionModelField[9],
                choice5 = questionModelField[10],
                correct = questionModelField[11],
                comment = questionModelField[12],
                correctComment = questionModelField[13]
            )

            difficulty.text = questionModel.difficulty
            questionTitle.text = questionModel.title
            viewModel.fetchAndUpdateScore(userInfo.userId.toString(), userInfo.userName.toString(), isCorrect, questionModel.difficulty)
            viewModel.fetchAndUpdatePercent(questionModel.number, isCorrect)
            viewModel.saveSolution(questionModel, userChoice!!, time, isCorrect)
        }

        communityButton.setOnClickListener {
            val intent = Intent(this@ResultActivity, CommunityActivity::class.java)
            startActivity(intent)
        }

        homeButton.setOnClickListener {
            val intent = Intent(this@ResultActivity, MainActivity::class.java)
            startActivity(intent)
        }

    }
}