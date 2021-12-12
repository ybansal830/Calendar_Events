package com.product.calendarevents.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.product.calendarevents.databinding.ItemTaskBinding
import com.product.calendarevents.model.local.TaskEntity

class TaskRecycler(
    private val list: List<TaskEntity>,
    private val onTaskClickListener: OnTaskClickListener
): RecyclerView.Adapter<TaskRecycler.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemTaskBinding = ItemTaskBinding.inflate(layoutInflater,parent,false)
        return TaskViewHolder(itemTaskBinding,onTaskClickListener)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.setData(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class TaskViewHolder(
        private val itemTaskBinding: ItemTaskBinding,
        private val onTaskClickListener: OnTaskClickListener
    ): RecyclerView.ViewHolder(itemTaskBinding.root){

        fun setData(taskEntity: TaskEntity){
            itemTaskBinding.let { item ->
                taskEntity.apply {
                    item.id = taskID.toString()
                    item.date = "Date: $day $date"
                    item.title = "Title: $title"
                    item.desc = "Description: $description"
                    item.onClick = onTaskClickListener
                }
            }
        }

    }
}