package com.example.ck_cmvvm.screen.main.calendar

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ck_cmvvm.databinding.FragmentCalendarBinding
import com.example.ck_cmvvm.screen.base.BaseFragment
import com.example.ck_cmvvm.screen.main.home.HomeFragment
import com.example.ck_cmvvm.widget.adapter.CalendarAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar

class CalendarFragment: BaseFragment<CalendarViewModel, FragmentCalendarBinding>() {

    private val adapter = CalendarAdapter()
    private lateinit var dialog: CalendarCustomDialog

    var startTime: Long = 0
    var endTime: Long = 0

    override val viewModel by viewModel<CalendarViewModel>()

    override fun getViewBinding(): FragmentCalendarBinding = FragmentCalendarBinding.inflate(layoutInflater)

    override fun observeData() = viewModel.solutionsForDay.observe(viewLifecycleOwner) {

    }

    override fun initViews() = with(binding) {
        super.initViews()

        calendarRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        calendarRecyclerView.adapter = adapter

        val today = Calendar.getInstance()

        // 현재 날짜에 해당하는 문제들을 가져오기 위해 fetchSolutionsForDay 호출
        val dayStart = today.apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val dayEnd = today.apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }.timeInMillis

        viewModel.fetchSolutionsForDay(dayStart, dayEnd)
        viewModel.solutionsForDay.observe(viewLifecycleOwner) { solutions ->
            adapter.submitList(solutions)
        }

        calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            fetchSolutionsForSelectedDay(year, month, dayOfMonth)
            dialog = CalendarCustomDialog(requireContext(), viewModel, startTime, endTime)
            dialog.setCanceledOnTouchOutside(true)
            adapter.notifyDataSetChanged()
            viewModel.solutionsForDay.observe(viewLifecycleOwner) { solutions ->
                adapter.submitList(solutions)
            }
        }

        deleteFloatButton.setOnClickListener {
            if (!::dialog.isInitialized) {
                dialog = CalendarCustomDialog(requireContext(), viewModel, startTime, endTime)
            }
            dialog.show()
        }
    }

    private fun fetchSolutionsForSelectedDay(year: Int, month: Int, day: Int) {
        val calendar = Calendar.getInstance()

        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)

        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val dayStart = calendar.timeInMillis

        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        val dayEnd = calendar.timeInMillis

        startTime = dayStart
        endTime = dayEnd

        viewModel.fetchSolutionsForDay(dayStart, dayEnd)
    }

    companion object{

        fun newInstance() = CalendarFragment()

        const val TAG = "CalendarFragment"

    }
}