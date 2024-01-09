package com.example.ck_cmvvm.screen.main.home.question.solving

import android.content.Intent
import androidx.lifecycle.Observer
import com.example.ck_cmvvm.databinding.ActivityChoiceSolutionBinding
import com.example.ck_cmvvm.model.solution.SolutionModel
import com.example.ck_cmvvm.screen.base.BaseActivity
import com.example.ck_cmvvm.screen.main.home.question.result.ResultActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SolvingActivity: BaseActivity<SolvingViewModel, ActivityChoiceSolutionBinding>() {

    override val viewModel by viewModel<SolvingViewModel>()

    override fun getViewBinding(): ActivityChoiceSolutionBinding = ActivityChoiceSolutionBinding.inflate(layoutInflater)

    override fun observeData() = with(binding) {
        viewModel.questionPercents.observe(this@SolvingActivity, Observer { percents ->
            val correct = percents.correctCount
            val wrong = percents.wrongCount
            val total = correct+wrong
            val percent: Double = if (correct != 0){
                (correct.toDouble() / total) * 100.0
            } else {
                0.0
            }
            answerPercentage.text = "$percent%"
        })
    }

    override fun initViews() = with(binding) {
        super.initViews()


        val questionModelField = intent.getStringArrayListExtra("questionModelFields")
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
            viewModel.fetchPercent(questionModel.number)
            questionNumber.text = questionModel.number
            questionTitle.text = questionModel.title
            questionDifficulty.text = questionModel.difficulty
            explanationTextView.text = questionModel.explan
            limitTextView.text = questionModel.limit
            contentTitle.text = questionModel.content
            choice1.text = questionModel.choice1
            choice2.text = questionModel.choice2
            choice3.text = questionModel.choice3
            choice4.text = questionModel.choice4
            choice5.text = questionModel.choice5

            val choices = listOf(choice1, choice2, choice3, choice4, choice5)
            choices.forEachIndexed { index, button ->
                button.setOnClickListener {
                    val isCorrect = checkAnswer(questionModel.correct, "choice${index + 1}")
                    goToResultActivity(questionModel, isCorrect)
                }
            }
        }

    }

    private fun goToResultActivity(model: SolutionModel, isCorrect: Boolean){
        val intent = Intent(this@SolvingActivity, ResultActivity::class.java)
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
        intent.putExtra("isCorrect", isCorrect)
        startActivity(intent)
    }

    private fun checkAnswer(correctAnswer: String, userAnswer: String): Boolean {
        return correctAnswer == userAnswer
    }

}