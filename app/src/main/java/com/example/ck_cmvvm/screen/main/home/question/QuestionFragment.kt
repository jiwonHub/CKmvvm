package com.example.ck_cmvvm.screen.main.home.question

import android.content.Intent
import com.bumptech.glide.Glide
import com.example.ck_cmvvm.R
import com.example.ck_cmvvm.data.repository.SharedPreferencesRepository
import com.example.ck_cmvvm.databinding.FragmentHomeQuestionBinding
import com.example.ck_cmvvm.screen.base.BaseFragment
import com.example.ck_cmvvm.screen.community.CommunityActivity
import com.example.ck_cmvvm.screen.main.home.question.rank.RankActivity
import com.example.ck_cmvvm.widget.adapter.CustomPagerAdapter
import com.example.ck_cmvvm.widget.adapter.PageItem
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin

class QuestionFragment: BaseFragment<QuestionViewModel, FragmentHomeQuestionBinding>() {

    private val sharedPreferencesRepository: SharedPreferencesRepository by lazy {
        SharedPreferencesRepository(requireActivity().applicationContext)
    }

    override val viewModel by viewModel<QuestionViewModel>()

    override fun getViewBinding(): FragmentHomeQuestionBinding = FragmentHomeQuestionBinding.inflate(layoutInflater)

    override fun observeData() {

    }

    override fun initViews()= with(binding) {
        super.initViews()

        val userInfo = sharedPreferencesRepository.getUserInfo()

        setUserInfo(userInfo.userName!!, userInfo.userImage!!)

        val items = listOf(
            PageItem(R.drawable.coding, "방금 입문한 초보"),
            PageItem(R.drawable.normal, "어느 정도 적응한 중급"),
            PageItem(R.drawable.hard, "자신감 급 상승한 고급"),
            PageItem(R.drawable.hell, "현직자와 대화가 가능한 최고급")
        )

        val adapter = CustomPagerAdapter(requireContext(), items)
        viewPager.adapter = adapter

        communityButton.setOnClickListener {
            val intent = Intent(requireActivity(), CommunityActivity::class.java)
            startActivity(intent)
        }
        rankButton.setOnClickListener {
            val intent = Intent(requireContext(), RankActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setUserInfo(userName: String, userImage: String) = with(binding){
        myLayoutNameTextView.text = userName
        Glide.with(myLayoutImageView)
            .load(userImage)
            .into(myLayoutImageView)
    }
}