package dev.sariego.androidnews.framework.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dev.sariego.androidnews.data.DefaultArticlesRepository
import dev.sariego.androidnews.data.local.ArticlesLocalDataSource
import dev.sariego.androidnews.data.remote.ArticlesRemoteDataSource
import dev.sariego.androidnews.domain.repository.ArticlesRepository
import dev.sariego.androidnews.framework.db.RoomLocalDataSource
import dev.sariego.androidnews.framework.net.HackerNewsRemoteDataSource

@Module
@InstallIn(ApplicationComponent::class)
interface DataModule {

    @Binds
    fun bindArticlesRepository(impl: DefaultArticlesRepository): ArticlesRepository

    @Binds
    fun bindLocalDataSource(impl: RoomLocalDataSource): ArticlesLocalDataSource

    @Binds
    fun bindRemoteDataSource(impl: HackerNewsRemoteDataSource): ArticlesRemoteDataSource
}