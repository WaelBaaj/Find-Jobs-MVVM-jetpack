package com.wa82bj.findgitjobs

import android.content.Context
import androidx.multidex.MultiDex
import com.squareup.leakcanary.LeakCanary
import com.wa82bj.findgitjobs.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/*
** Created by Wael Baaj 16/10/2019
*/

class App: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>
            = DaggerAppComponent.builder()
        .application(this)
        .build()

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code..
    }
}