package com.example.ck_cmvvm.data.entity.solution

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.ck_cmvvm.util.converter.RoomTypeConverters
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
@TypeConverters(RoomTypeConverters::class)
data class SolutionEntity (

    override val id: String,
    val number: String,
    @PrimaryKey
    val title: String,
    val difficulty: String,
    val type: String,
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


): com.example.ck_cmvvm.data.entity.Entity, Parcelable