package com.product.calendarevents.repository

import com.product.calendarevents.extra.Constants
import com.product.calendarevents.model.local.TaskDao
import com.product.calendarevents.model.local.TaskEntity
import com.product.calendarevents.model.remote.ApiClient
import com.product.calendarevents.model.remote.Task
import com.product.calendarevents.model.remote.TaskDetail
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val apiClient: ApiClient,
    private val taskDao: TaskDao
) {

    fun getRemoteTasks(): Observable<List<Task>>{
        return apiClient.getTaskList(Constants.USER_ID)
            .flatMap {
                return@flatMap Observable.just(it.tasks)
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun deleteRemoteTask(taskId: Int): Observable<Void>{
        val hashMap = HashMap<String,Int>()
        hashMap["user_id"] = Constants.USER_ID
        hashMap["task_id"] = taskId
        return apiClient.deleteTask(hashMap)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun storeRemoteTask(taskDetail: TaskDetail): Observable<Void>{
        val hashMap = HashMap<String,Any>()
        hashMap["user_id"] = Constants.USER_ID
        hashMap["task"] = taskDetail
        return apiClient.storeTask(hashMap)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getLocalTasks(): Observable<List<TaskEntity>> {
        return taskDao.getTaskList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun deleteLocalTask(taskId: Int){
        CoroutineScope(Dispatchers.IO).launch {
            taskDao.deleteTask(taskId)
        }
    }

    fun storeLocalTasksList(list: List<TaskEntity>){
        CoroutineScope(Dispatchers.IO).launch {
            taskDao.storeTasksList(list)
        }
    }

}