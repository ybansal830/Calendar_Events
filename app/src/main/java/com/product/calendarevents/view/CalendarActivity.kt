package com.product.calendarevents.view

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
import kotlinx.android.synthetic.main.activity_calendar.*
import java.text.SimpleDateFormat
import java.util.*

class CalendarActivity : AppCompatActivity() {

    private lateinit var activityCalendarBinding: ActivityCalendarBinding

    private var yearArray = mutableListOf<String>()

    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy",Locale.getDefault())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCalendarBinding = DataBindingUtil.setContentView(
            this,R.layout.activity_calendar)

        setYearSpinner()

        setMonthSpinner()

        setCalendarRecyclerView(31,5)

        onYearSelectListener()

        onMonthSelectListener()

    }

    private fun onMonthSelectListener() {
        activityCalendarBinding.apply {
            monthSpinner
                .onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val month = p2+1
                    val year = 2021 + yearSpinner.selectedItemId
                    setMonthView(month,year)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }
        }
    }

    private fun setMonthView(month: Int,year: Long) {
        val date = dateFormat.parse("01/$month/$year")
        calendar.time = date!!
        val day = calendar.get(Calendar.DAY_OF_WEEK)
        var days = 31
        if(month == 2){
            if(year%4 == 0L) days = 29
            else days = 28
        }
        else if(month == 4 || month == 6 || month == 9 || month == 11){
            days = 30
        }
        setCalendarRecyclerView(days,day)
    }

    private fun onYearSelectListener() {

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
                adapter = CalendarRecyclerAdapter(days,day)
                layoutManager = GridLayoutManager(context,7)
            }
        }
    }
}