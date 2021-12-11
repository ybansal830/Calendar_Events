package com.product.calendarevents.view.adapter

import androidx.recyclerview.widget.RecyclerView
import com.product.calendarevents.databinding.ItemCalendarBinding

class CalendarRecyclerViewHolder(
    private val itemCalendarBinding: ItemCalendarBinding,
    private val startDay: Int
) : RecyclerView.ViewHolder(itemCalendarBinding.root) {

        fun setData(dayPos: Int){
            itemCalendarBinding.apply {
                pos = (dayPos+1).toString()
                when((startDay+dayPos)%7){
                    0 -> day = "Sun"
                    1 -> day = "Mon"
                    2 -> day = "Tue"
                    3 -> day = "Wed"
                    4 -> day = "Thur"
                    5 -> day = "Fri"
                    6 -> day = "Sat"
                }
            }
        }

}