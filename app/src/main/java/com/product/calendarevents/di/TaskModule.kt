package com.product.calendarevents.di

import android.content.Context
import androidx.room.Room
import com.product.calendarevents.extra.Constants
import com.product.calendarevents.model.local.TaskDao
import com.product.calendarevents.model.local.TaskDatabase
import com.product.calendarevents.model.remote.ApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TaskModule {

    @Provides
    @Singleton
    fun getApiClient(): ApiClient{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build().create(ApiClient::class.java)
    }

    @Provides
    @Singleton
    fun getTaskDao(@ApplicationContext context: Context): TaskDao{
        return Room.databaseBuilder(
            context,
            TaskDatabase::class.java,
            "task_db"
        ).build().taskDao()
    }

}