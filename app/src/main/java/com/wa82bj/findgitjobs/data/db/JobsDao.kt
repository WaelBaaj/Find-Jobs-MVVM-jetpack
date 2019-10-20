package com.wa82bj.findgitjobs.data.db

import androidx.room.*
import io.reactivex.Flowable
import io.reactivex.Single

/*
** Created by Wael Baaj 17/10/2019
*/


@Dao
abstract interface JobsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(ads: JobsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(jobs: List<JobsEntity>)

    @Query("SELECT * FROM jobs_table")
    abstract fun getAllJobs(): Flowable<List<JobsEntity>>

    @Query("SELECT * FROM jobs_table ")
    abstract fun getJobsFromRoomDB(): List<JobsEntity>

    @Query("SELECT * FROM jobs_table where id = :jobId")
    abstract fun loadOneByJobId(jobId: String): Single<JobsEntity>

    @Query("DELETE FROM jobs_table")
    abstract fun deleteAll()

}