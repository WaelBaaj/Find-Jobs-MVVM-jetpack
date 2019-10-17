package com.wa82bj.findgitjobs.di

import android.app.Application
import androidx.room.Room
import com.wa82bj.findgitjobs.data.db.AppDatabase
import com.wa82bj.findgitjobs.data.db.JobsDao
import com.wa82bj.findgitjobs.data.db.JobsDatabase
import com.wa82bj.findgitjobs.data.db.JobsRoomDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDb(app: Application): AppDatabase =
        Room.databaseBuilder(app, AppDatabase::class.java, "jobs_app.db")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()


    @Singleton
    @Provides
    fun provideJobsDao(db: AppDatabase): JobsDao = db.JobsDao()

    @Singleton
    @Provides
    fun provideJobsDatabase(db: AppDatabase, jobsDao: JobsDao): JobsDatabase =
        JobsRoomDatabase(db, jobsDao)


}