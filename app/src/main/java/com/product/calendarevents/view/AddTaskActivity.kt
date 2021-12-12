package com.product.calendarevents.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.product.calendarevents.R
import com.product.calendarevents.databinding.ActivityAddTaskBinding
import com.product.calendarevents.model.remote.TaskDetail
import com.product.calendarevents.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTaskActivity : AppCompatActivity() {

    private val viewModel: TaskViewModel by viewModels()

    private lateinit var activityAddTaskBinding: ActivityAddTaskBinding

    private lateinit var dayOfWeek: String
    private lateinit var selectedDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityAddTaskBinding = DataBindingUtil
            .setContentView(this,
            R.layout.activity_add_task)

        setDetails()

        activityAddTaskBinding.btnAddTask.setOnClickListener {
            addTask()
        }

    }

    private fun addTask() {
        var title = ""
        var desc = ""
        activityAddTaskBinding.apply {
            title = etTaskTitle.text.toString().trim()
            desc = etTaskDesc.text.toString().trim()
        }
        if (title.isNotEmpty() && desc.isNotEmpty()){
            val task = TaskDetail(selectedDate,dayOfWeek,desc,title)
            viewModel.storeRemoteTask(task)
            startActivity(Intent(this,CalendarActivity::class.java))
        }
    }

    private fun setDetails() {
        val selectedDay = intent.getIntExtra("day",0)
        selectedDate = intent.getStringExtra("date").toString()
        dayOfWeek = "Sunday"
        when(selectedDay){
            1 -> dayOfWeek = "Monday"
            2 -> dayOfWeek = "Tuesday"
            3 -> dayOfWeek = "Wednesday"
            4 -> dayOfWeek = "Thursday"
            5 -> dayOfWeek = "Friday"
            6 -> dayOfWeek = "Saturday"
        }
        activityAddTaskBinding.apply {
            day = dayOfWeek
            date = selectedDate
        }
    }
}