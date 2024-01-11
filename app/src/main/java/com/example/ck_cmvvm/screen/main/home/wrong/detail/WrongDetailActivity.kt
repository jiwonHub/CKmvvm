package com.example.ck_cmvvm.screen.main.home.wrong.detail

import android.util.Log
import com.example.ck_cmvvm.R
import com.example.ck_cmvvm.databinding.ActivityWrongDetailBinding
import com.example.ck_cmvvm.screen.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class WrongDetailActivity: BaseActivity<WrongDetailViewModel, ActivityWrongDetailBinding>() {

    override val viewModel by viewModel<WrongDetailViewModel>()

    override fun getViewBinding(): ActivityWrongDetailBinding = ActivityWrongDetailBinding.inflate(layoutInflater)

    override fun observeData() {

    }

    override fun initViews() = with(binding){
        super.initViews()

        val number = intent.getStringExtra("number")
        val title = intent.getStringExtra("title")
        val difficulty = intent.getStringExtra("difficulty")
        val explan = intent.getStringExtra("explan")
        val limit = intent.getStringExtra("limit")
        val content = intent.getStringExtra("content")
        val _choice1 = intent.getStringExtra("choice1")
        val _choice2 = intent.getStringExtra("choice2")
        val _choice3 = intent.getStringExtra("choice3")
        val _choice4 = intent.getStringExtra("choice4")
        val _choice5 = intent.getStringExtra("choice5")
        val correct = intent.getStringExtra("correct")
        val _comment = intent.getStringExtra("comment")
        val userChoice = intent.getStringExtra("userChoice")
        val _correctComment = intent.getStringExtra("correctComment")
        val correctPer = intent.getIntExtra("correctPer", 0)
        val wrongPer = intent.getIntExtra("wrongPer", 0)

        val totalValue = correctPer + wrongPer
        val percent: Double = if (correctPer != 0){
            (correctPer.toDouble() / totalValue) * 100.0
        } else {
            0.0
        }
        Log.d("total", percent.toString())

        setColor(correct!!, userChoice!!)

        questionNumber.text = number
        answerPercentage.text = "$percent%"
        SolutionTitle.text = title
        SolutionDifficulty.text = difficulty
        explanationTextView.text = explan
        LimitTextView.text = limit
        ContentTitle.text = content
        choice1.text = _choice1
        choice2.text = _choice2
        choice3.text = _choice3
        choice4.text = _choice4
        choice5.text = _choice5
        comment.text = _comment
        correctComment.text = _correctComment

        backButton.setOnClickListener {
            finish()
        }
    }

    private fun setColor(correct: String, selected: String) = with(binding){
        val choicesMap = mapOf(
            "choice1" to choice1,
            "choice2" to choice2,
            "choice3" to choice3,
            "choice4" to choice4,
            "choice5" to choice5
        )

        choicesMap[correct]?.setBackgroundResource(R.drawable.round_fill_green)

        if (selected != correct) {
            choicesMap[selected]?.setBackgroundResource(R.drawable.round_fill_red)
        }
    }
}