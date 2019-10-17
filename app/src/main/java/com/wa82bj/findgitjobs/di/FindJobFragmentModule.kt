package com.wa82bj.findgitjobs.di

import androidx.lifecycle.ViewModel
import com.wa82bj.findgitjobs.ui.search.FindJobFragment
import com.wa82bj.findgitjobs.ui.search.FindJobViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/*
** Created by Wael Baaj 16/10/2019
*/

@Module
internal abstract class FindJobFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(FindJobViewModel::class)
    abstract fun bindFindJobViewModel( viewModel: FindJobViewModel): ViewModel

    @ContributesAndroidInjector
    abstract fun contributeFindJobFragment(): FindJobFragment


}