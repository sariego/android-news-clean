package dev.sariego.androidnews.framework.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dev.sariego.androidnews.framework.di.qualifier.ServerUrl
import dev.sariego.androidnews.framework.net.HackerNewsApi

@Module
@InstallIn(ApplicationComponent::class)
class NetModule {

    @Provides
    @ServerUrl
    fun provideServerUrl() = HackerNewsApi.SERVER_URL
}