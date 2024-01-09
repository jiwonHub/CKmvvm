package com.example.ck_cmvvm.model.solution

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SolutionModel(
    val number: String,
    val title: String,
    val difficulty: String,
    val explan: String,
    val limit: String,
    val content: String,
    val choice1: String,
    val choice2: String,
    val choice3: String,
    val choice4: String,
    val choice5: String,
    val correct: String,
    val comment: String,
    val correctComment: String
): Parcelable