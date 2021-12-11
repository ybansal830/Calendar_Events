package com.product.calendarevents.model.remote


import com.google.gson.annotations.SerializedName

data class TaskDetail(
    @SerializedName("date")
    val date: String,
    @SerializedName("day")
    val day: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("title")
    val title: String
)