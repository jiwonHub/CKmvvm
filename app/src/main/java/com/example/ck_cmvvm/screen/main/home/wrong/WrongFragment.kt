package com.example.ck_cmvvm.screen.main.home.wrong

import com.example.ck_cmvvm.databinding.FragmentHomeWrongBinding
import com.example.ck_cmvvm.screen.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class WrongFragment: BaseFragment<WrongViewModel, FragmentHomeWrongBinding>() {
    override val viewModel by viewModel<WrongViewModel>()

    override fun getViewBinding(): FragmentHomeWrongBinding = FragmentHomeWrongBinding.inflate(layoutInflater)

    override fun observeData() {

    }
}