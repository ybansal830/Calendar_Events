package com.product.calendarevents.model.remote


import com.google.gson.annotations.SerializedName

data class ResponseDTO(
    @SerializedName("tasks")
    val tasks: List<Task>
)