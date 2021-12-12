package com.product.calendarevents.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.product.calendarevents.R
import com.product.calendarevents.databinding.ActivityCalendarBinding
import com.product.calendarevents.view.adapter.CalendarRecyclerAdapter
import com.product.calendarevents.view.adapter.OnDayClickListener
import kotlinx.android.synthetic.main.activity_calendar.*
import java.text.SimpleDateFormat
import java.util.*

class CalendarActivity : AppCompatActivity(), OnDayClickListener {

    private lateinit var activityCalendarBinding: ActivityCalendarBinding

    private var yearArray = mutableListOf<String>()

    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy",Locale.getDefault())

    private var selectedYear = 2021
    private var selectedMonth = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCalendarBinding = DataBindingUtil.setContentView(
            this,R.layout.activity_calendar)

        setYearSpinner()

        setMonthSpinner()

        setCalendarRecyclerView(31,5)

        onYearSelectListener()

        onMonthSelectListener()

        activityCalendarBinding.ivEvents.setOnClickListener {
            startActivity(Intent(this,TasksActivity::class.java))
        }

    }

    private fun onMonthSelectListener() {
        activityCalendarBinding.apply {
            monthSpinner
                .onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    selectedMonth = p2+1
                    setMonthView()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }
        }
    }

    private fun onYearSelectListener() {
        activityCalendarBinding.apply {
            yearSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    selectedYear = 2021 + p2
                    setMonthView()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }
        }
    }

    private fun setMonthView() {
        val date = dateFormat.parse("01/$selectedMonth/$selectedYear")
        calendar.time = date!!
        val day = calendar.get(Calendar.DAY_OF_WEEK)
        var days = 31
        if(selectedMonth == 2){
            days = if(selectedYear%4 == 0) 29
            else 28
        }
        else if(selectedMonth == 4 || selectedMonth == 6 ||
            selectedMonth == 9 || selectedMonth == 11){
            days = 30
        }
        setCalendarRecyclerView(days,day)
    }

    private fun setMonthSpinner() {
        ArrayAdapter.createFromResource(this,
        R.array.months,
        android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice)
            activityCalendarBinding.monthSpinner.adapter = adapter
        }
    }

    private fun setYearSpinner() {
        for (i in 2021..2100) yearArray.add(i.toString())
        val yearArrayAdapter = ArrayAdapter<String>(this,
            android.R.layout.simple_spinner_item,yearArray)
        yearArrayAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice)
        activityCalendarBinding.yearSpinner.adapter = yearArrayAdapter
    }

    private fun setCalendarRecyclerView(days: Int,day: Int) {
        activityCalendarBinding.apply {
            calendar_recycler_view.apply {
                adapter = CalendarRecyclerAdapter(days,day-1,this@CalendarActivity)
                layoutManager = GridLayoutManager(context,7)
            }
        }
    }

    override fun onClick(day: String) {
        val intent = Intent(this,AddTaskActivity::class.java)
        val date = dateFormat.parse("$day/$selectedMonth/$selectedYear")
        calendar.time = date!!
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        intent.putExtra("day",dayOfWeek-1)
        intent.putExtra("date",dateFormat.format(date))
        startActivity(intent)
    }
}