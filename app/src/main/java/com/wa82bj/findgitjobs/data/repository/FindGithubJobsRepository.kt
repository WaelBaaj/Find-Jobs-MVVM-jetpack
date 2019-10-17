package com.wa82bj.findgitjobs.data.repository

import com.wa82bj.findgitjobs.data.api.FindGithubJobsApi
import com.wa82bj.findgitjobs.data.db.JobsDatabase
import com.wa82bj.findgitjobs.data.db.toJobs
import com.wa82bj.findgitjobs.data.model.JobsModel
import com.wa82bj.findgitjobs.utils.rx.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

/*
** Created by Wael Baaj 16/10/2019
*/

class FindGithubJobsRepository @Inject constructor(
    private val apiService: FindGithubJobsApi,
    private val jobsDatabase: JobsDatabase
) {

    fun searchPosition(search:String): Observable<List<JobsModel>> {
        return apiService.searchPositions(search)
    }

     /*fun loadFavoriteJobs(): Single<List<JobsModel>> =
        Single.zip(loadFavoriteJobsDb(), loadFavoriteJobsDb()
            , BiFunction { t1, t2 ->
                if (t1.isNotEmpty()) {
                    val products = ArrayList<JobsModel>()
                    products.addAll(t1)
                    return@BiFunction products.toList()
                } else return@BiFunction t2
            })

    private fun loadFavoriteJobsDb(): Single<List<JobsModel>> =
        jobsDatabase.getFavoriteJobs()
            .map {
                return@map it.toJobs()
            }
            .subscribeOn(schedulerProvider.io())*/
}