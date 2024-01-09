package com.example.ck_cmvvm.model

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

abstract class Model( // 추상 클래스로 정의됨. 모든 모델이 가져야 할 공통적인 속성을 정의한다.
    open val id: String, // 모델의 고유 식별자
    open val diffType: DifficultyType // 모델이 표현하는 셀의 타입.
){
    companion object{ // 클래스 수준에서 공유되는 정적 멤버들을 정의하는 블록
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Model> = object : DiffUtil.ItemCallback<Model>(){
            // Model 객체의 리스트 변화를 효율적으로 처리하기 위한 DiffUtil.ItemCallback
            // 이 콜백은 두 모델 객체가 동일한지 여부를 판단하는 데 사용됨.
            override fun areItemsTheSame(oldItem: Model, newItem: Model): Boolean {
                // 두 모델 객체가 같은 항목을 대표하는지 여부 판단.
                return oldItem.id == newItem.id && oldItem.diffType == newItem.diffType
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Model, newItem: Model): Boolean {
                // 두 모델 객체의 내용이 같은지 여부 판단
                return oldItem === newItem
            }

        }
    }
}