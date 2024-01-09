package com.example.ck_cmvvm.screen.main.home

import android.Manifest
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.ck_cmvvm.R
import com.example.ck_cmvvm.data.repository.SharedPreferencesRepository
import com.example.ck_cmvvm.databinding.FragmentHomeBinding
import com.example.ck_cmvvm.screen.base.BaseFragment
import com.example.ck_cmvvm.screen.main.home.question.QuestionFragment
import com.example.ck_cmvvm.screen.main.home.wrong.WrongFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment: BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    override val viewModel by viewModel<HomeViewModel>()

    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    override fun observeData() {

    }

    override fun initViews() = with(binding) {
        super.initViews()

        val questionFragment = QuestionFragment()
        val wrongFragment = WrongFragment()


        replaceFragment(questionFragment)

        examButton.setOnClickListener {
            replaceFragment(questionFragment)
        }
        wrongButton.setOnClickListener {
            replaceFragment(wrongFragment)
        }

    }

    companion object{

        fun newInstance() = HomeFragment()

        const val TAG = "HomeFragment"

    }

    private fun replaceFragment(fragment: Fragment) { // 클릭한 바텀 네비게이션 종류에 따라 프래그먼트 변경
        childFragmentManager.beginTransaction()
            .apply {
                replace(R.id.fragmentContainer_home, fragment)
                commit()
            }
    }
}