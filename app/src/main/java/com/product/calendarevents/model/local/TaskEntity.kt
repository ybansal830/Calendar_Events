package com.product.calendarevents.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "task")
data class TaskEntity(
    @PrimaryKey val taskID: Int?,
    @ColumnInfo(name = "date") val date: String?,
    @ColumnInfo(name = "day") val day: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "title") val title: String?)