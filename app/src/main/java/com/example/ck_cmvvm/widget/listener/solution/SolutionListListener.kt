package com.example.ck_cmvvm.widget.listener.solution

import com.example.ck_cmvvm.model.solution.SolutionModel
import com.example.ck_cmvvm.widget.listener.AdapterListener

interface SolutionListListener: AdapterListener {

    fun onClickItem(model: SolutionModel)

}