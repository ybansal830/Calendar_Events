package com.product.calendarevents.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.product.calendarevents.databinding.ItemCalendarBinding

class CalendarRecyclerAdapter(
    private val days: Int,
    private val startDay: Int,
    private val onDayClickListener: OnDayClickListener
): RecyclerView.Adapter<CalendarRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarRecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemCalendarBinding = ItemCalendarBinding.inflate(
            layoutInflater,parent,false)
        return CalendarRecyclerViewHolder(itemCalendarBinding,startDay,onDayClickListener)
    }

    override fun onBindViewHolder(holder: CalendarRecyclerViewHolder, position: Int) {
        holder.setData(position)
    }

    override fun getItemCount(): Int {
        return days
    }
}