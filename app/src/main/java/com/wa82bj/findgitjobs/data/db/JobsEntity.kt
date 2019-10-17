package com.wa82bj.findgitjobs.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
** Created by Wael Baaj 17/10/2019
*/

@Entity(tableName = "jobs_table")
data class JobsEntity (

    @PrimaryKey(autoGenerate = false)
    val id:String,
    val created_at:String,
    val title:String,
    val fav: Boolean,
    val location:String,
    val type:String,
    val description:String,
    val how_to_apply:String,
    val company:String,
    val company_url:String?,
    val company_logo:String?,
    val url:String
)