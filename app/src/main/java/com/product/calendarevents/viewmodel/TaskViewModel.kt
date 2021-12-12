package com.product.calendarevents.viewmodel

import androidx.lifecycle.ViewModel
import com.product.calendarevents.model.local.TaskEntity
import com.product.calendarevents.model.remote.Task
import com.product.calendarevents.model.remote.TaskDetail
import com.product.calendarevents.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository
): ViewModel() {

    fun getRemoteTasks(){
        repository.getRemoteTasks().subscribe(object: Observer<List<Task>>{
            override fun onSubscribe(d: Disposable?) {
            }
            override fun onNext(t: List<Task>?) {
                storeLocalTasksList(t)
            }
            override fun onError(e: Throwable?) {
            }
            override fun onComplete() {
            }
        })
    }

    fun storeLocalTasksList(list: List<Task>?){
        list?.let { listT ->
            val newList = mutableListOf<TaskEntity>()
            for (i in listT){
                i.taskDetail.apply {
                    newList.add(TaskEntity(i.taskId,date,day,description,title))
                }
            }
            repository.storeLocalTasksList(newList)
        }
    }

    fun getLocalTasks(): Observable<List<TaskEntity>>{
        return repository.getLocalTasks()
    }

    fun deleteRemoteTask(taskId: Int){
        repository.deleteRemoteTask(taskId).subscribe(object: Observer<Void>{
            override fun onSubscribe(d: Disposable?) {
            }
            override fun onNext(t: Void?) {
                repository.deleteLocalTask(taskId)
            }
            override fun onError(e: Throwable?) {
            }
            override fun onComplete() {
            }
        })
    }

    fun storeRemoteTask(task: TaskDetail){
        repository.storeRemoteTask(task).subscribe(object: Observer<Void>{
            override fun onSubscribe(d: Disposable?) {
            }
            override fun onNext(t: Void?) {
                getRemoteTasks()
            }
            override fun onError(e: Throwable?) {
            }
            override fun onComplete() {
            }
        })
    }

}