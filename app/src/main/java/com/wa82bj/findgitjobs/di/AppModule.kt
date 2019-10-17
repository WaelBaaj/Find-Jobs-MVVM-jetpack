package com.wa82bj.findgitjobs.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

/*
** Created by Wael Baaj 16/10/2019
*/

@Module(includes = [NetworkModule::class])
abstract class AppModule {

    @Binds
    abstract fun provideApplication(application: Application): Context



}