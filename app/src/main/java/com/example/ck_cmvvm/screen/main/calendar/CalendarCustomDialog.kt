package com.example.ck_cmvvm.screen.main.calendar

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Toast
import com.example.ck_cmvvm.databinding.DialogCalendarBinding

class CalendarCustomDialog(context: Context, viewModel: CalendarViewModel, dayStart: Long, dayEnd: Long): Dialog(context) {

    private lateinit var binding: DialogCalendarBinding

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cancelButton.setOnClickListener {
            viewModel.deleteAllSolution(dayStart, dayEnd)
            viewModel.fetchData()
            Toast.makeText(context, "삭제 성공!", Toast.LENGTH_SHORT).show()
            dismiss()
        }
        binding.confirmButton.setOnClickListener {
            Toast.makeText(context, "삭제 취소", Toast.LENGTH_SHORT).show()
            dismiss()
        }

    }

}