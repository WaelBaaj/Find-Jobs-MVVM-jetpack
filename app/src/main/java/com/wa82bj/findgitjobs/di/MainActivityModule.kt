package com.wa82bj.findgitjobs.di

import androidx.lifecycle.ViewModelProvider
import com.wa82bj.findgitjobs.ui.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

/*
** Created by Wael Baaj 16/10/2019
*/

@Module
internal abstract class MainActivityModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @ContributesAndroidInjector(modules = [FindJobFragmentModule::class])
    internal abstract fun contributeMainActivity(): MainActivity
}