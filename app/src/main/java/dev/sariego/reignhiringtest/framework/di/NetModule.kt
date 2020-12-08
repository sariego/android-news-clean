package dev.sariego.reignhiringtest.framework.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dev.sariego.reignhiringtest.framework.di.qualifier.ServerUrl
import dev.sariego.reignhiringtest.framework.net.HackerNewsApi

@Module
@InstallIn(ApplicationComponent::class)
class NetModule {

    @Provides
    @ServerUrl
    fun provideServerUrl() = HackerNewsApi.SERVER_URL
}