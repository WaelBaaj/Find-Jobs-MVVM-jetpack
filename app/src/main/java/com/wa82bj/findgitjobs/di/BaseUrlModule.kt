package com.wa82bj.findgitjobs.di

import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

/*
** Created by Wael Baaj 16/10/2019
*/

@Module
class BaseUrlModule {

    @Provides
    @Singleton
    @Named("baseUrl")
    fun provideBaseUrl(): String {
        return "https://jobs.github.com/"
    }
}