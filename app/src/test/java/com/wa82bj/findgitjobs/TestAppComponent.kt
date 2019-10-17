package com.wa82bj.findgitjobs

import android.app.Application
import com.wa82bj.findgitjobs.di.AppComponent
import com.wa82bj.findgitjobs.di.AppModule
import com.wa82bj.findgitjobs.di.MainActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    MockUrlModule::class,
    MainActivityModule::class])
interface TestAppComponent : AppComponent {

    override fun inject(app: App)

    override fun inject(instance: DaggerApplication)

    fun getMockWebServer():MockWebServer

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): TestAppComponent
    }
}