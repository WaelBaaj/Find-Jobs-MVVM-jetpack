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

    // Favorite Products
    @Query("SELECT * FROM jobs_table where fav = 1")
    abstract fun getFavoriteJobs(): List<JobsEntity>

    @Update
    abstract fun update(jobs: JobsEntity)

    @Query("UPDATE jobs_table SET fav = :fav  WHERE id =:jobId")
    abstract fun update( fav : Int ,jobId : String)

    @Query("SELECT * FROM jobs_table  where fav = :fav and id = :JobId")
    abstract fun isFavoriteJobs(fav : Int ,JobId : String): Single<JobsEntity>
}