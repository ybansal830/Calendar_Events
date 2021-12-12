package com.product.calendarevents.model.remote

import com.product.calendarevents.extra.Constants
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiClient {

    @POST(Constants.GET_TASK_LIST)
    fun getTaskList(
        @Body hashMap: HashMap<String, Int>
    )
    : Observable<ResponseDTO>

    @POST(Constants.STORE_TASK)
    fun storeTask(
        @Body hashMap: HashMap<String, Any>
    )
    : Observable<Void>

    @POST(Constants.DELETE_TASK)
    fun deleteTask(
        @Body hashMap: HashMap<String, Int>
    )
    : Observable<Void>

}