package com.wa82bj.findgitjobs.data.db

import com.wa82bj.findgitjobs.data.model.JobsModel
import io.reactivex.Flowable

fun Flowable<List<JobsEntity>>.toJobs(): Flowable<List<JobsModel>> = map {
    return@map it.toJobs()
}

fun List<JobsEntity>.toJobs(): List<JobsModel> =
    map { JobsModel(it.id ,it.created_at, it.fav,it.title, it.location, it.type ,it.description ,
        it.how_to_apply,it.company,it.company_url,it.company_logo,it.url) }