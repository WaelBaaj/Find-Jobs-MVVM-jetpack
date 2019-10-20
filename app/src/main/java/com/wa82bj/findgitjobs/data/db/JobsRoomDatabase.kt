package com.wa82bj.findgitjobs.data.db

import androidx.room.RoomDatabase
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

/***
 **** Created by Wael Baaj 17/10/2019
 ***/


@Suppress("NAME_SHADOWING")
class JobsRoomDatabase @Inject constructor(
    private val database: RoomDatabase,
    private val jobsDao: JobsDao
) : JobsDatabase {

    override fun getJobsLessThanAndEqualPage(): Single<List<JobsEntity>> =
        Flowable.just(jobsDao.getJobsFromRoomDB())
            .flatMapIterable {
                return@flatMapIterable it
            }
            .map {
                return@map it
            }
            .toList()

    override fun saveJobsEntities(jobs: List<JobsEntity>) {
        database.runInTransaction {
            for (jobs in jobs) {

                jobsDao.insert(jobs)
            }
        }
    }

    override fun getAllJobs(): Flowable<List<JobsEntity>> =
        jobsDao.getAllJobs()

}