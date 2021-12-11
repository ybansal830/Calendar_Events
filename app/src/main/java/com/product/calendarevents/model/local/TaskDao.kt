package com.product.calendarevents.model.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Observable

@Dao
interface TaskDao {

    @Query("SELECT * FROM task")
    fun getTaskList(): Observable<List<TaskEntity>>

    @Insert
    fun storeTask(taskEntity: TaskEntity)

    @Query("DELETE FROM task WHERE taskID = :taskId")
    fun deleteTask(taskId: Int)

}