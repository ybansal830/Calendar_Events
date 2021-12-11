package com.product.calendarevents.repository

import com.product.calendarevents.model.local.TaskDao
import com.product.calendarevents.model.remote.ApiClient
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val apiClient: ApiClient,
    private val taskDao: TaskDao
) {



}