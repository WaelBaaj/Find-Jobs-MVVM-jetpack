package com.wa82bj.findgitjobs.data.api

import com.wa82bj.findgitjobs.data.model.JobsModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/*
** Created by Wael Baaj 16/10/2019
*/

interface FindGithubJobsApi {

    @GET("positions.json?")
    fun searchPositions(@Query("search") search:String
    ): Observable<List<JobsModel>>

}