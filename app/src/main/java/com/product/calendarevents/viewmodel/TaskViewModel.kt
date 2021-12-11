package com.product.calendarevents.viewmodel

import androidx.lifecycle.ViewModel
import com.product.calendarevents.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository
): ViewModel() {



}