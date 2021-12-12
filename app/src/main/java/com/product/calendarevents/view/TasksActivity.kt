package com.product.calendarevents.view

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.product.calendarevents.R
import com.product.calendarevents.databinding.ActivityTasksBinding
import com.product.calendarevents.model.local.TaskEntity
import com.product.calendarevents.view.adapter.OnTaskClickListener
import com.product.calendarevents.view.adapter.TaskRecycler
import com.product.calendarevents.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

@AndroidEntryPoint
class TasksActivity : AppCompatActivity(), OnTaskClickListener {

    private lateinit var activityTaskBinding: ActivityTasksBinding

    private val viewModel: TaskViewModel by viewModels()

    private var taskList = mutableListOf<TaskEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityTaskBinding = DataBindingUtil
            .setContentView(this,
                R.layout.activity_tasks)

        setRecyclerView()

        getTaskList()

        activityTaskBinding.ivBack.setOnClickListener {
            startActivity(Intent(this,
                CalendarActivity::class.java))
        }

    }


    /* Getting task list from remote server then storing it into local room db and then showing
    * the latest data on the recyclerview. */

    private fun getTaskList() {
        viewModel.getRemoteTasks()
        viewModel.getLocalTasks().subscribe(object: Observer<List<TaskEntity>>{
            override fun onSubscribe(d: Disposable?) {
            }
            override fun onNext(t: List<TaskEntity>?) {
                t?.let {
                    taskList.clear()
                    taskList.addAll(it)
                    activityTaskBinding
                        .tasksRecyclerView
                        .adapter!!
                        .notifyDataSetChanged()
                }
            }
            override fun onError(e: Throwable?) {
            }
            override fun onComplete() {
            }
        })
    }

    private fun setRecyclerView() {
        activityTaskBinding.apply {
            tasksRecyclerView.apply {
                adapter = TaskRecycler(taskList,
                    this@TasksActivity)
                layoutManager = LinearLayoutManager(
                    this@TasksActivity)
            }
        }
    }


    /* On click of any task item, showing the option to delete it. If confirm delete clicked then
    * deleting the task from both room db as well as remote server. */

    override fun onClick(taskId: String) {
        AlertDialog.Builder(this)
            .setTitle("Delete Task")
            .setMessage("Confirm delete task")
            .setPositiveButton("Delete",
                object: DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    viewModel.deleteRemoteTask(taskId.toInt())
                }
            })
            .show()
    }
}