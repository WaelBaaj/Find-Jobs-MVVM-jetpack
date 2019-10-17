package com.wa82bj.findgitjobs.data.model


/*
** Created by Wael Baaj 16/10/2019
*/

data class JobsModel(

    val id:String? = null,
    val created_at:String? = null,
    val fav: Boolean ? = null,
    val title:String? = null,
    val location:String? = null,
    val type:String? = null,
    val description:String? = null,
    val how_to_apply:String? = null,
    val company:String? = null,
    val company_url:String? = null,
    val company_logo:String? = null,
    val url:String? = null
)