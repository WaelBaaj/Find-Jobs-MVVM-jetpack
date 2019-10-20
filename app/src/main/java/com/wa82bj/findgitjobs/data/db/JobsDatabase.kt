package com.wa82bj.findgitjobs.data.db

import io.reactivex.Flowable
import io.reactivex.Single

/***
**** Created by Wael Baaj 17/10/2019
***/


interface JobsDatabase {

    fun saveJobsEntities(jobs: List<JobsEntity>)

    fun getAllJobs(): Flowable<List<JobsEntity>>

    fun getJobsLessThanAndEqualPage(): Single<List<JobsEntity>>

}